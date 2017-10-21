/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.network;

import java.io.Closeable;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;



public final class NetworkUtility
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	private static final Logger	log	= Logger.getLogger(NetworkUtility.class.getName());
	
	
	private NetworkUtility()
	{
	}
	
	
	
	public static NetworkInterface chooseNetworkInterface(final String networkStr, final int compareBytes)
	{
		if (networkStr.trim().isEmpty())
		{
			return null;
		}
		
		InetAddress network = null;
		try
		{
			network = InetAddress.getByName(networkStr);
		} catch (UnknownHostException err1)
		{
			log.error("Unknown host: " + networkStr);
			return null;
		}
		
		
		NetworkInterface result = null;
		try
		{
			List<NetworkInterface> ifaces = Collections.list(NetworkInterface.getNetworkInterfaces());
			Iterator<NetworkInterface> it = ifaces.iterator();
			while (it.hasNext() && (result == null))
			{
				NetworkInterface iface = it.next();
				if (!iface.isUp())
				{
					continue;
				}
				
				List<InetAddress> iAddrs = Collections.list(iface.getInetAddresses());
				for (InetAddress addr : iAddrs)
				{
					if (addr == null)
					{
						continue;
					}
					
					if (cmpIP4Addrs(addr, network, compareBytes))
					{
						result = iface;
						break;
					}
				}
			}
		} catch (SocketException err)
		{
			log.error("Error retrieving network-interfaces!", err);
		}
		
		return result;
	}
	
	
	private static boolean cmpIP4Addrs(final InetAddress addr1, final InetAddress addr2, final int bytesToCompare)
	{
		byte[] addr1b = addr1.getAddress();
		byte[] addr2b = addr2.getAddress();
		
		if ((addr1b.length != 4) || (addr2b.length != 4))
		{
			return false;
		}
		
		for (int i = 0; i < bytesToCompare; i++)
		{
			if (addr1b[i] != addr2b[i])
			{
				return false;
			}
		}
		
		return true;
	}
	
	
	
	public static void closeQuietly(final Closeable c)
	{
		if (c != null)
		{
			try
			{
				c.close();
			} catch (IOException err)
			{
				// ignore
			}
		}
	}
}
