/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/
package edu.dhbw.mannheim.tigers.sumatra.view.log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;



public class FileSystemTree extends DefaultMutableTreeNode
{
	private static final long	serialVersionUID	= 1L;
	
	
	
	public FileSystemTree(final File file)
	{
		setUserObject(file);
	}
	
	
	
	@Override
	public int getChildCount()
	{
		return addFiles((File) getUserObject()).size();
	}
	
	
	
	@Override
	public FileSystemTree getChildAt(final int index)
	{
		return new FileSystemTree((File) addFiles((File) getUserObject()).get(index));
	}
	
	
	
	@Override
	public boolean isLeaf()
	{
		return !((File) getUserObject()).isDirectory();
	}
	
	
	
	@Override
	public String toString()
	{
		return ((File) getUserObject()).getName();
	}
	
	
	
	private List<Object> addFiles(final File file)
	{
		List<Object> fileList = new ArrayList<Object>();
		File[] files = file.listFiles();
		
		if (files != null)
		{
			for (int i = 0; i < files.length; i++)
			{
				// filter svn-data
				if (!(files[i].getName().contains(".svn")))
				{
					fileList.add(files[i]);
				}
			}
		}
		
		return fileList;
	}
}
