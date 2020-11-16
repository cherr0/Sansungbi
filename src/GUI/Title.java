package GUI;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Title extends JPanel {
	private JTextField textField;
//	public static ArrayList<JLabel> rainText = new ArrayList<>();
//	public static JLabel[] life = new JLabel[3];

	public static ImageIcon t_background, upIcon, downIcon;
	
	
	
	public Title() {
		setLayout(null);
		
		
		t_background = new ImageIcon("img/background-image.png");
		textField = new JTextField();
		textField.setBounds(290, 171, 53, 21);
		add(textField);
		textField.setColumns(10);
		
	}
	
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		g.drawImage(t_background.getImage(), 0, 0, null); // 0,0 좌표부터 이미지를 뿌림
//	}
}
