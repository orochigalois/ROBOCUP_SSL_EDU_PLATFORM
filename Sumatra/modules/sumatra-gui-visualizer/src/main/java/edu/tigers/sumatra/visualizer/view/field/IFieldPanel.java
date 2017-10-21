/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.visualizer.view.field;

import edu.tigers.sumatra.drawable.IDrawableTool;
import edu.tigers.sumatra.visualizer.view.EVisualizerOptions;
import edu.tigers.sumatra.visualizer.view.IFieldPanelObserver;
import edu.tigers.sumatra.wp.data.ShapeMap;



public interface IFieldPanel extends IDrawableTool
{
	
	void start();
	
	
	
	void stop();
	
	
	
	void paintOffline();
	
	
	
	void setPanelVisible(boolean visible);
	
	
	
	void setShapeMap(EShapeLayerSource source, ShapeMap shapeMap, boolean inverted);
	
	
	
	void addObserver(IFieldPanelObserver newObserver);
	
	
	
	void removeObserver(IFieldPanelObserver oldObserver);
	
	
	
	void setLayerVisiblility(final String layerId, final boolean visible);
	
	
	
	void onOptionChanged(final EVisualizerOptions option, final boolean isSelected);
	
	
	
	void clearField();
	
	
	
	void clearField(final EShapeLayerSource source);
	
	
	
	void repaint();
}
