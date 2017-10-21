/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.autoreferee.remote.impl;

import static edu.tigers.autoreferee.CheckedRunnable.execAndCatchAll;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.log4j.Logger;

import com.google.protobuf.InvalidProtocolBufferException;

import edu.tigers.autoreferee.engine.RefCommand;
import edu.tigers.autoreferee.remote.ICommandResult;
import edu.tigers.autoreferee.remote.IRefboxRemote;
import edu.tigers.sumatra.RefboxRemoteControl.SSL_RefereeRemoteControlReply;
import edu.tigers.sumatra.RefboxRemoteControl.SSL_RefereeRemoteControlReply.Outcome;
import edu.tigers.sumatra.RefboxRemoteControl.SSL_RefereeRemoteControlRequest;



public class ThreadedTCPRefboxRemote implements IRefboxRemote, Runnable
{
	private static final Logger					log	= Logger.getLogger(ThreadedTCPRefboxRemote.class);
	
	private final String								hostname;
	private final int									port;
	
	private Thread										thread;
	private RefboxRemoteSocket						socket;
	
	private LinkedBlockingDeque<QueueEntry>	commandQueue;
	
	
	
	public ThreadedTCPRefboxRemote(final String hostname, final int port)
	{
		this.hostname = hostname;
		this.port = port;
		
		socket = new RefboxRemoteSocket();
		commandQueue = new LinkedBlockingDeque<>();
		thread = new Thread(this, "RefboxRemoteSenderThread");
	}
	
	
	
	public synchronized void start() throws IOException
	{
		try
		{
			socket.connect(hostname, port);
		} catch (IOException e)
		{
			throw new IOException("Unable to connect to the Refbox: " + e.getMessage(), e);
		}
		thread.start();
	}
	
	
	
	@Override
	public synchronized void stop()
	{
		try
		{
			thread.interrupt();
			thread.join();
		} catch (InterruptedException e)
		{
			log.warn("Error while joining the sending thread", e);
		}
	}
	
	
	private synchronized void reconnect() throws IOException, InterruptedException
	{
		if (Thread.interrupted())
		{
			throw new InterruptedException();
		}
		socket.close();
		socket.connect(hostname, port);
	}
	
	
	@Override
	public ICommandResult sendCommand(final RefCommand command)
	{
		QueueEntry entry = new QueueEntry(command);
		try
		{
			commandQueue.put(entry);
		} catch (InterruptedException e)
		{
			log.error("", e);
		}
		return entry.getResult();
	}
	
	
	@Override
	public void run()
	{
		try
		{
			while (!Thread.interrupted())
			{
				try
				{
					readWriteLoop();
				} catch (IOException e)
				{
					
					try
					{
						log.debug("Reconnecting to refbox after IO error", e);
						reconnect();
					} catch (IOException ex)
					{
						log.error("Unable to reconnect to the refbox", ex);
						break;
					}
				}
			}
		} catch (InterruptedException e)
		{
			log.debug("Interrupted", e);
		}
		
		socket.close();
	}
	
	
	
	private void readWriteLoop() throws InterruptedException, InvalidProtocolBufferException, IOException
	{
		RemoteControlProtobufBuilder pbBuilder = new RemoteControlProtobufBuilder();
		
		while (true)
		{
			if (Thread.interrupted())
			{
				throw new InterruptedException();
			}
			
			QueueEntry entry = null;
			try
			{
				entry = commandQueue.take();
				SSL_RefereeRemoteControlRequest request = pbBuilder.buildRequest(entry.getCmd());
				SSL_RefereeRemoteControlReply reply = socket.sendRequest(request);
				
				if (reply.getOutcome() != Outcome.OK)
				{
					entry.getResult().setFailed();
				} else
				{
					entry.getResult().setSuccessful();
				}
			} catch (Exception e)
			{
				
				if (entry != null)
				{
					QueueEntry lambdaEntry = entry;
					execAndCatchAll(() -> commandQueue.putFirst(lambdaEntry));
				}
				throw e;
			}
		}
	}
	
	private static class QueueEntry
	{
		private final RefCommand			cmd;
		private final CommandResultImpl	result;
		
		
		public QueueEntry(final RefCommand cmd)
		{
			this(cmd, new CommandResultImpl());
		}
		
		
		public QueueEntry(final RefCommand cmd, final CommandResultImpl result)
		{
			this.cmd = cmd;
			this.result = result;
		}
		
		
		
		public RefCommand getCmd()
		{
			return cmd;
		}
		
		
		
		public CommandResultImpl getResult()
		{
			return result;
		}
	}
}
