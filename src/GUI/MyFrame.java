package GUI;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

	NPanel nPanel;
	ClientPanel cPanel;

    public MyFrame(String name){
    	setTitle(name);
		Rectangle range = new Rectangle(0, 0, 700, 600);
		setBounds(range);		// ���밪���� ��ġ ����
		setResizable(false);	// â ũ�� ���� �Ұ�
		setLayout(new BorderLayout());

		Client client = new Client();


		nPanel = new NPanel();
		cPanel = new ClientPanel(client);

		add(nPanel, BorderLayout.NORTH);
		add(cPanel, BorderLayout.CENTER);
    }
}
