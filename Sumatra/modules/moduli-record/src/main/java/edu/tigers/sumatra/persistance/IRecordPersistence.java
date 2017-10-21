/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.persistance;

import java.util.Collection;
import java.util.List;



public interface IRecordPersistence extends IFrameByTimestampPersistence, IPersistence
{
	
	List<BerkeleyLogEvent> loadLogEvents();
	
	
	
	void saveLogEvent(List<BerkeleyLogEvent> logEvents);
	
	
	
	RecordFrame getRecordFrame(final long tCur);
	
	
	
	RecordCamFrame getCamFrame(final long tCur);
	
	
	
	void saveRecordFrames(Collection<RecordFrame> recordFrames);
	
	
	
	void saveCamFrames(Collection<RecordCamFrame> camFrames);
}
