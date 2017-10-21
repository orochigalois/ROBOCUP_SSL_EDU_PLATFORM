/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import com.github.g3force.configurable.ConfigRegistration;
import com.github.g3force.configurable.Configurable;
import com.github.g3force.configurable.IConfigClient;
import com.github.g3force.configurable.IConfigObserver;

import edu.tigers.sumatra.MessagesRobocupSslGeometry.SSL_GeometryFieldSize;
import edu.tigers.sumatra.cam.data.CamCalibration;
import edu.tigers.sumatra.cam.data.CamFieldSize;
import edu.tigers.sumatra.cam.data.CamGeometry;
import edu.tigers.sumatra.ids.ETeam;
import edu.tigers.sumatra.math.AVector2;
import edu.tigers.sumatra.math.IVector2;
import edu.tigers.sumatra.math.Line;
import edu.tigers.sumatra.math.Vector2;
import edu.tigers.sumatra.math.Vector2f;
import edu.tigers.sumatra.model.SumatraModel;
import edu.tigers.sumatra.shapes.circle.Circle;
import edu.tigers.sumatra.shapes.rectangle.Rectangle;



public class Geometry
{
	@Configurable(spezis = { "GRSIM", "SUMATRA", "LAB", "TISCH", "ROBOCUP" })
	private static double							fieldLength								= 8090;
	@Configurable(spezis = { "GRSIM", "SUMATRA", "LAB", "TISCH", "ROBOCUP" })
	private static double							fieldWidth								= 6050;
	@Configurable(spezis = { "GRSIM", "SUMATRA", "LAB", "TISCH", "ROBOCUP" })
	private static double							boundaryWidth							= 350;
	@Configurable(spezis = { "GRSIM", "SUMATRA", "LAB", "TISCH", "ROBOCUP" })
	private static double							boundaryLength							= 350;
	@Configurable(spezis = { "GRSIM", "SUMATRA", "LAB", "TISCH", "ROBOCUP" })
	private static double							boundaryOffset							= 0;
	@Configurable(spezis = { "GRSIM", "SUMATRA", "LAB", "TISCH", "ROBOCUP" })
	private static double							judgesBorderWidth						= 425;
	@Configurable(spezis = { "GRSIM", "SUMATRA", "LAB", "TISCH", "ROBOCUP" })
	private static double							judgesBorderLength					= 425;
	@Configurable(spezis = { "GRSIM", "SUMATRA", "LAB", "TISCH",
			"ROBOCUP" }, comment = "Distance (goal line - penalty mark)")
	private static double							distanceToPenaltyMark				= 1000;
	@Configurable(spezis = { "GRSIM", "SUMATRA", "LAB", "TISCH",
			"ROBOCUP" }, comment = "radius of the two, small quarter circles at the sides of the penalty area.")
	private static double							distanceToPenaltyArea				= 1000;
	@Configurable(spezis = { "GRSIM", "SUMATRA", "LAB", "TISCH",
			"ROBOCUP" }, comment = "the length of the short line of the penalty area, that is parallel to the goal line")
	private static double							lengthOfPenaltyAreaFrontLine		= 500;
	@Configurable(spezis = { "GRSIM", "SUMATRA", "LAB", "TISCH", "ROBOCUP" })
	private static double							goalSize									= 1000;
	@Configurable(spezis = { "GRSIM", "SUMATRA", "LAB", "TISCH", "ROBOCUP" })
	private static double							centerCircleRadius					= 1000;
	@Configurable(spezis = { "GRSIM", "SUMATRA", "LAB", "TISCH", "ROBOCUP" })
	private static String							ballModelIdentifier					= "default";
	
	
	@Configurable
	private static double							ballRadius								= 21.5;
	@Configurable
	private static double							botRadius								= 90;
	@Configurable
	private static double							stopSpeed								= 1.5;
	@Configurable
	private static double							botToBallDistanceStop				= 500;
	@Configurable
	private static double							goalDepth								= 180;
	@Configurable(comment = "Bots must be behind this line on penalty shot")
	private static double							distancePenaltyMarkToPenaltyLine	= 400;
	
	@Configurable
	private static double							penaltyAreaMargin						= 100;
	@Configurable
	private static double							center2DribblerDistDefault			= 75;
	
	@Configurable
	private static Double[]							cameraHeights							= new Double[] { 3500.0, 3500.0, 3500.0,
			3500.0 };
	@Configurable
	private static Double[]							cameraFocalLength						= new Double[] { 0.0, 0.0, 0.0, 0.0 };
	@Configurable
	private static Double[]							cameraPrincipalPointX				= new Double[] { 0.0, 0.0, 0.0, 0.0 };
	@Configurable
	private static Double[]							cameraPrincipalPointY				= new Double[] { 0.0, 0.0, 0.0, 0.0 };
	
	
	private static Map<Integer, RealMatrix>	W											= new HashMap<>();
	
	@Configurable(comment = "If true, Geometry will be refreshed with data from vision, if available.")
	private static boolean							receiveGeometry						= true;
	
	
	
	private final Rectangle							field;
	
	private final Rectangle							fieldWBorders;
	
	private final Rectangle							fieldWReferee;
	
	private final Goal								goalOur;
	
	private final Goal								goalTheir;
	
	private final Line								goalLineOur;
	
	private final Line								goalLineTheir;
	
	private final PenaltyArea						penaltyAreaOur;
	
	private final PenaltyArea						penaltyAreaTheir;
	
	private final ExtendedPenaltyArea			penaltyAreaOurExt;
	
	private final ExtendedPenaltyArea			penaltyAreaTheirExt;
	
	private final Rectangle							penaltyKickAreaOur;
	
	private final Rectangle							penaltyKickAreaTheir;
	
	private final Vector2f							penaltyMarkOur;
	
	private final Vector2f							penaltyMarkTheir;
	
	private final Vector2f							penaltyLineOur;
	
	private final Vector2f							penaltyLineTheir;
	
	private final IVector2							center									= Vector2.ZERO_VECTOR;
	
	private final Circle								centerCircle;
	
	private final Rectangle							ourHalf;
	
	private final Rectangle							theirHalf;
	
	private final LearnedBallModel				ballModel;
	
	
	private static class ConfigCallback implements IConfigObserver
	{
		@Override
		public void afterApply(final IConfigClient configClient)
		{
			instance = new Geometry();
		}
	}
	
	
	static
	{
		ConfigRegistration.registerClass("geom", Geometry.class);
		ConfigRegistration.registerConfigurableCallback("geom", new ConfigCallback());
	}
	
	private static Geometry instance = new Geometry();
	
	
	
	private Geometry()
	{
		field = calcField(center, fieldLength, fieldWidth);
		fieldWBorders = calcField(center, fieldLength + ((boundaryOffset + boundaryLength) * 2),
				fieldWidth + ((boundaryOffset + boundaryWidth) * 2));
		fieldWReferee = calcField(center,
				fieldLength + ((boundaryOffset + boundaryLength) * 2) + (judgesBorderLength * 2), fieldWidth
						+ ((boundaryOffset + boundaryWidth) * 2) + (judgesBorderWidth * 2));
		goalOur = calcOurGoal(goalSize, fieldLength);
		goalTheir = calcTheirGoal(goalSize, fieldLength);
		goalLineOur = calcGoalLine(goalOur.getGoalCenter(), AVector2.Y_AXIS);
		goalLineTheir = calcGoalLine(goalTheir.getGoalCenter(), AVector2.Y_AXIS);
		penaltyAreaOur = new PenaltyArea(ETeam.TIGERS);
		penaltyAreaTheir = new PenaltyArea(ETeam.OPPONENTS);
		penaltyAreaOurExt = new ExtendedPenaltyArea(penaltyAreaOur);
		penaltyAreaTheirExt = new ExtendedPenaltyArea(penaltyAreaTheir);
		
		penaltyMarkOur = calcOurPenalityMark(fieldLength, distanceToPenaltyMark);
		penaltyMarkTheir = calcTheirPenalityMark(fieldLength, distanceToPenaltyMark);
		centerCircle = calcCenterCircle(center, centerCircleRadius);
		penaltyLineOur = calcOurPenalityLine(fieldLength, distanceToPenaltyMark, distancePenaltyMarkToPenaltyLine);
		penaltyLineTheir = calcTheirPenalityLine(fieldLength, distanceToPenaltyMark, distancePenaltyMarkToPenaltyLine);
		penaltyKickAreaOur = calcOurPenaltyKickArea(center, fieldLength, fieldWidth, distanceToPenaltyMark,
				distancePenaltyMarkToPenaltyLine);
		penaltyKickAreaTheir = calcTheirPenaltyKickArea(center, fieldLength, fieldWidth, distanceToPenaltyMark,
				distancePenaltyMarkToPenaltyLine);
		
		ourHalf = new Rectangle(field.topLeft(), field.xExtend() / 2, field.yExtend());
		theirHalf = new Rectangle(field.topLeft().addNew(new Vector2(field.xExtend() / 2, 0)), field.xExtend() / 2,
				field.yExtend());
		
		ballModel = new LearnedBallModel(ballModelIdentifier);
		
		// backup geometry RoboCup16
		Map<Integer, CamCalibration> calibs = new HashMap<>();
		// calibs.put(0, new CamCalibration(0, 621.75726, 368.72076, 276.2885, 0.69, 0.999156, -0.014069, 0.007252,
		// 0.037916,
		// -2049.5896, 1792.9927, 3994.0, 2035.8442, 1426.5729, 4145.822));
		// calibs.put(1, new CamCalibration(1, 650.6327, 360.5548, 278.36945, 0.59, -0.008287, -0.999845, 0.015501,
		// -0.001159, -2074.1401, 1809.0927, 4006.0, -2093.4487, -1649.4312, 4064.4473));
		// calibs.put(2, new CamCalibration(2, 641.35376, 385.25162, 312.854, 0.69, 0.008653, 0.998264, 0.050858,
		// 0.028412,
		// -2293.9917, -1684.9551, 4006.0, -2032.2515, 1300.2507, 4281.219));
		// calibs.put(3, new CamCalibration(3, 660.6845, 418.0124, 245.19305, 0.71, -0.010074, -0.999012, 0.042813,
		// 0.006441,
		// 2139.559, 1930.1101, 4006.0, 2050.931, -1621.3298, 4185.2085));
		
		// lab
		calibs.put(0, new CamCalibration(0, 583.5178, 393.20038, 307.02777, 0.28, 0.016409, -0.999801, 0.006034, 0.009562,
				1296.7423, -1035.8273, 2530.0, 0, 0, 0));
		calibs.put(1, new CamCalibration(1, 594.1432, 424.6054, 290.1744, 0.28, -0.009056, 0.999668, -0.005048, -0.023585,
				1308.2275, 944.46234, 2530.0, 0, 0, 0));
		calibs.put(2, new CamCalibration(2, 545.9973, 391.43784, 286.54358, 0.24, -0.998606, 4.59E-4, -0.051523, 0.011438,
				1621.8491, -1042.7896, 2530.0, 0, 0, 0));
		calibs.put(3, new CamCalibration(3, 543.0115, 390.5568, 308.23514, 0.25, 0.998494, 0.019138, 0.047846, -0.018807,
				1641.2815, 1019.6022, 2520.0, 0, 0, 0));
		CamGeometry geometry = new CamGeometry(calibs, new CamFieldSize(SSL_GeometryFieldSize.getDefaultInstance()));
		calcW(geometry);
	}
	
	
	
	public static void setCamDetection(final CamGeometry geometry)
	{
		boundaryLength = geometry.getField().getBoundaryWidth();
		boundaryWidth = geometry.getField().getBoundaryWidth();
		fieldLength = geometry.getField().getFieldLength();
		fieldWidth = geometry.getField().getFieldWidth();
		goalSize = geometry.getField().getGoalWidth();
		goalDepth = geometry.getField().getGoalDepth();
		
		// TODO extract penalty area
		calcW(geometry);
		
		instance = new Geometry();
	}
	
	
	private static void calcW(final CamGeometry geometry)
	{
		for (CamCalibration calib : geometry.getCalibrations().values())
		{
			Geometry.getCameraFocalLength()[calib.getCameraId()] = calib.getFocalLength();
			Geometry.getCameraPrincipalPointX()[calib.getCameraId()] = calib.getPrincipalPointX();
			Geometry.getCameraPrincipalPointY()[calib.getCameraId()] = calib.getPrincipalPointY();
			
			
			Rotation rotation = new Rotation(calib.getQ3(), calib.getQ0(), calib.getQ1(), calib.getQ2(), false);
			RealMatrix R = MatrixUtils.createRealMatrix(rotation.getMatrix()).transpose();
			RealVector t = MatrixUtils.createRealVector(new double[] { calib.getTx(), calib.getTy(), calib.getTz() });
			RealMatrix H = MatrixUtils.createRealMatrix(3, 4);
			// H = [obj.R obj.t];
			H.setSubMatrix(R.getData(), 0, 0);
			H.setColumn(3, t.toArray());
			
			// K = [obj.f 0 obj.p(1); 0 obj.f obj.p(2); 0 0 1];
			RealMatrix K = MatrixUtils
					.createRealMatrix(new double[][] { { calib.getFocalLength(), 0, calib.getPrincipalPointX() },
							{ 0, calib.getFocalLength(), calib.getPrincipalPointY() },
							{ 0, 0, 1 } });
			
			W.put(calib.getCameraId(), K.multiply(H));
		}
	}
	
	
	
	public static RealMatrix getW(final int cameraId)
	{
		return W.get(cameraId);
	}
	
	
	
	public static void refresh()
	{
		String env = SumatraModel.getInstance().getGlobalConfiguration().getString("environment");
		ConfigRegistration.applySpezi("geom", env);
		instance = new Geometry();
	}
	
	
	private Rectangle calcField(final IVector2 center, final double fieldLength, final double fieldWidth)
	{
		return new Rectangle(center.addNew(new Vector2f(-fieldLength / 2, fieldWidth / 2.0)), fieldLength, fieldWidth);
	}
	
	
	private Goal calcOurGoal(final double goalSize, final double fieldLength)
	{
		return new Goal(goalSize, new Vector2f(-fieldLength / 2, 0), -getGoalDepth());
	}
	
	
	private Goal calcTheirGoal(final double goalSize, final double fieldLength)
	{
		return new Goal(goalSize, new Vector2f(fieldLength / 2, 0), getGoalDepth());
	}
	
	
	private Line calcGoalLine(final IVector2 goalCenter, final IVector2 dir)
	{
		return new Line(goalCenter, dir);
	}
	
	
	private Circle calcCenterCircle(final IVector2 center, final double radius)
	{
		return new Circle(center, radius);
	}
	
	
	private Vector2f calcOurPenalityMark(final double fieldLength, final double distanceToPenaltyMark)
	{
		return new Vector2f((-fieldLength / 2.0) + distanceToPenaltyMark, 0);
	}
	
	
	private Vector2f calcTheirPenalityMark(final double fieldLength, final double distanceToPenaltyMark)
	{
		return new Vector2f((fieldLength / 2.0) - distanceToPenaltyMark, 0);
	}
	
	
	private Vector2f calcTheirPenalityLine(final double fieldLength, final double distanceToPenaltyMark,
			final double distanceToPenaltyLine)
	{
		return new Vector2f((fieldLength / 2.0) - distanceToPenaltyMark - distanceToPenaltyLine, 0);
	}
	
	
	private Vector2f calcOurPenalityLine(final double fieldLength, final double distanceToPenaltyMark,
			final double distanceTopenaltyLine)
	{
		return new Vector2f((-fieldLength / 2.0) + distanceToPenaltyMark + distanceTopenaltyLine, 0);
	}
	
	
	private Rectangle calcOurPenaltyKickArea(final IVector2 center, final double fieldLength, final double fieldWidth,
			final double distanceToPenaltyMark, final double distancePenaltyMarkToPenaltyLine)
	{
		return new Rectangle(center.addNew(new Vector2f(-fieldLength / 2, fieldWidth / 2)), distanceToPenaltyMark
				+ distancePenaltyMarkToPenaltyLine, fieldWidth);
	}
	
	
	private Rectangle calcTheirPenaltyKickArea(final IVector2 center, final double fieldLength, final double fieldWidth,
			final double distanceToPenaltyMark, final double distancePenaltyMarkToPenaltyLine)
	{
		return new Rectangle(center.addNew(new Vector2f(
				(fieldLength / 2) - (distanceToPenaltyMark + distancePenaltyMarkToPenaltyLine), fieldWidth / 2)),
				distanceToPenaltyMark + distancePenaltyMarkToPenaltyLine, fieldWidth);
	}
	
	
	
	public static Goal getGoalOur()
	{
		return instance.goalOur;
	}
	
	
	
	public static Goal getGoalTheir()
	{
		return instance.goalTheir;
	}
	
	
	
	public static double getBallRadius()
	{
		return ballRadius;
	}
	
	
	
	public static double getStopSpeed()
	{
		return stopSpeed;
	}
	
	
	
	public static double getBotRadius()
	{
		return botRadius;
	}
	
	
	
	public static double getFieldLength()
	{
		return fieldLength;
	}
	
	
	
	public static double getFieldWidth()
	{
		return fieldWidth;
	}
	
	
	
	public static Rectangle getField()
	{
		return instance.field;
	}
	
	
	
	public static Rectangle getFieldWBorders()
	{
		return instance.fieldWBorders;
	}
	
	
	
	public static Rectangle getFieldWReferee()
	{
		return instance.fieldWReferee;
	}
	
	
	
	public static Line getGoalLineOur()
	{
		return instance.goalLineOur;
	}
	
	
	
	public static Line getGoalLineTheir()
	{
		return instance.goalLineTheir;
	}
	
	
	
	public static double getDistanceToPenaltyMark()
	{
		return distanceToPenaltyMark;
	}
	
	
	
	public static Vector2f getPenaltyMarkOur()
	{
		return instance.penaltyMarkOur;
	}
	
	
	
	public static Vector2f getPenaltyMarkTheir()
	{
		return instance.penaltyMarkTheir;
	}
	
	
	
	public static IVector2 getCenter()
	{
		return instance.center;
	}
	
	
	
	public static double getGoalSize()
	{
		return goalSize;
	}
	
	
	
	public static double getGoalDepth()
	{
		return goalDepth;
	}
	
	
	
	public static double getCenterCircleRadius()
	{
		return centerCircleRadius;
	}
	
	
	
	public static Circle getCenterCircle()
	{
		return instance.centerCircle;
	}
	
	
	
	public static double getDistanceToPenaltyArea()
	{
		return distanceToPenaltyArea;
	}
	
	
	
	public static double getLengthOfPenaltyAreaFrontLine()
	{
		return lengthOfPenaltyAreaFrontLine;
	}
	
	
	
	public static PenaltyArea getPenaltyAreaOur()
	{
		return instance.penaltyAreaOur;
	}
	
	
	
	public static PenaltyArea getPenaltyAreaTheir()
	{
		return instance.penaltyAreaTheir;
	}
	
	
	
	public static ExtendedPenaltyArea getPenaltyAreaOurExtended()
	{
		return instance.penaltyAreaOurExt;
	}
	
	
	
	public static ExtendedPenaltyArea getPenaltyAreaTheirExtended()
	{
		return instance.penaltyAreaTheirExt;
	}
	
	
	
	public static Rectangle getHalfOur()
	{
		return instance.ourHalf;
	}
	
	
	
	public static Rectangle getHalfTheir()
	{
		return instance.theirHalf;
	}
	
	
	
	public static Vector2f getPenaltyLineOur()
	{
		return instance.penaltyLineOur;
	}
	
	
	
	public static Vector2f getPenaltyLineTheir()
	{
		return instance.penaltyLineTheir;
	}
	
	
	
	public static double getBoundaryWidth()
	{
		return boundaryWidth + boundaryOffset;
	}
	
	
	
	public static double getDistancePenaltyMarkToPenaltyLine()
	{
		return distancePenaltyMarkToPenaltyLine;
	}
	
	
	
	public static double getBotToBallDistanceStop()
	{
		return botToBallDistanceStop;
	}
	
	
	
	public static final double getBoundaryLength()
	{
		return boundaryLength + boundaryOffset;
	}
	
	
	
	public static double getJudgesBorderWidth()
	{
		return judgesBorderWidth;
	}
	
	
	
	public static double getJudgesBorderLength()
	{
		return judgesBorderLength;
	}
	
	
	
	public static double getPenaltyAreaMargin()
	{
		return penaltyAreaMargin;
	}
	
	
	
	public static final double getCenter2DribblerDistDefault()
	{
		return center2DribblerDistDefault;
	}
	
	
	
	public static Double[] getCameraHeights()
	{
		return cameraHeights;
	}
	
	
	
	public static Double[] getCameraFocalLength()
	{
		return cameraFocalLength;
	}
	
	
	
	public static Double[] getCameraPrincipalPointX()
	{
		return cameraPrincipalPointX;
	}
	
	
	
	public static Double[] getCameraPrincipalPointY()
	{
		return cameraPrincipalPointY;
	}
	
	
	
	public static boolean isReceiveGeometry()
	{
		return receiveGeometry;
	}
	
	
	
	public static String getBallModelIdentifier()
	{
		return ballModelIdentifier;
	}
	
	
	
	public static LearnedBallModel getBallModel()
	{
		return instance.ballModel;
	}
	
	
	
	public static Rectangle getPenaltyKickAreaOur()
	{
		return instance.penaltyKickAreaOur;
	}
	
	
	
	public static Rectangle getPenaltyKickAreaTheir()
	{
		return instance.penaltyKickAreaTheir;
	}
	
	
	
	public static IVector2 getCornerLeftOur()
	{
		double xPos = -Geometry.fieldLength / 2.;
		double yPos = Geometry.fieldWidth / 2.;
		
		return new Vector2(xPos, yPos);
	}
	
	
	
	public static IVector2 getCornerRightOur()
	{
		double xPos = -Geometry.fieldLength / 2.;
		double yPos = -Geometry.fieldWidth / 2.;
		
		return new Vector2(xPos, yPos);
	}
}
