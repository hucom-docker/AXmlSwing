package org.arong.axmlswing.manager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.arong.axmlswing.GuiXmlLoader;
import org.arong.axmlswing.attribute.AttributeModel;
import org.arong.axmlswing.util.BeanUtil;
import org.arong.axmlswing.util.Dom4jUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * xml文件变量管理器
 * @author dipoo
 * @since 2015-01-30
 */
@SuppressWarnings("unchecked")
public class VarsManager {
	
	private static Map<String, String> vars = new HashMap<String, String>();
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 组件全局属性
	 */
	private static Map<String, AttributeModel> defaults = new HashMap<String, AttributeModel>();
	
	public final static String CONFIG_FILE_NAME = "/axmlswing.cfg.xml";
	
	public final static String SCAN_PACKAGE = "scan-package-xxx";
	
	public final static String EXTENDS_PREFFIX = "extends-";
	
	static{
		String rootPath = ClassLoader.getSystemResource("").getPath();
		vars.put("rootPath", rootPath);
		//加载配置文件
		try {
			Document doc = Dom4jUtil.getDOM(rootPath + CONFIG_FILE_NAME);
			Node root = doc.selectSingleNode("/configuation");
			//修复单词错误，同时configuation还可以使用
			if(root != null || (root = doc.selectSingleNode("/configuration")) != null){
				//包扫描路径
				Node n = root.selectSingleNode("scan-package");
				if(n != null){
					vars.put(SCAN_PACKAGE, n.getText());
				}
				//自定义变量
				n = root.selectSingleNode("properties");
				if(n != null){
					List<Node> ns =  n.selectNodes("property");
					if(ns != null && ns.size() > 0){
						for(Node node : ns){
							vars.put(((Element)node).attributeValue("name"), node.getText());
						}
					}
				}
				//标签全局属性
				n = root.selectSingleNode("tag-default");
				if(n != null){
					List<Node> ns =  n.selectNodes("tag");
					if(ns != null && ns.size() > 0){
						List<Node> attrs;
						for(Node tag : ns){
							attrs = tag.selectNodes("attr");
							if(attrs != null && attrs.size() > 0){
								AttributeModel model = new AttributeModel();
								for(Node attr : attrs){
									BeanUtil.setProperty(model, ((Element)attr).attributeValue("name"), new String[]{attr.getText()});
								}
								defaults.put(((Element)tag).attributeValue("name").toLowerCase(), model);
							}
						}
					}
				}
				//组件扩展
				n = root.selectSingleNode("extends");
				if(n != null){
					List<Node> ns =  n.selectNodes("component");
					Element e;
					if(ns != null && ns.size() > 0){
						for(Node comp : ns){
							e = (Element) comp;
							vars.put(EXTENDS_PREFFIX + e.attributeValue("name").toLowerCase(), e.getText());
						}
					}
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//配置文件不是必需的
			if(GuiXmlLoader.getLog()){
				Logger.getLogger("axmlswing").info("[axmlswing]没有找到配置文件,配置文件路径应为:" + rootPath + CONFIG_FILE_NAME);
			}
		}
	}
	
	public static Map<String, String> getVars() {
		return vars;
	}
	
	public static String getVarValue(String name){
		return getVars().get(name);
	}
	
	public static Map<String, AttributeModel> getDefaults() {
		return defaults;
	}
	
	public static String getExtentsCompValue(String name){
		return vars.get(EXTENDS_PREFFIX + name);
	}
	
	/**
	 * 占位符替换
	 */
	public static String transfer(String value){
		if(value == null)
			return null;
		String str = value.trim();
		vars.put("now", sdf.format(new Date()));
		for(String key : vars.keySet()){
			str = str.replaceAll("\\$\\{" + key + "\\}", vars.get(key));
		}
		return str;
	}
}
