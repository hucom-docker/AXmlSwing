package demo.listeners.components;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.arong.axmlswing.GuiXmlLoader;
import org.arong.axmlswing.event.AbstractListener;
import org.arong.axmlswing.event.EventAnnotation;
import org.arong.axmlswing.manager.ComponentManager;
import org.arong.util.FileUtil;
@EventAnnotation("xmlList")
public class XmlListListener extends AbstractListener{
	
	public void init(Container c) {
		//扫描layout.components包下的所有布局文件并显示
		JList xmlList = (JList) ComponentManager.getComponent("xmlList");
		File dataFile = new File(FileUtil.getProjectPath() + "/layout/components/");
		if(!dataFile.exists()){
			
		}else{
			File[] files = dataFile.listFiles();
			final List<File> groups = new ArrayList<File>();
			for(File file : files){
				if(!file.isDirectory()){
					groups.add(file);
				}
			}
			if(groups.size() > 0){
				xmlList.setModel(new ListModel() {
					public void removeListDataListener(ListDataListener l) {}
					public int getSize() {
						return groups.size();
					}
					public Object getElementAt(int index) {
						return groups.get(index).getPath();
					}
					public void addListDataListener(ListDataListener l) {}
				});
				xmlList.updateUI();
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		final JList xmlList = (JList) e.getSource();
		if(MouseEvent.BUTTON1 == e.getButton()){
			//双击
			if(2 == e.getClickCount()){
				
				JDialog sourceFrame = (JDialog) ComponentManager.getComponent("xmlSourceWindow");
				sourceFrame.setTitle((String) xmlList.getSelectedValue());
				JTextArea tp = (JTextArea) ComponentManager.getComponent("xmlTextPanel");
				tp.setBorder(null);
				String t = "";
				try {
					t = FileUtil.getTextFromReader(new FileReader((String) xmlList.getSelectedValue()), true);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				tp.setText(t);
				tp.setCaretPosition(0);
				sourceFrame.setVisible(true);
				
				String layoutFile = xmlList.getSelectedValue().toString();
				GuiXmlLoader.load(layoutFile);
			}
		}else if(MouseEvent.BUTTON3 == e.getButton()){
			//选中
			xmlList.setSelectedIndex(xmlList.locationToIndex(e.getPoint()));
			JPopupMenu pm = (JPopupMenu) ComponentManager.getComponent("xmlPopupMenu");
			JMenuItem showSourceItem = (JMenuItem) ComponentManager.getComponent("showXmlSource");
			showSourceItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog sourceFrame = (JDialog) ComponentManager.getComponent("xmlSourceWindow");
					sourceFrame.setTitle((String) xmlList.getSelectedValue());
					JTextArea tp = (JTextArea) ComponentManager.getComponent("xmlTextPanel");
					tp.setBorder(null);
					String t = "";
					try {
						t = FileUtil.getTextFromReader(new FileReader((String) xmlList.getSelectedValue()), true);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					tp.setText(t);
					tp.setCaretPosition(0);
					sourceFrame.setVisible(true);
				}
			});
			pm.show(xmlList, e.getX(), e.getY());
		}
	}
}