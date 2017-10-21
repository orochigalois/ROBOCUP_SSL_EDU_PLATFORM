/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.sisyphus.finder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;

import edu.tigers.sumatra.ai.sisyphus.errt.tree.Node;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.AngleMath;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.shapes.circle.Circle;
import edu.tigers.sumatra.shapes.circle.ICircle;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.Goal;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.PenaltyArea;
import edu.tigers.sumatra.wp.data.SimpleWorldFrame;
import edu.tigers.sumatra.wp.data.WorldFrame;
import edu.tigers.sumatra.wp.fieldPrediction.FieldPredictionInformation;
import edu.tigers.sumatra.wp.fieldPrediction.FieldPredictor;
import edu.tigers.sumatra.wp.fieldPrediction.WorldFramePrediction;
import edu.x.framework.skillsystem.MovementCon;



public final class FieldInformation
{
	
	
	private static final double		GOALPOST_RADIUS					= 10;
	
	private final double					botRadius							= Geometry.getBotRadius();
	private final double					ballRadius							= Geometry.getBallRadius();
	private final Goal					goalTheir							= Geometry.getGoalTheir();
	private final Goal					goalOur								= Geometry.getGoalOur();
	private final PenaltyArea			penaltyAreaOur						= Geometry
			.getPenaltyAreaOur();
	private final PenaltyArea			penaltyAreaTheir					= Geometry
			.getPenaltyAreaTheir();
	// safety distance
	@Configurable(comment = "min. distance to obstacles [mm]")
	private static double				safetyDistance						= 100;
	@Configurable(comment = "min. distance to obstacles in 2nd round [mm]")
	private static double				secondSafetyDistance				= 45;
	@Configurable(comment = "min. distance to the ball", defValue = "50")
	private static double				safetyDistanceBall				= 200;
	@Configurable(comment = "puffer between end of safety distance and possible start / target positions for the ERRT")
	private static double				pufferSafetyEndToDest			= 10;
	@Configurable(comment = "time in seconds which are predicted for the path planning")
	private static double				predictionIterationsMaximum	= 10;
	@Configurable(comment = "time in seconds for the prediction step size")
	private static double				predictionStepSize				= 0.1;
	@Configurable(comment = "Margin between our penArea border and bot center to keep")
	private static double				margin2PenaltyAreaOurInner		= 200;
	// @Configurable(comment =
	// "Margin between our penArea border and bot center to keep, should be bigger than margin2PenaltyAreaPathPlanning")
	private final double					margin2PenaltyAreaOurOuter		= Geometry.getPenaltyAreaMargin();
	@Configurable(comment = "Margin between their penArea border and bot center to keep")
	private static double				margin2PenaltyAreaTheirInner	= 90;
	@Configurable(comment = "Margin between their penArea border and bot center to keep, should be bigger than margin2PenaltyAreaPathPlanning")
	private static double				margin2PenaltyAreaTheirOuter	= 150;
	@Configurable(comment = "Path planning max distance, calculated by factor times velocy", defValue = "2000")
	private static double				pathPlanningCircleVelocity		= 2000;
	@Configurable(comment = "Path planning max distance, calculated by factor times velocy", defValue = "200")
	private static double				pathPlanningCircleFixed			= 200;
	
	
	
	private boolean						isSecondTry							= false;
	
	
	private final MovementCon			moveCon;
	private WorldFrame					wFrame								= null;
	private WorldFramePrediction		wfp									= null;
	private final BotID					botId;
	
	
	private boolean						prohibitTigersPenArea			= true;
	private boolean						prohibitTheirPenArea				= false;
	private double							safetyDistanceBot					= safetyDistance;
	
	private boolean						targetAdjustedBecauseOfBall	= false;
	private boolean						startAdjustedBecauseOfBall		= false;
	private IVector2						preprocessedDestination			= null;
	private IVector2						preprocessedStart					= null;
	
	private final List<IVector2>		ignoredPoints						= new ArrayList<IVector2>();
	private final List<BotID>			ignoredBotsForCollDetect		= new ArrayList<BotID>();
	
	
	private final Map<BotID, Double>	specialSafetyDistances			= new HashMap<BotID, Double>();
	
	
	private final List<FutureBot>		botPosList							= new ArrayList<FutureBot>();
	private FutureObstacle				ball;
	
	
	static
	{
		ConfigRegistration.registerClass("sisyphus", FieldInformation.class);
	}
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public FieldInformation(final BotID botId, final MovementCon moveCon)
	{
		this.botId = botId;
		this.moveCon = moveCon;
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public void updateWorldFrame(final WorldFrame wFrame)
	{
		if (wFrame.getBot(botId) == null)
		{
			// do not update this time
			return;
		}
		this.wFrame = wFrame;
		wfp = new FieldPredictor(wFrame.getBots().values(), wFrame.getBall()).create();
		ball = new FutureObstacle(wFrame.getBall().getPos(), wFrame.getBall().getVel());
		ITrackedBot bot = wFrame.getBot(botId);
		if (bot != null)
		{
			moveCon.update(wFrame, bot);
		}
		updateInternalFields();
	}
	
	
	private void updateInternalFields()
	{
		if (isSecondTry)
		{
			safetyDistanceBot = secondSafetyDistance;
		} else
		{
			safetyDistanceBot = safetyDistance;
		}
		preprocessedStart = wFrame.getBot(botId).getPos();
		preprocessedDestination = moveCon.getDestination();
		handleProhibitPenaltyArea();
		
		// ORDER IMPORTANT
		adjustStartIfInPenArea();
		adjustTargetIfInPenArea();
		adjustStartIfBallOrBotIsAtStart();
		adjustTargetIfBallIsTarget();
		adjustTargetIfBotIsTarget();
		fillBotPosList(0);
	}
	
	
	private void handleProhibitPenaltyArea()
	{
		if (moveCon.isPenaltyAreaAllowedOur()
				|| penaltyAreaOur.isPointInShape(wFrame.getBot(botId).getPos()))
		{
			prohibitTigersPenArea = false;
		} else
		{
			prohibitTigersPenArea = true;
		}
		
		if (moveCon.isPenaltyAreaAllowedTheir()
				|| penaltyAreaTheir.isPointInShape(wFrame.getBot(botId).getPos()))
		{
			prohibitTheirPenArea = false;
		} else
		{
			prohibitTheirPenArea = true;
		}
	}
	
	
	
	
	
	
	public void fillBotPosList(final double time)
	{
		setBotAndBallToTimeAndMaxDist(time, Geometry.getFieldLength());
	}
	
	
	
	public void setBotAndBallToTimeAndMaxDist(final double time, final double maxDist)
	{
		botPosList.clear();
		
		botPosList.addAll(getAllBotsExceptMe(time, maxDist));
		
		// if bot blocks anything, don't consider it
		for (IVector2 obstacle : ignoredPoints)
		{
			removeBotsNearPoint(obstacle);
		}
		ball = new FutureObstacle(wfp.getBall().getPosAt(time), wfp.getBall().getVel());
	}
	
	
	private List<FutureBot> getAllBotsExceptMe(final double time)
	{
		return getAllBotsExceptMe(time, Geometry.getFieldLength());
	}
	
	
	private List<FutureBot> getAllBotsExceptMe(final double time, final double maxDist)
	{
		List<FutureBot> newBotPosList = new ArrayList<FutureBot>(13);
		for (Entry<BotID, FieldPredictionInformation> fpi : wfp.getBots().entrySet())
		{
			if (!botId.equals(fpi.getKey())
					&& (GeoMath.distancePP(fpi.getValue().getPosAt(time), wFrame.getBot(botId).getPos()) < maxDist))
			{
				newBotPosList.add(new FutureBot(fpi.getKey(), fpi.getValue().getPosAt(time), fpi.getValue().getVel()));
			}
		}
		return newBotPosList;
		
	}
	
	
	
	private List<IVector2> removeBotsNearPoint(final IVector2 point)
	{
		final List<IVector2> botsAtPoint = new ArrayList<IVector2>();
		for (final IVector2 bot : getAllBotsExceptMe(0))
		{
			if (isBotNearPoint(bot, point, safetyDistanceBot, botRadius))
			{
				botsAtPoint.add(bot);
			}
		}
		for (final IVector2 bot : botsAtPoint)
		{
			botPosList.remove(bot);
		}
		return botsAtPoint;
	}
	
	
	
	private boolean isBotNearPoint(final IVector2 bot, final IVector2 point, final double safetyDistance,
			final double obstacleRadius)
	{
		return GeoMath.distancePP(bot, point) < (safetyDistance + botRadius + obstacleRadius);
	}
	
	
	
	
	private void adjustTargetIfInPenArea()
	{
		if (penaltyAreaOur.isPointInShape(preprocessedDestination, margin2PenaltyAreaOurOuter)
				&& prohibitTigersPenArea)
		{
			// preprocessedDestination = Geometry.getPenaltyAreaOur()
			// .nearestPointOutside(preprocessedDestination);
			preprocessedDestination = penaltyAreaOur.nearestPointOutside(preprocessedDestination,
					margin2PenaltyAreaOurOuter + 1);
		}
		
		if (penaltyAreaTheir.isPointInShape(preprocessedDestination, margin2PenaltyAreaTheirOuter)
				&& prohibitTheirPenArea)
		{
			preprocessedDestination = penaltyAreaTheir.nearestPointOutside(preprocessedDestination,
					margin2PenaltyAreaTheirOuter + 1);
		}
	}
	
	
	
	private void adjustStartIfBallOrBotIsAtStart()
	{
		IVector2 possiblePreprocessedStart = null;
		startAdjustedBecauseOfBall = false;
		if (moveCon.isTheirBotsObstacle() || moveCon.isOurBotsObstacle())
		{
			List<FutureBot> obstacles = obstaclesAtPoint(preprocessedStart);
			ignoredBotsForCollDetect.clear();
			for (FutureBot obstacle : obstacles)
			{
				possiblePreprocessedStart = preprocessStartForObstacle(obstacle, false);
				ignoredBotsForCollDetect.add(obstacle.getBot());
				startAdjustedBecauseOfBall = true;
			}
		}
		if (moveCon.isBallObstacle())
		{
			if (isBotNearPoint(ball, preprocessedStart, safetyDistanceBall, ballRadius)
					&& !isBotNearPoint(wFrame.getBot(botId).getPos(), ball, safetyDistanceBall,
							botRadius))
			{
				possiblePreprocessedStart = preprocessStartForObstacle(ball, true);
				startAdjustedBecauseOfBall = true;
			}
		}
		if (startAdjustedBecauseOfBall)
		{
			if (possiblePreprocessedStart != null)
			{
				preprocessedStart = possiblePreprocessedStart;
			}
		}
	}
	
	
	private void adjustStartIfInPenArea()
	{
		startAdjustedBecauseOfBall = false;
		if (!moveCon.isPenaltyAreaAllowedOur()
				&& penaltyAreaOur.isPointInShape(preprocessedStart, margin2PenaltyAreaOurInner))
		{
			startAdjustedBecauseOfBall = true;
			preprocessedStart = penaltyAreaOur.nearestPointOutside(preprocessedStart,
					margin2PenaltyAreaOurInner + 1);
		}
		if (!moveCon.isPenaltyAreaAllowedTheir()
				&& penaltyAreaTheir.isPointInShape(preprocessedStart, margin2PenaltyAreaTheirInner))
		{
			startAdjustedBecauseOfBall = true;
			preprocessedStart = penaltyAreaTheir.nearestPointOutside(preprocessedStart,
					margin2PenaltyAreaTheirInner + 1);
		}
	}
	
	
	
	private void adjustTargetIfBallIsTarget()
	{
		if (moveCon.isBallObstacle())
		{
			if (isBallAtTarget()
					&& !isBotNearPoint(wFrame.getBot(botId).getPos(), ball,
							getNeededDistanceFromBallToStartPathPlanning(),
							botRadius))
			{
				preprocessedDestination = botToObstacleLeadPoint(wFrame.getBot(botId), preprocessedDestination, wFrame
						.getBall().getPos(), getNeededDistanceFromBallToStartPathPlanning());
				targetAdjustedBecauseOfBall = true;
			}
		}
	}
	
	
	
	private void adjustTargetIfBotIsTarget()
	{
		List<FutureBot> botsAtTarget = obstaclesAtPoint(preprocessedDestination);
		for (FutureBot bot : botsAtTarget)
		{
			double distBotToDest = bot.subtractNew(preprocessedDestination).getLength2();
			if (distBotToDest > pufferSafetyEndToDest)
			{
				specialSafetyDistances.put(bot.getBot(), distBotToDest - pufferSafetyEndToDest - (botRadius * 2));
			} else
			{
				ignoredPoints.add(bot);
			}
		}
		
	}
	
	
	private IVector2 botToObstacleLeadPoint(final ITrackedBot bot, final IVector2 destination,
			final IVector2 obstacle, final double distance)
	{
		IVector2 obstacleToDestination = destination.subtractNew(obstacle);
		IVector2 earlierStoppingDirection = null;
		if (obstacleToDestination.isZeroVector())
		{
			IVector2 obstacleToBot = bot.getPos().subtractNew(obstacle);
			earlierStoppingDirection = obstacleToBot;
		} else
		{
			earlierStoppingDirection = obstacleToDestination;
		}
		IVector2 obstacleToDestinationScaled = earlierStoppingDirection.scaleToNew(distance);
		return obstacle.addNew(obstacleToDestinationScaled);
	}
	
	
	private List<FutureBot> obstaclesAtPoint(final IVector2 point)
	{
		return botAtPoint(point, safetyDistanceBot);
	}
	
	
	private List<FutureBot> botAtPoint(final IVector2 point, final double safetyDist)
	{
		List<FutureBot> obstacles = new ArrayList<FutureBot>();
		for (final FutureBot bot : getAllBotsExceptMe(0))
		{
			if (isBotNearPoint(bot, point, safetyDist, botRadius))
			{
				obstacles.add(bot);
			}
		}
		return obstacles;
	}
	
	
	
	private IVector2 preprocessStartForObstacle(final IVector2 obstacle, final boolean isBall)
	{
		IVector2 possiblePreprocessedStart = null;
		IVector2 obstacleToStart = preprocessedStart.subtractNew(obstacle);
		if (obstacleToStart.isZeroVector())
		{
			// log.info("A bot has the same position than an obstacle (bot or ball).");
			return preprocessedStart;
		}
		double radius = botRadius;
		double safety = safetyDistanceBot;
		if (isBall)
		{
			radius = ballRadius;
			safety = safetyDistanceBall;
		}
		// Idea of the following code:
		// If the bot is too close to an obstacle first drive away form the obstacle in the most direct way and then
		// continue with path planning
		double neededDistance = radius + safety + botRadius;
		double distanceOrthogonalMovementSqare = ((neededDistance * neededDistance)
				- (obstacleToStart.getLength2() * obstacleToStart
						.getLength2()));
		double distanceOrthogonalMovement = Math.sqrt(distanceOrthogonalMovementSqare);
		IVector2 orthogonalToObstacleToStart = obstacleToStart.getNormalVector();
		IVector2 startOnCircle = preprocessedStart.addNew(orthogonalToObstacleToStart
				.scaleToNew(distanceOrthogonalMovement));
		IVector2 obstacleToStartOnCircle = startOnCircle.subtractNew(obstacle);
		double angleToRotate = Math.abs(AngleMath.difference(obstacleToStart.getAngle(),
				obstacleToStartOnCircle.getAngle()));
		double distanceToRealTarget = Double.MAX_VALUE;
		
		double endAngle = angleToRotate * 2;
		// log.warn("circle around: " + obstacle + "(" + preprocessedStart + ") " + angleToRotate);
		for (double currentAngle = 0; currentAngle < endAngle; currentAngle += 0.1)
		{
			IVector2 possibleStart = GeoMath.stepAlongCircle(startOnCircle, obstacle, currentAngle);
			// log.warn("possibleStart: " + possibleStart);
			if (isPointOK(possibleStart))
			{
				double distance = possibleStart.subtractNew(preprocessedDestination).getLength2();
				if (distance < distanceToRealTarget)
				{
					distanceToRealTarget = distance;
					possiblePreprocessedStart = possibleStart;
				}
			}
		}
		return possiblePreprocessedStart;
	}
	
	
	private boolean isBallAtTarget()
	{
		return ball.equals(preprocessedDestination, getNeededDistanceFromBallToStartPathPlanning());
	}
	
	
	private double getNeededDistanceFromBallToStartPathPlanning()
	{
		return botRadius + ballRadius + (safetyDistanceBall * 2);
	}
	
	
	
	
	
	
	
	public boolean isWayOK(final IVector2 a, final IVector2 b)
	{
		return isWayOK(a, b, 1.0);
	}
	
	
	
	public boolean isWayOK(final IVector2 a, final IVector2 b, final double safetyFactor)
	{
		double maxDist = (((wFrame.getBot(botId).getVel().getLength2() * pathPlanningCircleVelocity)
				+ pathPlanningCircleFixed));
		setBotAndBallToTimeAndMaxDist(0f, maxDist);
		boolean result1 = isWayOKImpl(a, b, safetyFactor);
		setBotAndBallToTimeAndMaxDist(0.1f, maxDist);
		boolean result2 = isWayOKImpl(a, b, safetyFactor);
		setBotAndBallToTimeAndMaxDist(0f, 0);
		return result1 && result2;
	}
	
	
	private boolean isWayOKImpl(final IVector2 a, final IVector2 b, final double safetyFactor)
	{
		if (moveCon.isTheirBotsObstacle() || moveCon.isOurBotsObstacle())
		{
			if (isBotInWay(a, b, safetyDistanceBot * safetyFactor))
			{
				return false;
			}
		}
		
		if (moveCon.isGoalPostObstacle())
		{
			if (isGoalPostInWay(a, b, safetyDistanceBot * safetyFactor))
			{
				return false;
			}
		}
		
		if (isProhibitedFieldAreaInWay(a, b))
		{
			return false;
		}
		if (moveCon.isBallObstacle())
		{
			if (isBallInWay(a, b, safetyDistanceBall * safetyFactor))
			{
				return false;
			}
		}
		
		
		// YOOUUUUU SHALL NOT PASS! (watching LoTR right now^^`)
		return true;
	}
	
	
	
	public boolean isPointOK(final IVector2 point)
	{
		return isPointOK(point, safetyDistanceBot, false);
	}
	
	
	
	public boolean isPointOK(final IVector2 point, final double calcSafetyDistance, final boolean forceConsiderBall)
	{
		return (isPointOKPP(point, calcSafetyDistance, forceConsiderBall) == null);
	}
	
	
	
	public IVector2 isPointOKPP(final IVector2 point, final double calcSafetyDistance, final boolean forceConsiderBall)
	{
		if (moveCon.isTheirBotsObstacle() || moveCon.isOurBotsObstacle())
		{
			IVector2 obstacle = testBot(point, calcSafetyDistance);
			if (obstacle != null)
			{
				return obstacle;
			}
		}
		if (moveCon.isGoalPostObstacle())
		{
			// if (testGoalPost(point, calcSafetyDistance))
			// {
			// return false;
			// }
		}
		
		if (testProhibitedFieldArea(point))
		{
			return penaltyAreaOur.getPenaltyRectangle().getMidPoint();
		}
		
		if (moveCon.isBallObstacle() || forceConsiderBall)
		{
			if (testBall(point, calcSafetyDistance))
			{
				return ball;
			}
		}
		
		return null;
	}
	
	
	
	public MovementCon getMoveCon()
	{
		return moveCon;
	}
	
	
	
	private boolean isBallInWay(final IVector2 nodeA, final IVector2 nodeB, final double safetyDistance)
	{
		for (double i = 0; (i <= (predictionIterationsMaximum * predictionStepSize))
				&& (!isSecondTry || (i == 0)); i += predictionStepSize)
		{
			IVector2 ballPos = wFrame.getBall().getPosByTime(i);
			if (isElementInWay(nodeA, nodeB, ballPos, ballRadius, safetyDistance))
			{
				return true;
			}
		}
		return false;
	}
	
	
	private boolean testBall(final IVector2 node, final double safetyDistance)
	{
		if (testElement(node, ball, ballRadius, safetyDistance))
		{
			return true;
		}
		return false;
	}
	
	
	
	private boolean isGoalPostInWay(final IVector2 nodeA, final IVector2 nodeB, final double safetyDistance)
	{
		double safety = safetyDistance + GOALPOST_RADIUS + botRadius;
		if ((nodeA.x() < (goalOur.getGoalCenter().x() + safety)) || (nodeB.x() < (goalOur.getGoalCenter().x() + safety)))
		{
			if (goalOur.isLineCrossingGoal(nodeA, nodeB, safety))
			{
				return true;
			}
		}
		if ((nodeA.x() > (goalTheir.getGoalCenter().x() - safety))
				|| (nodeB.x() > (goalTheir.getGoalCenter().x() - safety)))
		{
			if (goalTheir.isLineCrossingGoal(nodeA, nodeB, safety))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	// @SuppressWarnings("unused")
	// private boolean testGoalPost(final IVector2 node, final double safetyDistance)
	// {
	// double safety = safetyDistance + GOALPOST_RADIUS + botRadius;
	// if ((node.x() < (goalOur.getGoalCenter().x() + safety)))
	// {
	// // this function is called very often, so we check first
	// if (goalOur.isLineCrossingGoal(node, node, safety))
	// {
	// return true;
	// }
	// }
	// if ((node.x() > (goalTheir.getGoalCenter().x() - safety)))
	// {
	// if (goalTheir.isLineCrossingGoal(node, node, safety))
	// {
	// return true;
	// }
	// }
	// return false;
	// }
	
	
	private boolean isProhibitedFieldAreaInWay(final IVector2 nodeA, final IVector2 nodeB)
	{
		if (prohibitTigersPenArea)
		{
			if (isPenaltyAreaInWay(nodeA, nodeB, penaltyAreaOur, margin2PenaltyAreaOurInner))
			{
				return true;
			}
		}
		
		if (prohibitTheirPenArea)
		{
			if (isPenaltyAreaInWay(nodeA, nodeB, penaltyAreaTheir, margin2PenaltyAreaTheirInner))
			{
				return true;
			}
		}
		
		if (moveCon.isRefereeStop())
		{
			ICircle stopArea = new Circle(ball, Geometry.getBotToBallDistanceStop());
			if (!stopArea.lineIntersections(new Line(nodeA, nodeB.subtractNew(nodeA))).isEmpty())
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	private boolean testProhibitedFieldArea(final IVector2 node)
	{
		if (prohibitTigersPenArea)
		{
			// no safety needed
			if (testPenaltyAreaOur(node))
			{
				return true;
			}
		}
		
		if (prohibitTheirPenArea)
		{
			// no safety needed
			if (testPenaltyAreaTheir(node))
			{
				return true;
			}
		}
		
		if (moveCon.isRefereeStop())
		{
			ICircle stopArea = new Circle(ball, Geometry.getBotToBallDistanceStop());
			if (stopArea.isPointInShape(node))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	private boolean isPenaltyAreaInWay(final IVector2 nodeA, final IVector2 nodeB, final PenaltyArea penArea,
			final double margin)
	{
		int stepSize = 10;
		for (int i = 0; (i < 1000) && ((i * 10) < nodeA.subtractNew(nodeB).getLength2()); i = i + 10)
		{
			IVector2 testPoint = GeoMath.stepAlongLine(nodeA, nodeB, i * stepSize);
			if (penArea.isPointInShape(testPoint, margin))
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	private boolean testPenaltyAreaOur(final IVector2 node)
	{
		return penaltyAreaOur.isPointInShape(node, margin2PenaltyAreaOurOuter);
	}
	
	
	private boolean testPenaltyAreaTheir(final IVector2 node)
	{
		return penaltyAreaTheir.isPointInShape(node, margin2PenaltyAreaTheirOuter);
	}
	
	
	
	private boolean isBotInWay(final IVector2 a, final IVector2 b, final double safetyDistance)
	{
		for (final FutureBot bot : botPosList)
		{
			for (double i = 0; (i <= (predictionIterationsMaximum * predictionStepSize))
					&& (!isSecondTry || (i == 0)); i += predictionStepSize)
			{
				IVector2 testPos = wfp.getBot(bot.getBot()).getPosAt(i);
				if (isElementInWay(a, b, testPos, botRadius, safetyDistanceForBot(bot, safetyDistance)))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	private IVector2 testBot(final IVector2 point, final double safetyDistance)
	{
		for (final FutureBot bot : botPosList)
		{
			if (!ignoredBotsForCollDetect.contains(bot.getBot())
					&&
					
					testElement(point, bot, botRadius, safetyDistanceForBot(bot, safetyDistance)))
			{
				// log.info("Collision: " + botId.getTeamColor().toString() + " " + botId.getNumber() + " with "
				// + bot.getBot().getTeamColor().toString() + " " + bot.getBot().getNumber());
				return bot;
			}
		}
		
		return null;
	}
	
	
	private double safetyDistanceForBot(final FutureBot bot, double defaultSafetyDistance)
	{
		if (specialSafetyDistances.containsKey(bot.getBot()))
		{
			defaultSafetyDistance = Math.min(specialSafetyDistances.get(bot.getBot()), defaultSafetyDistance);
		}
		return defaultSafetyDistance;
	}
	
	
	
	private boolean isElementInWay(final IVector2 nodeA, final IVector2 nodeB, final IVector2 elementPos,
			final double elementRadius,
			final double safetyDistance)
	{
		if (nodeA.equals(nodeB))
		{
			return false;
		}
		
		final double distanceX = nodeB.x() - nodeA.x();
		final double distanceY = nodeB.y() - nodeA.y();
		IVector2 nearest = null;
		
		final double u = (Math.abs((elementPos.x() - nodeA.x()) * distanceX) + Math.abs((elementPos.y() - nodeA.y())
				* distanceY))
				/ ((distanceX * distanceX) + (distanceY * distanceY));
		
		if (u < 0)
		{
			nearest = nodeA;
		} else if (u > 1)
		{
			nearest = nodeB;
		} else
		{
			// nearest point on line is between nodeA and nodeB
			nearest = new Node(nodeA.x() + (int) (u * distanceX), nodeA.y() + (int) (u * distanceY));
		}
		
		
		if (nearest.equals(elementPos, (botRadius + elementRadius + safetyDistance)))
		{
			return true;
		}
		
		return false;
	}
	
	
	private boolean testElement(final IVector2 node, final IVector2 elementPos, final double elementRadius,
			final double safetyDistance)
	{
		if (GeoMath.distancePP(node, elementPos) < (botRadius + elementRadius + safetyDistance))
		{
			return true;
		}
		
		return false;
	}
	
	
	
	
	
	public boolean isBotIllegallyInPenaltyArea()
	{
		return (!moveCon.isPenaltyAreaAllowedOur() && penaltyAreaOur.isPointInShape(wFrame.getBot(botId).getPos(),
				margin2PenaltyAreaOurOuter));
	}
	
	
	
	public IVector2 getNearestNodeOutsidePenArea()
	{
		IVector2 tigerPos = wFrame.getBot(botId).getPos();
		IVector2 pointOnLine = penaltyAreaOur.nearestPointOutside(tigerPos);
		IVector2 directionVectorToOutside = pointOnLine.subtractNew(tigerPos);
		directionVectorToOutside = directionVectorToOutside.scaleToNew(0);
		return pointOnLine.addNew(directionVectorToOutside);
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public IVector2 getPreprocessedTarget()
	{
		return preprocessedDestination;
	}
	
	
	
	public double getSafetyDistanceBall()
	{
		return safetyDistanceBall;
	}
	
	
	
	public boolean isTargetAdjustedBecauseOfBall()
	{
		return targetAdjustedBecauseOfBall;
	}
	
	
	
	public boolean isStartAdjustedBecauseOfBall()
	{
		return startAdjustedBecauseOfBall;
	}
	
	
	
	public SimpleWorldFrame getwFrame()
	{
		return wFrame;
	}
	
	
	
	public boolean isSecondTry()
	{
		return isSecondTry;
	}
	
	
	
	public void setSecondTry(final boolean isSecondTry)
	{
		this.isSecondTry = isSecondTry;
		updateInternalFields();
	}
	
	
	
	public IVector2 getPreprocessedStart()
	{
		return preprocessedStart;
	}
	
	
	
	public void setPreprocessedStart(final IVector2 preprocessedStart)
	{
		this.preprocessedStart = preprocessedStart;
	}
	
	private static class FutureBot extends FutureObstacle
	{
		
		private final BotID bot;
		
		
		private FutureBot(final BotID botID, final IVector2 pos, final IVector2 vel)
		{
			super(pos, vel);
			bot = botID;
		}
		
		
		
		public BotID getBot()
		{
			return bot;
		}
		
	}
	
	private static class FutureObstacle extends Vector2
	{
		private final IVector2 vel;
		
		
		private FutureObstacle(final IVector2 pos, final IVector2 vel)
		{
			super(pos);
			this.vel = vel;
		}
		
		
		
		@SuppressWarnings("unused")
		public IVector2 getVel()
		{
			return vel;
		}
	}
	
}
