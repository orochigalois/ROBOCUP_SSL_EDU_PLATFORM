/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.filetree;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;



public class FileTree extends JPanel
{
	
	private static final long					serialVersionUID	= 6901756711335047357L;
	@SuppressWarnings("unused")
	private static final Logger				log					= Logger.getLogger(FileTree.class.getName());
	
	private final List<IFileTreeObserver>	observers			= new CopyOnWriteArrayList<IFileTreeObserver>();
	private final JTree							tree;
	
	private final List<String>					selectedPaths		= new ArrayList<>();
	
	
	
	public FileTree(final File dir)
	{
		this(dir, null);
	}
	
	
	private boolean equalTreePaths(final TreePath tp1, final TreePath tp2)
	{
		if (tp1.getPath().length != tp2.getPath().length)
		{
			return false;
		}
		for (int i = 0; i < tp1.getPathCount(); i++)
		{
			DefaultMutableTreeNode node1 = (DefaultMutableTreeNode) tp1.getPathComponent(i);
			DefaultMutableTreeNode node2 = (DefaultMutableTreeNode) tp2.getPathComponent(i);
			if (!node1.getUserObject().equals(node2.getUserObject()))
			{
				return false;
			}
		}
		return true;
	}
	
	
	
	public FileTree(final File dir, final FileTree preFileTree)
	{
		setLayout(new BorderLayout());
		
		// Make a tree list with all the nodes, and make it a JTree
		tree = new JTree(addNodes(null, dir));
		tree.setRootVisible(false);
		
		if (preFileTree != null)
		{
			JTree preTree = preFileTree.tree;
			int nextRow = 0;
			for (int preRow = 0; preRow < preTree.getRowCount(); preRow++)
			{
				TreePath preTp = preTree.getPathForRow(preRow);
				for (int row = nextRow; row < tree.getRowCount(); row++)
				{
					TreePath tp = tree.getPathForRow(row);
					if (equalTreePaths(preTp, tp))
					{
						if (preTree.isExpanded(preRow))
						{
							tree.expandPath(tp);
						}
						nextRow = row + 1;
						break;
					}
				}
			}
		}
		
		// Add a listener
		tree.addTreeSelectionListener(new TreeSelectionListener()
		{
			@Override
			public void valueChanged(final TreeSelectionEvent e)
			{
				for (TreePath tp : e.getPaths())
				{
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
					Element el = (Element) node.getUserObject();
					String filename = el.file.getAbsolutePath();
					boolean isNew = e.isAddedPath(tp);
					if (isNew && !selectedPaths.contains(filename))
					{
						selectedPaths.add(filename);
					}
					if (!isNew)
					{
						selectedPaths.remove(filename);
					}
				}
				for (IFileTreeObserver o : observers)
				{
					o.onFileSelected(selectedPaths);
				}
			}
		});
		
		// Lastly, put the JTree into a JScrollPane.
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.getViewport().add(tree);
		scrollpane.setPreferredSize(new Dimension(250, 0));
		add(BorderLayout.CENTER, scrollpane);
	}
	
	
	
	public void expandPaths(final int numPaths)
	{
		for (int i = 0; i < tree.getRowCount(); i++)
		{
			TreePath path = tree.getPathForRow(i);
			if (path.getPathCount() < numPaths)
			{
				tree.expandPath(path);
			}
		}
	}
	
	
	
	public void addObserver(final IFileTreeObserver observer)
	{
		observers.add(observer);
	}
	
	
	
	public void removeObserver(final IFileTreeObserver observer)
	{
		observers.remove(observer);
	}
	
	
	
	private DefaultMutableTreeNode addNodes(final DefaultMutableTreeNode curTop, final File dir)
	{
		Element curPath = new Element(dir);
		DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
		if (curTop != null)
		{ // should only be null at root
			curTop.add(curDir);
		}
		List<String> ol = new ArrayList<>();
		String[] tmp = dir.list();
		if (tmp == null)
		{
			log.error("Directory does not exist: " + dir.getAbsolutePath());
			return curDir;
		}
		for (String element : tmp)
		{
			ol.add(element);
		}
		Collections.sort(ol, String.CASE_INSENSITIVE_ORDER);
		File f;
		List<File> files = new ArrayList<>();
		// Make two passes, one for Dirs and one for Files. This is #1.
		for (int i = 0; i < ol.size(); i++)
		{
			String thisObject = ol.get(i);
			String newPath;
			if (curPath.file.getPath().equals("."))
			{
				newPath = thisObject;
			} else
			{
				newPath = curPath.file.getPath() + File.separator + thisObject;
			}
			if ((f = new File(newPath)).isDirectory())
			{
				addNodes(curDir, f);
			} else
			{
				files.add(f);
			}
		}
		// Pass two: for files.
		for (int fnum = 0; fnum < files.size(); fnum++)
		{
			curDir.add(new DefaultMutableTreeNode(new Element(files.get(fnum))));
		}
		return curDir;
	}
	
	
	
	public interface IFileTreeObserver
	{
		
		void onFileSelected(List<String> filenames);
	}
	
	private static class Element
	{
		File	file;
		
		
		Element(final File file)
		{
			this.file = file;
		}
		
		
		@Override
		public String toString()
		{
			return file.getName();
		}
		
		
		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = (prime * result) + ((file == null) ? 0 : file.hashCode());
			return result;
		}
		
		
		@Override
		public boolean equals(final Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			Element other = (Element) obj;
			if (file == null)
			{
				if (other.file != null)
				{
					return false;
				}
			} else if (!file.equals(other.file))
			{
				return false;
			}
			return true;
		}
	}
}
