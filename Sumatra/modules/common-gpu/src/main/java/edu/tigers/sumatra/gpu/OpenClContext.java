/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.gpu;

import static org.jocl.CL.clBuildProgram;
import static org.jocl.CL.clCreateCommandQueue;
import static org.jocl.CL.clCreateContext;
import static org.jocl.CL.clCreateKernel;
import static org.jocl.CL.clCreateProgramWithSource;
import static org.jocl.CL.clReleaseCommandQueue;
import static org.jocl.CL.clReleaseContext;
import static org.jocl.CL.clReleaseKernel;
import static org.jocl.CL.clReleaseProgram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jocl.CL;
import org.jocl.cl_command_queue;
import org.jocl.cl_context;
import org.jocl.cl_context_properties;
import org.jocl.cl_device_id;
import org.jocl.cl_kernel;
import org.jocl.cl_platform_id;
import org.jocl.cl_program;



public class OpenClContext
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final Logger		log				= Logger.getLogger(OpenClContext.class.getName());
	private final List<String>			kernelSource	= new ArrayList<String>();
	private final cl_command_queue	commandQueue;
	private final cl_context			context;
	private cl_kernel						kernel;
												
												
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public OpenClContext()
	{
		cl_platform_id platform = OpenClHandler.getPlatform();
		cl_device_id device = OpenClHandler.getDevice();
		
		// Initialize the context properties
		cl_context_properties contextProperties = new cl_context_properties();
		contextProperties.addProperty(CL.CL_CONTEXT_PLATFORM, platform);
		
		// Create a context for the selected device
		context = clCreateContext(contextProperties, 1, new cl_device_id[] { device }, null, null, null);
		
		// Create a command-queue for the selected device
		commandQueue = clCreateCommandQueue(context, device, 0, null);
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	
	public final cl_kernel createProgram(final String kernelName)
	{
		try
		{
			// Create the program from the source code
			cl_program program = clCreateProgramWithSource(context, kernelSource.size(),
					kernelSource.toArray(new String[kernelSource.size()]), null, null);
					
			// Build the program
			clBuildProgram(program, 0, null, "-cl-single-precision-constant", null, null);
			// Create the kernel
			kernel = clCreateKernel(program, kernelName, null);
			clReleaseProgram(program);
		} catch (Error e)
		{
			log.error("Error while building OpenCL kernel", e);
			return null;
		}
		return kernel;
	}
	
	
	
	public final void loadSourceFile(final String sourceFileName)
	{
		BufferedReader br = null;
		try
		{
			InputStream is = OpenClContext.class.getResourceAsStream(sourceFileName);
			br = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while (true)
			{
				line = br.readLine();
				if (line == null)
				{
					break;
				}
				sb.append(line).append("\n");
			}
			kernelSource.add(sb.toString());
		} catch (IOException e)
		{
			log.error("Could not read kernel: " + sourceFileName, e);
		} finally
		{
			try
			{
				if (br != null)
				{
					br.close();
				}
			} catch (IOException ex)
			{
				log.error("Could not close BufferedReader", ex);
			}
		}
	}
	
	
	
	public final void loadSource(final String sourceCode)
	{
		kernelSource.add(sourceCode);
	}
	
	
	
	public final void close()
	{
		// Release kernel, program, and memory objects
		clReleaseKernel(kernel);
		clReleaseCommandQueue(commandQueue);
		clReleaseContext(context);
		kernelSource.clear();
	}
	
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public final cl_command_queue getCommandQueue()
	{
		return commandQueue;
	}
	
	
	
	public final cl_context getContext()
	{
		return context;
	}
	
	
	
	public final cl_kernel getKernel()
	{
		return kernel;
	}
}