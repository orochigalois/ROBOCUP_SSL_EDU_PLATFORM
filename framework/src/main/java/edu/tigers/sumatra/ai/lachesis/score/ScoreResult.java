/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.ai.lachesis.score;


public class ScoreResult
{
	
	public enum EUsefulness
	{
		
		NEUTRAL(0),
		
		LIMITED(1),
		
		BAD(2);
		private final int	level;
		
		
		private EUsefulness(final int level)
		{
			this.level = level;
		}
		
		
		
		public int getLevel()
		{
			return level;
		}
	}
	
	private final EUsefulness			usefulness;
	private final int						degree;
	
	private static final ScoreResult	DEFAULT	= new ScoreResult(EUsefulness.NEUTRAL, 0);
	
	
	
	public ScoreResult(final EUsefulness usefulness)
	{
		super();
		this.usefulness = usefulness;
		degree = 0;
	}
	
	
	
	public ScoreResult(final EUsefulness usefulness, final int degree)
	{
		super();
		this.usefulness = usefulness;
		this.degree = degree;
	}
	
	
	
	public EUsefulness getUsefulness()
	{
		return usefulness;
	}
	
	
	
	public int getDegree()
	{
		return degree;
	}
	
	
	
	public static ScoreResult defaultResult()
	{
		return DEFAULT;
	}
	
	
	
	public boolean moreUsefulThan(final ScoreResult result)
	{
		if (getUsefulness().getLevel() < result.getUsefulness().getLevel())
		{
			return true;
		}
		if (getUsefulness().getLevel() > result.getUsefulness().getLevel())
		{
			return false;
		}
		return getDegree() < result.degree;
	}
}
