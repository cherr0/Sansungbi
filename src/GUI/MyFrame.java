package GUI;

import client.Client;
import client.MyListener;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

	MyListener ml;

	public Client client;
	public NPanel nPanel;
	public EPanel ePanel;
	public SPanel sPanel;
	public ClientPanel cPanel;

    public MyFrame(Client client,String name){
    	setTitle(name);
		Rectangle range = new Rectangle(0, 0, 700, 600);
		setBounds(range);		// 절대값으로 위치 설정
		setResizable(false);	// 창 크기 조절 불가
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		this.client = client;
		ml = new MyListener(this);


		nPanel = new NPanel(ml);
		cPanel = new ClientPanel(client);
		ePanel = new EPanel(ml);
		sPanel = new SPanel(ml);

		add(nPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);
		add(ePanel, BorderLayout.EAST);
		add(sPanel, BorderLayout.SOUTH);

		setVisible(true);
    }

    public void setMyListener(MyListener ml){
    	this.ml = ml;
	}
}
