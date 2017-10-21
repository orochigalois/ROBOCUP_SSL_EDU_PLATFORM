/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.data.metis.testutils;

import edu.tigers.sumatra.ai.data.TacticalField;
import edu.tigers.sumatra.ai.data.ballpossession.BallPossession;
import edu.tigers.sumatra.ai.data.ballpossession.EBallPossession;
import edu.tigers.sumatra.ids.BotID;
import edu.tigers.sumatra.ids.ETeamColor;



public class TacticalFieldTestUtils
{
	
	public static TacticalField initializeTacticalFieldJustWithBallPossessionForSide(
			final EBallPossession eBallPossession)
	{
		TacticalField newTacticalField = new TacticalField();
		
		BotID botID = BotID.createBotId(0, ETeamColor.BLUE);
		
		BallPossession ballPossession = new BallPossession();
		ballPossession.setEBallPossession(eBallPossession);
		ballPossession.setTigersId(botID);
		
		if (eBallPossession == EBallPossession.BOTH)
		{
			BotID opponentID = BotID.createBotId(0, ETeamColor.YELLOW);
			ballPossession.setOpponentsId(opponentID);
		}
		
		newTacticalField.setBallPossession(ballPossession);
		
		return newTacticalField;
	}
	
	
	
	public static TacticalField initializeTacticalFieldJustWithPossessingBot(final BotID possessingBot)
	{
		TacticalField newTacticalField = new TacticalField();
		
		BallPossession ballPossession = new BallPossession();
		
		ballPossession.setTigersId(possessingBot);
		ballPossession.setEBallPossession(EBallPossession.WE);
		
		newTacticalField.setBallPossession(ballPossession);
		
		return newTacticalField;
	}
}
