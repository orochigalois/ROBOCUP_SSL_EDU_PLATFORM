/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/
package edu.tigers.autoreferee.remote;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import edu.tigers.autoreferee.engine.RefCommand;
import edu.tigers.autoreferee.remote.impl.ThreadedTCPRefboxRemote;
import edu.tigers.sumatra.Referee.SSL_Referee.Command;





public class RemoteTest
{
	
	
	@Test
	@Ignore
	public void test() throws IOException, InterruptedException
	{
		ThreadedTCPRefboxRemote remote = new ThreadedTCPRefboxRemote("localhost", 10007);
		remote.start();
		for (int i = 0; i < 10; i++)
		{
			remote.sendCommand(new RefCommand(Command.DIRECT_FREE_BLUE));
			Thread.sleep(1000);
			remote.sendCommand(new RefCommand(Command.STOP));
			Thread.sleep(1000);
		}
		remote.stop();
	}
	
}
