/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.dhbw.mannheim.tigers.sumatra.view.rcm;

import java.util.List;

import edu.tigers.sumatra.rcm.ExtIdentifier;



public interface IIdentifierSelectionObserver
{
	
	void onIdentifiersSelected(List<ExtIdentifier> identifiers);
	
	
	
	void onIdentifiersSelectionCanceled();
}
