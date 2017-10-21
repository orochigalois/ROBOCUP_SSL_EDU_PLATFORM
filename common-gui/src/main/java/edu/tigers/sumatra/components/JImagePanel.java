/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.components;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.tigers.sumatra.util.ImageScaler;



public class JImagePanel extends JPanel
{
	
	
	private static final long	serialVersionUID	= 5698886438859212214L;
	
	private ImageIcon				originalImage;
	
	private ImageIcon				scaledImage;
	
	private Dimension				scaleDimension		= new Dimension();
	
	
	
	public JImagePanel()
	{
		
	}
	
	
	
	public JImagePanel(final ImageIcon image)
	{
		originalImage = image;
		scaledImage = image;
	}
	
	
	@Override
	protected void paintComponent(final Graphics g)
	{
		super.paintComponent(g);
		
		if (originalImage == null)
		{
			return;
		}
		
		Dimension availDim = getSize();
		int availWidth = availDim.width;
		int availHeight = availDim.height;
		
		if ((availWidth == 0) || (availHeight == 0))
		{
			return;
		}
		
		if (!scaleDimension.equals(availDim))
		{
			scale(availWidth, availHeight);
			scaleDimension = availDim;
		}
		
		int imgWidth = scaledImage.getIconWidth();
		int imgHeight = scaledImage.getIconHeight();
		
		// Center the image if it does not perfectly fit the available space
		int xOffset = Math.max((availWidth - imgWidth) / 2, 0);
		int yOffset = Math.max((availHeight - imgHeight) / 2, 0);
		
		g.drawImage(scaledImage.getImage(), xOffset, yOffset, null);
	}
	
	
	private void scale(final int width, final int height)
	{
		int imgWidth = originalImage.getIconWidth();
		int imgHeight = originalImage.getIconHeight();
		
		double widthRatio = (double) width / imgWidth;
		double heightRatio = (double) height / imgHeight;
		double ratio = Math.min(widthRatio, heightRatio);
		
		int finalHeight = (int) (imgHeight * ratio);
		int finalWidth = (int) (imgWidth * ratio);
		scaledImage = ImageScaler.scaleImageIconSmooth(originalImage, finalWidth, finalHeight);
	}
	
	
	
	public void setImage(final ImageIcon image)
	{
		originalImage = image;
		scaledImage = image;
		scaleDimension = new Dimension();
		repaint();
	}
	
	
	
	public ImageIcon getImage()
	{
		return originalImage;
	}
}
