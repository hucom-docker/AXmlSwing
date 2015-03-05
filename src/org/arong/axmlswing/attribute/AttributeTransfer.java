package org.arong.axmlswing.attribute;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.arong.axmlswing.manager.ColorManager;
import org.arong.axmlswing.manager.ComponentManager;
import org.arong.axmlswing.manager.CursorManager;
import org.arong.axmlswing.manager.FontManager;

/**
 * 属性转换，与AttributeValidator配合使用
 * @author dipoo
 * @since 2015-01-29
 */
public class AttributeTransfer {
	
	public static int[] size(String value){
		String[] arr = value.split(",");
		int[] ret = new int[2];
		for(int i = 0; i < arr.length; i ++){
			ret[i] = Integer.parseInt(arr[i].trim());
		}
		return ret;
	}
	
	public static int[] intArray(String value, String tag){
		String[] a = value.split(tag);
		int[] ints = new int[a.length];
		for(int i = 0; i < a.length; i++){
			ints[i] = Integer.parseInt(a[i]);
		}
		return ints;
	}
	
	public static int[] bounds(String value){
		String[] arr = value.split(",");
		int[] ret = new int[4];
		for(int i = 0; i < arr.length; i ++){
			ret[i] = Integer.parseInt(arr[i].trim());
		}
		return ret;
	}
	
	public static Cursor cursor(String value){
		return CursorManager.getCursors().get(value.toUpperCase());
	}
	
	public static Color color(String value){
		Color color = ColorManager.getColors().get(value.toUpperCase());
		if(color == null){
			try {
				color = new Color(Integer.parseInt(value, 16));
				ColorManager.getColors().put(value.toUpperCase(), color);
            } catch(NumberFormatException e) {
            }
		}
		return color;
	}
	
	public static Font font(String value){
		Font font = FontManager.getFonts().get(value);
		if(font == null){
			String[] arr = value.split(",");
			font = new Font(arr[0], Integer.parseInt(arr[1].trim()), Integer.parseInt(arr[2].trim()));
			FontManager.getFonts().put(value, font);
		}
		return font;
	}
	
	public static Icon icon(String value){
		return new ImageIcon(value);
	}
	
	/**
	 * 解析表达式返回鼠标接听器<br>
	 * 格式为：action:id1|id2<br/>
	 * action:动作指令
	 * id:组件id
	 */
	public static MouseListener onclick(final String value, final int clickCount){
		return new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() != clickCount)
					return;
				actionEvent(e.getSource(), value);
			}
		};
	}
	
	public static ActionListener actionListener(final String value){
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionEvent(e.getSource(), value);
			}
		};
	}
	
	public final static void actionEvent(Object source, String value){
		String[] a = value.split(":");
		String action = a[0];
		//退出应用程序:exit
		if("exit".equals(action)){
			System.exit(0);
		}else if(a.length == 2){
			if("this".equals(a[1].trim())){
				setAction((Component)source, action);
			}else{
				String[] compsId = a[1].split("\\|");
				//关闭某些窗口：close:helpWindow|settingWindow
				Component c;
				for(String id : compsId){
					if("this".equals(id)){
						c = (Component)source;
					}else{
						c = ComponentManager.getComponent(id.trim());
					}
					if(c != null){
						setAction(c, action);
					}
				}
			}
		}
	}
	
	private static void setAction(Component c, String action){
		if("close".equals(action)){
			if(c instanceof Window){//只有Window的子类才能关闭
				((Window)c).dispose();
			}
		}else if("show".equals(action)){
			c.setVisible(true);
		}else if("hide".equals(action)){
			c.setVisible(false);
		}else if("enabled".equals(action)){
			c.setEnabled(true);
		}else if("disabled".equals(action)){
			c.setEnabled(false);
		}else if("click".equals(action)){
			MouseListener[] mls = c.getMouseListeners();
			if(mls != null){
				for (int i = 0; i < mls.length; i++) {
					mls[i].mouseClicked(new MouseEvent(c, 1, System.currentTimeMillis(), 1, 1, 1, 1, false, 1));
				}
			}
		}else if("dblclick".equals(action)){
			MouseListener[] mls = c.getMouseListeners();
			if(mls != null){
				for (int i = 0; i < mls.length; i++) {
					mls[i].mouseClicked(new MouseEvent(c, 1, System.currentTimeMillis(), 1, 1, 1, 2, false, 1));
				}
			}
		}
	}
}
