package demo.extend;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class DemoBox extends JPanel {
	private static final long serialVersionUID = 7184269743769427783L;
	
	private String text;
	
	private JTextField f = new JTextField();
	
	public DemoBox(){
		this.add(f);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		if(text != null){
			f.setText(text);
		}
	}
	
}
