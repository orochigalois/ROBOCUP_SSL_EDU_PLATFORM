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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.tigers.sumatra.ai.data.frames.MetisAiFrame;
import edu.tigers.sumatra.ai.lachesis.score.ScoreResult.EUsefulness;
import edu.tigers.sumatra.ai.pandora.roles.ARole;
import edu.tigers.sumatra.wp.data.ITrackedBot;



public abstract class AScore
{
	private boolean	active	= true;
	
	
	
	public void setActive(final boolean active)
	{
		this.active = active;
	}
	
	
	
	protected abstract ScoreResult doCalcScore(ITrackedBot tiger, ARole role, MetisAiFrame frame);
	
	
	
	public ScoreResult calcScore(final ITrackedBot tiger, final ARole role, final MetisAiFrame frame)
	{
		if (active)
		{
			return doCalcScore(tiger, role, frame);
		}
		return ScoreResult.defaultResult();
	}
	
	
	
	public static ScoreResult getCumulatedResult(final Collection<AScore> scores, final ITrackedBot tiger,
			final ARole role, final MetisAiFrame frame)
	{
		List<ScoreResult> results = new ArrayList<ScoreResult>(scores.size());
		for (AScore score : scores)
		{
			results.add(score.calcScore(tiger, role, frame));
		}
		return getCumulatedResult(results);
	}
	
	
	
	public static ScoreResult getCumulatedResult(final List<ScoreResult> scores)
	{
		int degree = 0;
		EUsefulness usefulness = EUsefulness.NEUTRAL;
		for (ScoreResult result : scores)
		{
			int lvl = result.getUsefulness().getLevel();
			if (lvl > usefulness.getLevel())
			{
				usefulness = result.getUsefulness();
				degree = result.getDegree();
			}
			else if (lvl == usefulness.getLevel())
			{
				degree += result.getDegree();
			}
		}
		return new ScoreResult(usefulness, degree);
	}
	
}
