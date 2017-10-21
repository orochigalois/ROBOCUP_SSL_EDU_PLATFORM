/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.util;

import java.awt.Image;

import javax.swing.ImageIcon;



public final class ImageScaler
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public static final int	BUTTON_DEFAULT_SIZE	= 30;
	
	
	// --------------------------------------------------------------------------
	// --- constructors ---------------------------------------------------------
	// --------------------------------------------------------------------------
	
	private ImageScaler()
	{
		
	}
	
	
	// --------------------------------------------------------------------------
	// --- methods --------------------------------------------------------------
	// --------------------------------------------------------------------------
	
	
	public static ImageIcon scaleImageIcon(final ImageIcon imageIcon, final int width, final int height)
	{
		return scale(imageIcon, width, height, java.awt.Image.SCALE_DEFAULT);
	}
	
	
	
	public static ImageIcon scaleImageIconSmooth(final ImageIcon imageIcon, final int width, final int height)
	{
		return scale(imageIcon, width, height, java.awt.Image.SCALE_SMOOTH);
	}
	
	
	private static ImageIcon scale(final ImageIcon imageIcon, final int width, final int height, final int hints)
	{
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(width, height, hints);
		return new ImageIcon(newimg);
	}
	
	
	
	public static ImageIcon scaleDefaultButtonImageIcon(final String path)
	{
		ImageIcon imageIcon = new ImageIcon(ImageScaler.class.getResource(path));
		return scaleImageIcon(imageIcon, BUTTON_DEFAULT_SIZE, BUTTON_DEFAULT_SIZE);
	}
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
