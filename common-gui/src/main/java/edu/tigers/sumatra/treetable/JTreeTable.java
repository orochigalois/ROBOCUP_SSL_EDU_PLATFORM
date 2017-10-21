/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.treetable;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import org.apache.commons.configuration.HierarchicalConfiguration.Node;
import org.apache.commons.configuration.tree.ConfigurationNode;

import edu.tigers.sumatra.lookandfeel.ILookAndFeelStateObserver;
import edu.tigers.sumatra.lookandfeel.LookAndFeelStateAdapter;


public class JTreeTable extends JTable {
	
	private static final long serialVersionUID = -3052468144632521282L;

	
	protected TreeTableCellRenderer tree;
	private final ITreeTableModel treeTableModel;

	
	public JTreeTable(final ITreeTableModel treeTableModel) {
		super();
		setTreeTableModel(treeTableModel);
		this.treeTableModel = treeTableModel;

		// setCellEditor(anEditor) // # Potentally check for validity...?
	}

	
	public void setTreeTableModel(final ITreeTableModel treeTableModel) {
		// Create the tree. It will be used as a renderer and editor.
		tree = new TreeTableCellRenderer(treeTableModel);
		final TreeRenderer treeRenderer = new TreeRenderer(treeTableModel);
		// Cares for the rendering of the first column by the JTree
		tree.setCellRenderer(treeRenderer);

		// Install a tableModel representing the visible rows in the tree.
		super.setModel(new TreeTableModelAdapter(treeTableModel, tree));

		// Force the JTable and JTree to share their row selection models.
		final ListToTreeSelectionModelWrapper selectionWrapper = new ListToTreeSelectionModelWrapper();
		tree.setSelectionModel(selectionWrapper);
		setSelectionModel(selectionWrapper.getListSelectionModel());

		// Install the tree editor renderer and editor.
		setDefaultRenderer(ITreeTableModel.class, tree);
		setDefaultEditor(ITreeTableModel.class, new TreeTableCellEditor());

		setDefaultRenderer(Boolean.TYPE, getDefaultRenderer(Boolean.class));
		setDefaultEditor(Boolean.TYPE, getDefaultEditor(Boolean.class));

		// No grid.
		setShowGrid(false);

		// No intercell spacing
		setIntercellSpacing(new Dimension(0, 0));

		// And update the height of the trees row to match that of
		// the table.
		if (tree.getRowHeight() < 1) {
			// Metal looks better like this.
			setRowHeight(18);
		}
	}

	@Override
	public String getToolTipText(final MouseEvent event) {
		return treeTableModel.getToolTipText(event);
	}

	
	@Override
	public void updateUI() {
		super.updateUI();
		if (tree != null) {
			tree.updateUI();
		}
		// Use the tree's default foreground and background colors in the
		// table.
		LookAndFeel.installColorsAndFont(this, "Tree.background", "Tree.foreground", "Tree.font");
	}

	
	@Override
	public int getEditingRow() {
		return (getColumnClass(editingColumn) == ITreeTableModel.class) ? -1 : editingRow;
	}

	
	@Override
	public void setRowHeight(final int rowHeight) {
		super.setRowHeight(rowHeight);
		if ((tree != null) && (tree.getRowHeight() != rowHeight)) {
			tree.setRowHeight(getRowHeight());
		}
	}

	@Override
	public TableCellEditor getCellEditor(final int row, final int column) {
		if (column == 1) {
			TreePath path = tree.getPathForRow(row);
			Node node = (Node) path.getLastPathComponent();
			for (ConfigurationNode attr : node.getAttributes("class")) {
				Class<?> classType = getClassFromValue(attr.getValue());
				if (classType.isEnum()) {
					String[] entries = new String[classType.getEnumConstants().length];
					int i = 0;
					for (Object obj : classType.getEnumConstants()) {
						entries[i++] = obj.toString();
					}
					JComboBox<String> cb = new JComboBox<String>(entries);
					return new DefaultCellEditor(cb);
				}
				TableCellEditor defEditor = getDefaultEditor(classType);
				if (defEditor == null) {
					return getDefaultEditor(String.class);
				}
				return defEditor;
			}
		}
		return super.getCellEditor(row, column);
	}

	@Override
	public TableCellRenderer getCellRenderer(final int row, final int column) {
		if (column == 1) {
			TreePath path = tree.getPathForRow(row);
			Node node = (Node) path.getLastPathComponent();
			for (ConfigurationNode attr : node.getAttributes("class")) {
				Class<?> classType = getClassFromValue(attr.getValue());
				return getDefaultRenderer(classType);
			}
		}
		return super.getCellRenderer(row, column);
	}

	
	public Class<?> getClassFromValue(final Object value) {
		if (value.getClass() == Class.class) {
			return (Class<?>) value;
		}
		String clazz = (String) value;
		if (clazz.equals("int")) {
			return Integer.TYPE;
		}
		if (clazz.equals("long")) {
			return Long.TYPE;
		}
		if (clazz.equals("float")) {
			return Float.TYPE;
		}
		if (clazz.equals("double")) {
			return Double.TYPE;
		}
		if (clazz.equals("boolean")) {
			return Boolean.TYPE;
		}
		try {
			return Class.forName(clazz);
		} catch (ClassNotFoundException err) {
			return String.class;
		}
	}

	
	public class TreeTableCellRenderer extends JTree implements TableCellRenderer {
		
		private static final long serialVersionUID = 6816892617917678961L;

		
		protected int visibleRow;

		
		public TreeTableCellRenderer(final ITreeTableModel treeTableModel) {
			super(treeTableModel);
		}

		
		@Override
		public void updateUI() {
			super.updateUI();
			// Make the tree's cell renderer use the table's cell selection
			// colors.
			final TreeCellRenderer tcr = getCellRenderer();
			if (tcr instanceof DefaultTreeCellRenderer) {
				final DefaultTreeCellRenderer dtcr = ((DefaultTreeCellRenderer) tcr);
				// For 1.1 uncomment this, 1.2 has a bug that will cause an
				// exception to be thrown if the border selection color is
				// null.
				dtcr.setTextSelectionColor(UIManager.getColor("Table.selectionForeground"));
				dtcr.setBackgroundSelectionColor(UIManager.getColor("Table.selectionBackground"));
			}
		}

		
		@Override
		public void setRowHeight(final int rowHeight) {
			if (rowHeight > 0) {
				super.setRowHeight(rowHeight);
				if ((JTreeTable.this != null) && (JTreeTable.this.getRowHeight() != rowHeight)) {
					JTreeTable.this.setRowHeight(getRowHeight());
				}
			}
		}

		
		@Override
		public void setBounds(final int x, final int y, final int w, final int h) {
			super.setBounds(x, 0, w, JTreeTable.this.getHeight());
		}

		
		@Override
		public void paint(final Graphics g) {
			g.translate(0, -visibleRow * getRowHeight());
			super.paint(g);
		}

		
		@Override
		public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
				final boolean hasFocus, final int row, final int column) {
			if (isSelected) {
				setBackground(table.getSelectionBackground());
			} else {
				setBackground(table.getBackground());
			}

			visibleRow = row;
			return this;
		}
	}

	
	private class TreeTableCellEditor extends AbstractCellEditor implements TableCellEditor {
		
		private static final long serialVersionUID = -2591875536212318768L;

		@Override
		public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected,
				final int r, final int c) {
			return tree;
		}

		
		@Override
		public boolean isCellEditable(final EventObject e) {
			if (e instanceof MouseEvent) {
				for (int counter = getColumnCount() - 1; counter >= 0; counter--) {
					if (getColumnClass(counter) == ITreeTableModel.class) {
						final MouseEvent me = (MouseEvent) e;
						final MouseEvent newME = new MouseEvent(tree, me.getID(), me.getWhen(), me.getModifiers(),
								me.getX() - getCellRect(0, counter, true).x, me.getY(), me.getClickCount(),
								me.isPopupTrigger());
						tree.dispatchEvent(newME);
						break;
					}
				}
			}

			return false;
		}

		@Override
		public Object getCellEditorValue() {
			// Don't know what this is for! O.o
			return null;
		}
	}

	
	class ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel {
		
		private static final long serialVersionUID = -5909393885929909085L;

		
		protected boolean updatingListSelectionModel;

		
		public ListToTreeSelectionModelWrapper() {
			super();
			getListSelectionModel().addListSelectionListener(createListSelectionListener());
		}

		
		ListSelectionModel getListSelectionModel() {
			return listSelectionModel;
		}

		
		@Override
		public void resetRowSelection() {
			if (!updatingListSelectionModel) {
				updatingListSelectionModel = true;
				try {
					super.resetRowSelection();
				} finally {
					updatingListSelectionModel = false;
				}
			}
			// Notice how we don't message super if
			// updatingListSelectionModel is true. If
			// updatingListSelectionModel is true, it implies the
			// ListSelectionModel has already been updated and the
			// paths are the only thing that needs to be updated.
		}

		
		protected ListSelectionListener createListSelectionListener() {
			return new ListSelectionHandler();
		}

		
		protected void updateSelectedPathsFromSelectedRows() {
			if (!updatingListSelectionModel) {
				updatingListSelectionModel = true;
				try {
					// This is way expensive, ListSelectionModel needs an
					// enumerator for iterating.
					final int min = listSelectionModel.getMinSelectionIndex();
					final int max = listSelectionModel.getMaxSelectionIndex();

					clearSelection();
					if ((min != -1) && (max != -1)) {
						for (int counter = min; counter <= max; counter++) {
							if (listSelectionModel.isSelectedIndex(counter)) {
								final TreePath selPath = tree.getPathForRow(counter);

								if (selPath != null) {
									addSelectionPath(selPath);
								}
							}
						}
					}
				} finally {
					updatingListSelectionModel = false;
				}
			}
		}

		
		class ListSelectionHandler implements ListSelectionListener {
			@Override
			public void valueChanged(final ListSelectionEvent e) {
				updateSelectedPathsFromSelectedRows();
			}
		}
	}

	
	private static class TreeRenderer extends DefaultTreeCellRenderer implements ILookAndFeelStateObserver {
		
		private static final long serialVersionUID = 7785387299072802203L;

		private final ITreeTableModel treeTableModel;

		
		public TreeRenderer(final ITreeTableModel treeTableModel) {
			super();
			this.treeTableModel = treeTableModel;
			LookAndFeelStateAdapter.getInstance().addObserver(this);
		}

		@Override
		public void onLookAndFeelChanged() {
			setUI(UIManager.getUI(this));

			setOpenIcon(UIManager.getIcon("Tree.openIcon"));
			setLeafIcon(UIManager.getIcon("Tree.leafIcon"));
			setClosedIcon(UIManager.getIcon("Tree.closedIcon"));
		}

		@Override
		public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean selected,
				final boolean expanded, final boolean isLeaf, final int row, final boolean hasFocus) {
			final Component comp = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row,
					hasFocus);

			// Let the model change whatever it wants
			final JLabel label = (JLabel) comp;
			// DefaultTreeCellRenderer uses a JLabel!
			treeTableModel.renderTreeCellComponent(label, value);

			return comp;
		}
	}
}