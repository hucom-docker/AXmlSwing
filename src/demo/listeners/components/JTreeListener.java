package demo.listeners.components;

import java.awt.Container;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.arong.axmlswing.event.AbstractListener;
import org.arong.axmlswing.event.EventAnnotation;
@EventAnnotation("demo_JTree")
public class JTreeListener extends AbstractListener {
	public void init(Container c) {
		JTree jt = (JTree) c;
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("The Java Series");
		jt.setModel(new DefaultTreeModel(top));
	}
}
