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
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.SocketChannel;

import com.google.protobuf.InvalidProtocolBufferException;

import edu.tigers.sumatra.RefboxRemoteControl.SSL_RefereeRemoteControlReply;
import edu.tigers.sumatra.RefboxRemoteControl.SSL_RefereeRemoteControlRequest;



public class RefboxRemoteSocket
{
	private static final int	INT_FIELD_SIZE	= 4;
	
	private SocketChannel		socket;
	private ByteBuffer			intBuffer;
	
	
	
	public RefboxRemoteSocket()
	{
		intBuffer = ByteBuffer.allocate(INT_FIELD_SIZE);
		intBuffer.order(ByteOrder.BIG_ENDIAN);
	}
	
	
	
	public synchronized void connect(final String hostname, final int port) throws IOException
	{
		socket = SocketChannel.open();
		InetSocketAddress addr = new InetSocketAddress(hostname, port);
		socket.connect(addr);
		socket.setOption(StandardSocketOptions.TCP_NODELAY, true);
	}
	
	
	
	public synchronized void close()
	{
		execAndCatchAll(() -> socket.close());
	}
	
	
	
	public SSL_RefereeRemoteControlReply sendRequest(final SSL_RefereeRemoteControlRequest request) throws IOException,
			InterruptedException, InvalidProtocolBufferException
	{
		try
		{
			writeRequest(request);
			return readReply();
		} catch (AsynchronousCloseException e)
		{
			
			throw new InterruptedException(e.getMessage());
		}
	}
	
	
	private SSL_RefereeRemoteControlReply readReply() throws IOException
	{
		prepareBuf(INT_FIELD_SIZE);
		while (intBuffer.hasRemaining())
		{
			socket.read(intBuffer);
		}
		
		intBuffer.flip();
		int msgLength = intBuffer.getInt();
		
		
		prepareBuf(msgLength);
		while (intBuffer.hasRemaining())
		{
			socket.read(intBuffer);
		}
		intBuffer.flip();
		
		return SSL_RefereeRemoteControlReply.parseFrom(intBuffer.array());
	}
	
	
	private void writeRequest(final SSL_RefereeRemoteControlRequest req) throws IOException
	{
		int totalSize = req.getSerializedSize() + INT_FIELD_SIZE;
		prepareBuf(totalSize);
		
		intBuffer.putInt(req.getSerializedSize());
		intBuffer.put(req.toByteArray());
		intBuffer.flip();
		
		while (intBuffer.hasRemaining())
		{
			socket.write(intBuffer);
		}
	}
	
	
	private void ensureSize(final int size)
	{
		if (intBuffer.capacity() < size)
		{
			intBuffer = ByteBuffer.allocate(size);
		}
	}
	
	
	private void prepareBuf(final int size)
	{
		ensureSize(size);
		intBuffer.clear();
		intBuffer.limit(size);
	}
}
