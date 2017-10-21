/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import edu.tigers.sumatra.model.SumatraModel;



public class AboutDialog extends JDialog
{
	// --------------------------------------------------------------------------
	// --- instance-variables ---------------------------------------------------
	// --------------------------------------------------------------------------
	private static final long	serialVersionUID	= 3461893941869192656L;
	private static ClassLoader	classLoader			= AboutDialog.class.getClassLoader();
	
	
	// --------------------------------------------------------------------------
	// --- constructor(s) -------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public AboutDialog()
	{
		// --- window configuration ---
		this.setSize(300, 350);
		setResizable(false);
		setTitle("关于");
		setLayout(new BorderLayout());
		setModal(true);
		
		// --- alignment: center on screen ---
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenDimension = tk.getScreenSize();
		this.setLocation((int) (screenDimension.getWidth() - getWidth()) / 2,
				(int) (screenDimension.getHeight() - getHeight()) / 2);
		
		// --- heading ---
		JLabel heading = new JLabel("标准平台决策开发系统", SwingConstants.CENTER);
		heading.setFont(new Font("Dialog", Font.BOLD, 15));
		this.add(heading, BorderLayout.NORTH);
		
		// --- logo ---
		JLabel logo = new JLabel();
		ImageIcon iconNormal = new ImageIcon(classLoader.getResource("tigerIcon.png"));
		ImageIcon iconSmall = new ImageIcon(iconNormal.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
		logo.setPreferredSize(new Dimension(300, 250));
		logo.setIcon(iconSmall);
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setVerticalAlignment(SwingConstants.CENTER);
		this.add(logo, BorderLayout.CENTER);
		
		// --- text ---
		JLabel text = new JLabel();
		text.setText("<html>版本号: " + SumatraModel.getVersion() + "<br>" + "智动天地（北京）科技有限公司<br>"
				+ "2017年7月" + "</html>");
		this.add(text, BorderLayout.SOUTH);
		
	}
}
