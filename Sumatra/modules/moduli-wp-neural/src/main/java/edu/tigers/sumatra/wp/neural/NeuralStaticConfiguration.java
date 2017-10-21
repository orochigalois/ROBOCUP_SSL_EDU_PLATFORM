/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.wp.neural;



public class NeuralStaticConfiguration
{
	
	
	public static class NeuralWPConfigs
	{
		
		public static final int	LastNFrames	= 6;
	}
	
	
	public static class BallConfigs
	{
		
		public static final int	OutputLayer				= 5;
		
		public static final int	InputLayer				= ((NeuralWPConfigs.LastNFrames - 1) * OutputLayer) + 1;
		
		public static final int	HiddenLayer				= 1;
		
		public static final int	NeuronsPerHidden		= 10;
		
		public static final int	ID							= -1;
		
		public static final int	ConvertedDataArray	= 6;
	}
	
	
	
	public static class BotConfigs
	{
		
		
		
		public static final int	OutputLayer				= 7;
		
		public static final int	InputLayer				= ((NeuralWPConfigs.LastNFrames - 1) * OutputLayer) + 1;
		
		public static final int	HiddenLayer				= 1;
		
		public static final int	NeuronsPerHidden		= 10;
		
		public static final int	ConvertedDataArray	= 9;
	}
	
	
}
