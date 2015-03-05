package demo.listeners.components;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.arong.axmlswing.GuiXmlLoader;
import org.arong.axmlswing.event.AbstractListener;
import org.arong.axmlswing.event.EventAnnotation;
import org.arong.axmlswing.manager.ComponentManager;
@EventAnnotation("xmlList")
public class XmlListListener extends AbstractListener{
	
	public void init(Container c) {
		//扫描layout.components包下的所有布局文件并显示
		JList xmlList = (JList) ComponentManager.getComponent("xmlList");
		File dataFile = new File(XmlListListener.class.getResource("/").getPath() + "layout/components/");
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
		//双击
		if(2 == e.getClickCount()){
			JList xmlList = (JList) e.getSource();
			String layoutFile = xmlList.getSelectedValue().toString();
			GuiXmlLoader.load(layoutFile);
		}
		
	}
}