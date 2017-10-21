/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.paramoptimizer.redirect;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;

import edu.tigers.sumatra.bot.EBotType;
import edu.tigers.sumatra.bot.IBot;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.math.AngleMath;
import edu.tigers.sumatra.math.GeoMath;
import edu.tigers.sumatra.math.ILine;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.IVector3;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.math.MathException;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.math.Vector3;
import edu.tigers.sumatra.paramoptimizer.redirect.RedirectDetector.RedirectDataSet;
import edu.tigers.sumatra.wp.data.DynamicPosition;
import edu.tigers.sumatra.wp.data.Geometry;
import edu.tigers.sumatra.wp.data.ITrackedBot;
import edu.tigers.sumatra.wp.data.SimpleWorldFrame;
import edu.tigers.sumatra.wp.data.TrackedBall;



public class RedirectParamCalc
{
	@SuppressWarnings("unused")
	private static final Logger							log										= Logger
			.getLogger(
					RedirectParamCalc.class
							.getName());
	
	
	private static final double							APPROX_ORIENT_BALL_DAMP_ACCURACY	= 0.005;
	
	private static final int								APPROX_ORIENT_BALL_DAMP_MAX_ITER	= 100;
	
	private static final double							BALL_DAMP_FACTOR						= 0.37;
	
	private static final double							REDIRECT_MAX_DIST_DIFF				= 1000;
	
	private static Map<EBotType, RedirectParamCalc>	instances								= new HashMap<>();
	
	private Map<BotID, IVector2>							shootVectors							= new HashMap<>();
	
	private static final int								ANGLE_CORRECTION_SIZE				= 1;
	private double[]											angleCorrectionOffset				= new double[ANGLE_CORRECTION_SIZE];
	private double[]											kickSpeedCorrectionOffset			= new double[ANGLE_CORRECTION_SIZE];
	private double												ballDampFactorOffset					= 0;
	
	
	private RedirectParamCalc()
	{
	}
	
	
	
	public static RedirectParamCalc forBot(final IBot bot)
	{
		synchronized (instances)
		{
			RedirectParamCalc c = instances.get(bot.getType());
			if (c == null)
			{
				c = new RedirectParamCalc();
				instances.put(bot.getType(), c);
			}
			return c;
		}
	}
	
	
	
	public void update(final RedirectDataSet ds)
	{
		IVector2 vecInInv = ds.getVelIn().multiplyNew(-1);
		Optional<IVector2> shootDir = getShootVector(ds.getBot().getBotId());
		if (!shootDir.isPresent())
		{
			return;
		}
		double desiredAngle = GeoMath.angleBetweenVectorAndVector(vecInInv, shootDir.get());
		double actualAngle = GeoMath.angleBetweenVectorAndVector(vecInInv, ds.getVelOut());
		
		double angleDiff = AngleMath.normalizeAngle(desiredAngle - actualAngle);
		double speedDiff = shootDir.get().getLength2() - ds.getVelOut().getLength2();
		
		int idx = getCorrectionIdx(desiredAngle);
		angleCorrectionOffset[idx] += angleDiff * 0.0;
		kickSpeedCorrectionOffset[idx] += speedDiff * 0.0;
		ballDampFactorOffset += speedDiff * 0.0;
		ballDampFactorOffset = -0.20;
		
		for (int i = 0; i < ANGLE_CORRECTION_SIZE; i++)
		{
			kickSpeedCorrectionOffset[i] = 1;
		}
		
		// DecimalFormat df = new DecimalFormat("0.000");
		// log.info("angle: " + df.format(desiredAngle) +
		// " angleDiff: " + df.format(angleDiff) +
		// " speedDiff: " + df.format(speedDiff));
		
		// log.info("ballDampFactorOffset: " + ballDampFactorOffset);
		
		// log.info("kickSpeedCorrectionOffset[0]: " + kickSpeedCorrectionOffset[0]);
		
		// log.info("\nangleCorr="
		// + Arrays.toString(angleCorrectionOffset) + "\nspeedCorr=" +
		// Arrays.toString(kickSpeedCorrectionOffset));
		
		// System.out.println(desiredAngle + " " + actualAngle);
	}
	
	
	private double getBallDampingFactor()
	{
		return Math.max(0, Math.min(1, BALL_DAMP_FACTOR + ballDampFactorOffset));
	}
	
	
	
	public double getRequiredBallSpeed(final IVector2 from, final DynamicPosition to, final double passReceivingSpeed)
	{
		double desiredBallSpeed;
		if (to.getTrackedId().isBot())
		{
			double dist = GeoMath.distancePP(from, to);
			desiredBallSpeed = Geometry.getBallModel().getVelForDist(dist, passReceivingSpeed);
		} else
		{
			desiredBallSpeed = 8;
		}
		return desiredBallSpeed;
	}
	
	
	
	public double getKickSpeed(final SimpleWorldFrame swf, final BotID botId, final DynamicPosition target,
			final double passReceivingSpeed)
	{
		ITrackedBot tBot = swf.getBot(botId);
		double inSpeed = swf.getBall().getVelByPos(tBot.getBotKickerPos());
		double dampedSpeed = inSpeed * (1 - getBallDampingFactor());
		double requiredBallSpeed = getRequiredBallSpeed(tBot.getBotKickerPos(), target, passReceivingSpeed);
		double desiredAngle = GeoMath.angleBetweenVectorAndVector(swf.getBall().getVel().multiplyNew(-1),
				target.subtractNew(tBot.getBotKickerPos()));
		double kickSpeed = (requiredBallSpeed - dampedSpeed) + kickSpeedCorrectionOffset[getCorrectionIdx(desiredAngle)];
		kickSpeed = Math.min(8, Math.max(0, kickSpeed));
		return kickSpeed;
	}
	
	
	
	public double calcRedirectOrientation(final IVector2 kickerPos, final double approxOrientation,
			final IVector2 ballVel, final IVector2 shootTarget, final double shootSpeed)
	{
		double shootAngle = shootTarget.subtractNew(kickerPos).getAngle();
		if (ballVel.isZeroVector())
		{
			return shootAngle;
		}
		return approxOrientation(shootSpeed, ballVel, approxOrientation, shootAngle, getBallDampingFactor());
	}
	
	
	
	public double calcRedirectAngle(final IVector2 kickerPos, final IVector2 receiver, final IVector2 vBall,
			final double shootSpeed)
	{
		double approxOrient = 0;
		if (!kickerPos.equals(receiver))
		{
			approxOrient = receiver.subtractNew(kickerPos).getAngle();
		}
		
		double targetAngle = calcRedirectOrientation(kickerPos, approxOrient, vBall, receiver, shootSpeed);
		double ballAngle;
		if (vBall.isZeroVector())
		{
			ballAngle = targetAngle;
		} else
		{
			ballAngle = AngleMath.normalizeAngle(vBall.getAngle() + AngleMath.PI);
		}
		double redirectAngle = Math.abs(AngleMath.difference(ballAngle, targetAngle));
		return redirectAngle;
	}
	
	
	
	public IVector3 calcRedirectPose(final ITrackedBot bot, final IVector2 botDesPos,
			final double botDesAngle,
			final TrackedBall ball,
			final IVector2 shootTarget, final double shootSpeed)
	{
		double center2DribblerDist = bot.getCenter2DribblerDist();
		double center2BallHit = center2DribblerDist + Geometry.getBallRadius();
		IVector2 ballVel = ball.getVel();
		// IVector2 ballVel = GeoMath.getBotKickerPos(bot.getPos(), bot.getAngle(), center2BallHit)
		// .subtractNew(ball.getPos()).scaleTo(2);
		IVector2 kickerPos;
		ILine ballTravelLine;
		if (ballVel.getLength2() > 0.1)
		{
			kickerPos = GeoMath.getBotKickerPos(botDesPos, bot.getAngle(), center2BallHit);
			ballTravelLine = new Line(ball.getPos(), ballVel);
			IVector2 lp = GeoMath.leadPointOnLine(bot.getPos(), ballTravelLine);
			if (GeoMath.distancePP(lp, bot.getPos()) > REDIRECT_MAX_DIST_DIFF)
			{
				kickerPos = GeoMath.getBotKickerPos(botDesPos, botDesAngle, center2BallHit);
				ballTravelLine = Line.newLine(ball.getPos(), kickerPos);
			}
		} else
		{
			kickerPos = GeoMath.getBotKickerPos(botDesPos, botDesAngle, center2BallHit);
			ballTravelLine = Line.newLine(ball.getPos(), kickerPos);
		}
		IVector2 leadPoint = GeoMath.leadPointOnLine(kickerPos, ballTravelLine);
		
		if (!Geometry.getField().isPointInShape(leadPoint))
		{
			try
			{
				leadPoint = Geometry.getField().getNearIntersectionPoint(ballTravelLine);
			} catch (MathException e)
			{
				// backup
				leadPoint = Geometry.getField().nearestPointInside(leadPoint, -110);
			}
		}
		
		double approxOrientation = botDesAngle;
		double targetOrientation = calcRedirectOrientation(leadPoint, approxOrientation, ballVel, shootTarget,
				shootSpeed);
		
		IVector2 dir = new Vector2(targetOrientation).scaleTo(-center2BallHit);
		IVector2 dest = leadPoint.addNew(dir);
		
		// filter high changes
		if (GeoMath.distancePP(botDesPos, dest) > REDIRECT_MAX_DIST_DIFF)
		{
			dest = botDesPos;
		}
		if (Math.abs(AngleMath.difference(targetOrientation, botDesAngle)) > AngleMath.PI_HALF)
		{
			targetOrientation = botDesAngle;
		}
		
		
		IVector3 positions = new Vector3(dest, targetOrientation);
		
		return positions;
	}
	
	
	
	public IVector3 calcRedirectPose(final ITrackedBot bot, final TrackedBall ball,
			final IVector2 shootTarget, final double shootSpeed)
	{
		return calcRedirectPose(bot, bot.getPos(), bot.getAngle(), ball, shootTarget, shootSpeed);
	}
	
	
	
	public double approxOrientation(final double shootSpeed, final IVector2 incomingSpeedVec,
			final double initialOrientation,
			final double targetAngle, final double ballDampFactor)
	{
		double destAngle = initialOrientation;
		// FIXME this should also be possible without approximation?!
		for (int i = 0; i < APPROX_ORIENT_BALL_DAMP_MAX_ITER; i++)
		{
			IVector2 vShootSpeed = new Vector2(destAngle).scaleTo(shootSpeed + 1e-5);
			IVector2 outVec = ballDamp(vShootSpeed, incomingSpeedVec, ballDampFactor, targetAngle);
			double diff = AngleMath.difference(targetAngle, outVec.getAngle());
			if (Math.abs(diff) < APPROX_ORIENT_BALL_DAMP_ACCURACY)
			{
				break;
			}
			destAngle = AngleMath.normalizeAngle(destAngle + diff);
		}
		return destAngle;
	}
	
	
	
	public IVector2 ballDamp(final IVector2 shootSpeed, final IVector2 incomingSpeedVec,
			final double ballDampFactor, final double targetAngle)
	{
		// incoming angle equal to outgoing angle
		IVector2 vecInInv = incomingSpeedVec.multiplyNew(-1);
		double diff = AngleMath.difference(shootSpeed.getAngle(), vecInInv.getAngle());
		double desiredAngle = Math
				.abs(AngleMath.getShortestRotation(targetAngle, incomingSpeedVec.multiplyNew(-1).getAngle()));
		double corr = Math.signum(diff) * angleCorrectionOffset[getCorrectionIdx(desiredAngle)];
		Vector2 outVec = new Vector2(
				vecInInv.getAngle() + ((diff * 2) + corr))
						.scaleTo(incomingSpeedVec.getLength2());
		
		// ball is damped by hit
		outVec.multiply(1 - ballDampFactor);
		
		// ball is drilled to shoot direction
		IVector2 dampVec = shootSpeed;
		outVec.add(dampVec);
		
		return outVec;
	}
	
	
	private int getCorrectionIdx(final double desiredAngle)
	{
		return (int) Math.min(ANGLE_CORRECTION_SIZE - 1,
				Math.round((desiredAngle / AngleMath.PI_HALF) * ANGLE_CORRECTION_SIZE));
	}
	
	
	
	public void setShootVector(final BotID botId, final IVector2 shootVector)
	{
		shootVectors.put(botId, shootVector);
	}
	
	
	
	public Optional<IVector2> getShootVector(final BotID botId)
	{
		return Optional.ofNullable(shootVectors.get(botId));
	}
}
