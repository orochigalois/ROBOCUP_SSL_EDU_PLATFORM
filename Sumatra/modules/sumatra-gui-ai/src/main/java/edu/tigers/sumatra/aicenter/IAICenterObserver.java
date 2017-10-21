/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.aicenter;

import edu.tigers.sumatra.ai.athena.IAIModeChanged;
import edu.tigers.sumatra.aicenter.view.IPlayControlPanelObserver;
import edu.tigers.sumatra.aicenter.view.IRoleControlPanelObserver;



public interface IAICenterObserver extends IPlayControlPanelObserver, IRoleControlPanelObserver, IAIModeChanged
{
}
