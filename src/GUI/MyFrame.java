package GUI;

import client.Client;
import client.MyListener;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

	MyListener ml;

	public NPanel nPanel;
	public EPanel ePanel;
	public SPanel sPanel;
	public ClientPanel cPanel;

    public MyFrame(String name){
    	setTitle(name);
		Rectangle range = new Rectangle(0, 0, 700, 600);
		setBounds(range);		// 절대값으로 위치 설정
		setResizable(false);	// 창 크기 조절 불가
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		ml = new MyListener(this);

		Client client = new Client();


		nPanel = new NPanel();
		cPanel = new ClientPanel(client);
		ePanel = new EPanel();
		sPanel = new SPanel();

		add(nPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);
		add(ePanel, BorderLayout.EAST);
		add(sPanel, BorderLayout.SOUTH);

		setVisible(true);
    }
}
