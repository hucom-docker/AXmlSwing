package org.arong.axmlswing.manager;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.JWindow;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneLayout;
import javax.swing.SpringLayout;
import javax.swing.text.JTextComponent;

import org.arong.axmlswing.attribute.AttributeModel;
import org.arong.axmlswing.attribute.AttributeTransfer;
import org.arong.axmlswing.attribute.AttributeValidator;
/**
 * 组件管理器
 * @author dipoo
 * @since 2015-01-27
 */
public class ComponentManager {
	private static Container mainWindow;
	
	private static Map<String, Component> components = new HashMap<String, Component>();
	
	public static Component getComponent(String id){
		return components.get(id);
	}
	
	public static void putComponent(String id, Component component){
		components.put(id, component);
	}

	public static void setMainWindow(Container mainWindow) {
		ComponentManager.mainWindow = mainWindow;
	}

	public static Container getMainWindow() {
		return mainWindow;
	}
	
	/**
	 * 设置组件共有的属性
	 */
	public static void setCommonAttribute(Container comp, AttributeModel attr){
		comp.setBounds(attr.getX(), attr.getY(), attr.getWidth(), attr.getHeight());
		if(AttributeValidator.size(attr.getSize())){
			int[] arr = AttributeTransfer.size(attr.getSize());
			//setSize
			comp.setSize(arr[0], arr[1]);
		}
		if(AttributeValidator.color(attr.getBackground())){
			//setBackground
			Color c = AttributeTransfer.color(attr.getBackground());
			if(comp instanceof JFrame){
				JFrame w = (JFrame) comp;
				w.getContentPane().setBackground(c);
			}else if(comp instanceof JDialog){
				JDialog w = (JDialog) comp;
				w.getContentPane().setBackground(c);
			}else if(comp instanceof JWindow){
				JWindow w = (JWindow) comp;
				w.getContentPane().setBackground(c);
			}else{
				comp.setBackground(c);
			}
		}
		if(AttributeValidator.color(attr.getForeground())){
			//setForeground
			Color c = AttributeTransfer.color(attr.getForeground());
			if(comp instanceof JFrame){
				JFrame w = (JFrame) comp;
				w.getContentPane().setForeground(c);
			}else if(comp instanceof JDialog){
				JDialog w = (JDialog) comp;
				w.getContentPane().setForeground(c);
			}else if(comp instanceof JWindow){
				JWindow w = (JWindow) comp;
				w.getContentPane().setForeground(c);
			}else{
				comp.setForeground(c);
			}
		}
		if(AttributeValidator.size(attr.getLocation())){
			int[] arr = AttributeTransfer.size(attr.getLocation());
			//setLocation
			comp.setLocation(arr[0], arr[1]);
		}
		if(AttributeValidator.size(attr.getMaximumSize())){
			int[] arr = AttributeTransfer.size(attr.getMaximumSize());
			//setMaximumSize
			comp.setMaximumSize(new Dimension(arr[0], arr[1]));
		}
		if(AttributeValidator.size(attr.getMinimumSize())){
			int[] arr = AttributeTransfer.size(attr.getMinimumSize());
			//setMinimumSize
			comp.setMinimumSize(new Dimension(arr[0], arr[1]));
		}
		if(AttributeValidator.size(attr.getPreferredSize())){
			int[] arr = AttributeTransfer.size(attr.getPreferredSize());
			//setPreferredSize
			comp.setPreferredSize(new Dimension(arr[0], arr[1]));
		}
		if(AttributeValidator.bounds(attr.getBounds())){
			int[] arr = AttributeTransfer.bounds(attr.getBounds());
			//setBounds
			comp.setBounds(arr[0], arr[1], arr[2], arr[3]);
		}
		if(AttributeValidator.cursor(attr.getCursor())){
			//setCursor
			comp.setCursor(AttributeTransfer.cursor(attr.getCursor()));
		}
		if(AttributeValidator.font(attr.getFont())){
			//setFont
			comp.setFont(AttributeTransfer.font(attr.getFont()));
		}
		if(attr.getLayout() != null){
			switch(attr.getLayout()){
				case 0:
					comp.setLayout(null);
					break;
				case 1:
					comp.setLayout(new FlowLayout());
					break;
				case 2:
					comp.setLayout(new BorderLayout());
					break;
				case 3:
					comp.setLayout(new BoxLayout(comp, BoxLayout.X_AXIS));
					break;
				case 4:
					comp.setLayout(new CardLayout());
					break;
				case 5:
					comp.setLayout(new GridLayout());
					break;
				case 6:
					comp.setLayout(new GridBagLayout());
					break;
				case 7:
					comp.setLayout(new GroupLayout(comp));
					break;
				case 8:
					comp.setLayout(new OverlayLayout(null));
					break;
				case 9:
					comp.setLayout(new ScrollPaneLayout());
					break;
				case 10:
					comp.setLayout(new SpringLayout());
					break;
			}
		}
		//设置单击事件
		if(!AttributeValidator.isBlank(attr.getOnclick())){
			if(comp instanceof JMenuItem){
				((JMenuItem) comp).addActionListener(AttributeTransfer.actionListener(attr.getOnclick()));
			}else{
				comp.addMouseListener(AttributeTransfer.onclick(attr.getOnclick(), 1));
			}
		}
		//设置双击事件
		if(!AttributeValidator.isBlank(attr.getOndblclick())){
			comp.addMouseListener(AttributeTransfer.onclick(attr.getOndblclick(), 2));
		}
	}
	
	/**
	 * 设置按钮共有的属性
	 */
	public static void setAbstactButtonAttribute(AbstractButton comp, AttributeModel attr){
		if(!AttributeValidator.isBlank(attr.getDisabledIcon())){
			comp.setDisabledIcon(AttributeTransfer.icon(attr.getDisabledIcon()));
		}
		if(!AttributeValidator.isBlank(attr.getIcon())){
			comp.setIcon(AttributeTransfer.icon(attr.getIcon()));
		}
		if(!AttributeValidator.isBlank(attr.getDisabledSelectedIcon())){
			comp.setDisabledSelectedIcon(AttributeTransfer.icon(attr.getDisabledSelectedIcon()));
		}
		if(!AttributeValidator.isBlank(attr.getPressedIcon())){
			comp.setPressedIcon(AttributeTransfer.icon(attr.getPressedIcon()));
		}
		if(!AttributeValidator.isBlank(attr.getRolloverIcon())){
			comp.setRolloverIcon(AttributeTransfer.icon(attr.getRolloverIcon()));
		}
		if(!AttributeValidator.isBlank(attr.getRolloverSelectedIcon())){
			comp.setRolloverSelectedIcon(AttributeTransfer.icon(attr.getRolloverSelectedIcon()));
		}
		if(AttributeValidator.bounds(attr.getMargin())){
			int[] arr = AttributeTransfer.bounds(attr.getMargin());
			comp.setMargin(new Insets(arr[0], arr[1], arr[2], arr[3]));
		}
	}
	
	/**
	 * 设置Text共有的属性
	 */
	public static void setTextComponentAttribute(JTextComponent comp, AttributeModel attr){
		if(AttributeValidator.color(attr.getCaretColor())){
			comp.setCaretColor(AttributeTransfer.color(attr.getCaretColor()));
		}
		if(AttributeValidator.color(attr.getSelectionColor())){
			comp.setSelectionColor(AttributeTransfer.color(attr.getSelectionColor()));
		}
		if(AttributeValidator.color(attr.getSelectedTextColor())){
			comp.setSelectedTextColor(AttributeTransfer.color(attr.getSelectedTextColor()));
		}
		if(AttributeValidator.color(attr.getDisabledTextColor())){
			comp.setDisabledTextColor(AttributeTransfer.color(attr.getDisabledTextColor()));
		}
		if(AttributeValidator.bounds(attr.getMargin())){
			int[] arr = AttributeTransfer.bounds(attr.getMargin());
			comp.setMargin(new Insets(arr[0], arr[1], arr[2], arr[3]));
		}
	}
	
	/**
	 * 设置组件的特殊属性
	 */
	public static void setComponentSpecificAttribute(String name, Component c, AttributeModel attr){
		if("jframe".equals(name)){
			JFrame comp = (JFrame)c;
			if(AttributeValidator.bounds(attr.getMaximizedBounds())){
				int[] arr = AttributeTransfer.bounds(attr.getMaximizedBounds());
				comp.setMaximizedBounds(new Rectangle(arr[0], arr[1], arr[2], arr[3]));
			}
			if(!AttributeValidator.isBlank(attr.getIconImage())){
				comp.setIconImage(((ImageIcon)AttributeTransfer.icon(attr.getIconImage())).getImage());
			}
			if(!AttributeValidator.isBlank(attr.getLocationRelativeTo())){
				comp.setLocationRelativeTo(ComponentManager.getComponent(attr.getLocationRelativeTo()));
			}
		}else if("jwindow".equals(name)){
			JWindow comp = (JWindow) c;
			if(!AttributeValidator.isBlank(attr.getIconImage())){
				comp.setIconImage(((ImageIcon)AttributeTransfer.icon(attr.getIconImage())).getImage());
			}
			if(!AttributeValidator.isBlank(attr.getLocationRelativeTo())){
				comp.setLocationRelativeTo(ComponentManager.getComponent(attr.getLocationRelativeTo()));
			}
		}else if("jdialog".equals(name)){
			JDialog comp = (JDialog) c;
			if(!AttributeValidator.isBlank(attr.getIconImage())){
				comp.setIconImage(((ImageIcon)AttributeTransfer.icon(attr.getIconImage())).getImage());
			}
			if(!AttributeValidator.isBlank(attr.getLocationRelativeTo())){
				comp.setLocationRelativeTo(ComponentManager.getComponent(attr.getLocationRelativeTo()));
			}
		}else if("jtextfield".equals(name)){
			JTextField comp = (JTextField) c;
			setTextComponentAttribute(comp, attr);
		}else if("jtextarea".equals(name)){
			JTextArea comp = (JTextArea) c;
			setTextComponentAttribute(comp, attr);
		}else if("jlabel".equals(name)){
			JLabel comp = (JLabel) c;
			if(!AttributeValidator.isBlank(attr.getIcon())){
				comp.setIcon(AttributeTransfer.icon(attr.getIcon()));
			}
			if(!AttributeValidator.isBlank(attr.getDisabledIcon())){
				comp.setDisabledIcon(AttributeTransfer.icon(attr.getDisabledIcon()));
			}
		}else if("jbutton".equals(name)){
			JButton comp = (JButton) c;
			setAbstactButtonAttribute(comp, attr);
		}else if("jtogglebutton".equals(name)){
			JToggleButton comp = (JToggleButton) c;
			setAbstactButtonAttribute(comp, attr);
		}else if("jcheckbox".equals(name)){
			JCheckBox comp = (JCheckBox) c;
			setAbstactButtonAttribute(comp, attr);
		}else if("jcombobox".equals(name)){
		}else if("jradiobutton".equals(name)){
			JRadioButton comp = (JRadioButton) c;
			setAbstactButtonAttribute(comp, attr);
		}else if("jmenuitem".equals(name)){
			JMenuItem comp = (JMenuItem) c;
			setAbstactButtonAttribute(comp, attr);
		}else if("jmenu".equals(name)){
			JMenu comp = (JMenu) c;
			setAbstactButtonAttribute(comp, attr);
			if(AttributeValidator.size(attr.getMenuLocation())){
				int[] arr = AttributeTransfer.size(attr.getMenuLocation());
				comp.setMenuLocation(arr[0], arr[1]);
			}
		}else if("jcheckboxmenuitem".equals(name)){
			JCheckBoxMenuItem comp = (JCheckBoxMenuItem) c;
			setAbstactButtonAttribute(comp, attr);
		}else if("jradiobuttonmenuitem".equals(name)){
			JRadioButtonMenuItem comp = (JRadioButtonMenuItem) c;
			setAbstactButtonAttribute(comp, attr);
		}else if("jmenubar".equals(name)){
			JMenuBar comp = (JMenuBar) c;
			if(AttributeValidator.bounds(attr.getMargin())){
				int[] arr = AttributeTransfer.bounds(attr.getMargin());
				comp.setMargin(new Insets(arr[0], arr[1], arr[2], arr[3]));
			}
		}else if("jpanel".equals(name)){
		}else if("jrootpane".equals(name)){
		}else if("jscrollpane".equals(name)){
		}else if("jscrollbar".equals(name)){
		}else if("jcolorchooser".equals(name)){
			JColorChooser comp = (JColorChooser) c;
			if(AttributeValidator.color(attr.getColor())){
				comp.setColor(AttributeTransfer.color(attr.getColor()));
			}
		}else if("jinternalframe".equals(name)){
			JInternalFrame comp = (JInternalFrame) c;
			if(!AttributeValidator.isBlank(attr.getFrameIcon())){
				comp.setFrameIcon(AttributeTransfer.icon(attr.getFrameIcon()));
			}
		}else if("jlayeredpane".equals(name)){
		}else if("jlist".equals(name)){
			JList comp = (JList) c;
			if(AttributeValidator.color(attr.getSelectionBackground())){
				comp.setSelectionBackground(AttributeTransfer.color(attr.getSelectionBackground()));
			}
			if(AttributeValidator.color(attr.getSelectionForeground())){
				comp.setSelectionForeground(AttributeTransfer.color(attr.getSelectionForeground()));
			}
			if(AttributeValidator.size(attr.getSelectionInterval())){
				int[] arr = AttributeTransfer.size(attr.getSelectionInterval());
				comp.setSelectionInterval(arr[0], arr[1]);
			}
		}else if("jpopupmenu".equals(name)){
			JPopupMenu comp = (JPopupMenu) c;
			if(AttributeValidator.size(attr.getPopupSize())){
				int[] arr = AttributeTransfer.size(attr.getPopupSize());
				comp.setPopupSize(arr[0], arr[1]);
			}
		}else if("jprogressbar".equals(name)){
		}else if("jseparator".equals(name)){
		}else if("jslider".equals(name)){
		}else if("jspinner".equals(name)){
		}else if("jsplitpane".equals(name)){
		}else if("jtabbedpane".equals(name)){
			JTabbedPane comp = (JTabbedPane) c;
			if(AttributeValidator.size(attr.getMnemonicAt())){
				int[] arr = AttributeTransfer.size(attr.getMnemonicAt());
				comp.setMnemonicAt(arr[0], arr[1]);
			}
		}else if("jtable".equals(name)){
			JTable comp = (JTable) c;
			if(AttributeValidator.size(attr.getColumnSelectionInterval())){
				int[] arr = AttributeTransfer.size(attr.getColumnSelectionInterval());
				comp.setColumnSelectionInterval(arr[0], arr[1]);
			}
			if(AttributeValidator.color(attr.getGridColor())){
				comp.setGridColor(AttributeTransfer.color(attr.getGridColor()));
			}
			if(AttributeValidator.size(attr.getIntercellSpacing())){
				int[] arr = AttributeTransfer.size(attr.getIntercellSpacing());
				comp.setIntercellSpacing(new Dimension(arr[0], arr[1]));
			}
			if(AttributeValidator.size(attr.getPreferredScrollableViewportSize())){
				int[] arr = AttributeTransfer.size(attr.getPreferredScrollableViewportSize());
				comp.setPreferredScrollableViewportSize(new Dimension(arr[0], arr[1]));
			}
			if(AttributeValidator.color(attr.getSelectionBackground())){
				comp.setSelectionBackground(AttributeTransfer.color(attr.getSelectionBackground()));
			}
			if(AttributeValidator.color(attr.getSelectionForeground())){
				comp.setSelectionForeground(AttributeTransfer.color(attr.getSelectionForeground()));
			}
		}else if("jtableheader".equals(name)){
		}else if("jtoolbar".equals(name)){
			JToolBar comp = (JToolBar) c;
			if(AttributeValidator.bounds(attr.getMargin())){
				int[] arr = AttributeTransfer.bounds(attr.getMargin());
				comp.setMargin(new Insets(arr[0], arr[1], arr[2], arr[3]));
			}
		}else if("jtooltip".equals(name)){
		}else if("jfilechooser".equals(name)){
			JFileChooser comp = (JFileChooser) c;
			if(!AttributeValidator.isBlank(attr.getCurrentDirectory())){
				comp.setCurrentDirectory(new File(attr.getCurrentDirectory()));
			}
			if(!AttributeValidator.isBlank(attr.getSelectedFile())){
				comp.setSelectedFile(new File(attr.getSelectedFile()));
			}
			if(!AttributeValidator.isBlank(attr.getSelectedFiles())){
				String[] a = attr.getSelectedFiles().split("\\|");
				File[] fs = new File[a.length];
				for(int i = 0; i < a.length; i++){
					fs[i] = new File(a[i]);
				}
				comp.setSelectedFiles(fs);
			}
		}else if("jtree".equals(name)){
			JTree comp = (JTree) c;
			if(AttributeValidator.intArray(attr.getSelectionRows(), ",")){
				int[] rows = AttributeTransfer.intArray(attr.getSelectionRows(), ",");
				comp.setSelectionRows(rows);
			}
		}else if("jviewport".equals(name)){
			JViewport comp = (JViewport) c;
			if(AttributeValidator.size(attr.getExtentSize())){
				int[] arr = AttributeTransfer.size(attr.getExtentSize());
				comp.setExtentSize(new Dimension(arr[0], arr[1]));
			}
			if(AttributeValidator.size(attr.getViewPosition())){
				int[] arr = AttributeTransfer.size(attr.getViewPosition());
				comp.setViewSize(new Dimension(arr[0], arr[1]));
			}
		}else if("jeditorpane".equals(name)){
			JEditorPane comp = (JEditorPane) c;
			setTextComponentAttribute(comp, attr);
		}else if("jdesktoppane".equals(name)){
		}else if("jformattedtextfield".equals(name)){
			JFormattedTextField comp = (JFormattedTextField) c;
			setTextComponentAttribute(comp, attr);
		}else if("jpasswordfield".equals(name)){
			JPasswordField comp = (JPasswordField) c;
			setTextComponentAttribute(comp, attr);
		}else if("jtextpane".equals(name)){
			JTextPane comp = (JTextPane) c;
			setTextComponentAttribute(comp, attr);
		}
	}
}