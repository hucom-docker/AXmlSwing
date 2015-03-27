package demo;

import javax.swing.UIManager;

import org.arong.axmlswing.GuiXmlLoader;
import org.arong.util.FileUtil;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class Demo {
	public static void main(String[] args) throws Exception {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
		} catch (Exception e) {

		}
		long s = System.currentTimeMillis();
		String dir = FileUtil.getProjectPath();
		//加载布局文件
		//GuiXmlLoader.load(dir + "/layout/demo.xml");
		//GuiXmlLoader.load(dir + "/layout/demo2.xml");
		
		//GuiXmlLoader.load(dir + "/layout/swing-generate.xml");
		
		GuiXmlLoader.load(dir + "/layout/components.xml");
		System.out.println("渲染时间："+ (System.currentTimeMillis() - s));
	}
}