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

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.tigers.sumatra.util.GlobalShortcuts.EShortcut;
import net.miginfocom.swing.MigLayout;



public class ShortcutsDialog extends JDialog
{
	// --------------------------------------------------------------------------
	// --- instance-variables ---------------------------------------------------
	// --------------------------------------------------------------------------
	private static final long serialVersionUID = 3461893941869192656L;
	
	
	// --------------------------------------------------------------------------
	// --- constructor(s) -------------------------------------------------------
	// --------------------------------------------------------------------------
	
	public ShortcutsDialog()
	{
		// --- window configuration ---
		this.setSize(300, 350);
		setResizable(false);
		setTitle("Shortcuts");
		setModal(true);
		
		// --- alignment: center on screen ---
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenDimension = tk.getScreenSize();
		this.setLocation((int) (screenDimension.getWidth() - getWidth()) / 2,
				(int) (screenDimension.getHeight() - getHeight()) / 2);
				
		JPanel scPanel = new JPanel(new MigLayout("fill", ""));
		for (EShortcut eShortcut : EShortcut.values())
		{
			JLabel lblId = new JLabel(eShortcut.name());
			JLabel lblDesc = new JLabel(eShortcut.getDesc());
			scPanel.add(lblId);
			scPanel.add(lblDesc, "wrap");
		}
		add(scPanel);
	}
}
