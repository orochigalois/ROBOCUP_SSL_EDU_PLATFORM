/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.drawable;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;



public final class ColorPickerFactory
{
	
	private static final List<Color>	colors	= new ArrayList<Color>();
	
	static
	{
		colors.add(new Color(0xF7181D));
		colors.add(new Color(0xF73E18));
		colors.add(new Color(0xF86A19));
		colors.add(new Color(0xF9951A));
		colors.add(new Color(0xF9C11B));
		colors.add(new Color(0xFAEC1B));
		colors.add(new Color(0xDEFB1C));
		colors.add(new Color(0xB4FB1D));
		colors.add(new Color(0x8AFC1E));
		colors.add(new Color(0x60FD1F));
		colors.add(new Color(0x37FE20));
	}
	
	
	private ColorPickerFactory()
	{
	}
	
	
	
	public static IColorPicker scaledSingleBlack(final double r, final double g, final double b, final int colorId)
	{
		return scaledSingleBlack(r, g, b, 1.0, colorId);
	}
	
	
	
	public static IColorPicker scaledSingleBlack(final double r, final double g, final double b, final double alpha,
			final int colorId)
	{
		return new IColorPicker()
		{
			
			@Override
			public Color applyColor(final Graphics2D g2, final double relValue)
			{
				Color color = getColor(relValue);
				g2.setColor(color);
				return color;
			}
			
			
			@Override
			public Color getColor(final double relValue)
			{
				Color color;
				switch (colorId)
				{
					case 1:
						color = new Color((float) relValue, (float) g, (float) b, (float) alpha);
						break;
					case 2:
						color = new Color((float) r, (float) relValue, (float) b, (float) alpha);
						break;
					case 3:
						color = new Color((float) r, (float) g, (float) relValue, (float) alpha);
						break;
					default:
						throw new IllegalArgumentException("Invalid colorId: " + colorId);
				}
				return color;
			}
		};
	}
	
	
	
	public static IColorPicker scaledDouble(final Color color1, final Color color2)
	{
		return new IColorPicker()
		{
			
			@Override
			public Color applyColor(final Graphics2D g, final double relValue)
			{
				Color color = getColor(relValue);
				g.setColor(color);
				return color;
			}
			
			
			@Override
			public Color getColor(final double relValue)
			{
				int red = (int) ((color2.getRed() * relValue) + (color1.getRed() * (1 - relValue)));
				int green = (int) ((color2.getGreen() * relValue) + (color1.getGreen() * (1 - relValue)));
				int blue = (int) ((color2.getBlue() * relValue) + (color1.getBlue() * (1 - relValue)));
				Color stepColor = new Color(red, green, blue);
				return stepColor;
			}
		};
	}
	
	
	
	public static IColorPicker scaledTransparent(final Color color)
	{
		return new IColorPicker()
		{
			@Override
			public Color applyColor(final Graphics2D g, final double relValue)
			{
				final int type = AlphaComposite.SRC_OVER;
				g.setColor(color);
				g.setComposite(AlphaComposite.getInstance(type, (float) relValue));
				return color;
			}
			
			
			@Override
			public Color getColor(final double relValue)
			{
				return color;
			}
		};
	}
	
	
	
	public static IColorPicker greenRedGradient()
	{
		return new IColorPicker()
		{
			
			@Override
			public Color getColor(final double relValue)
			{
				
				double step = 1.0 / colors.size();
				for (int i = 0; i < colors.size(); i++)
				{
					double val = (i + 1) * step;
					if (relValue <= val)
					{
						return colors.get(colors.size() - i - 1);
					}
				}
				return Color.black;
			}
			
			
			@Override
			public Color applyColor(final Graphics2D g, final double relValue)
			{
				Color c = getColor(relValue);
				g.setColor(c);
				return c;
			}
		};
	}
}
