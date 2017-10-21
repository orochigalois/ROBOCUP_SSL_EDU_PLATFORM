/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.referee;

import edu.tigers.sumatra.Referee.SSL_Referee.Command;
import edu.tigers.sumatra.math.IVector2;



public interface ICreateRefereeMsgObserver
{
	
	void onSendOwnRefereeMsg(Command cmd, int goalsBlue, int goalsYellow, short timeLeft, long timestamp,
			IVector2 placementPos);
			
			
	
	void onEnableReceive(boolean receive);
}
