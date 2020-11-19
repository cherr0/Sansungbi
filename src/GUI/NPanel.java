package GUI;

import client.MyListener;

import javax.swing.*;
import java.awt.*;


/* North Panel
   사용자 이름 등록, 수정란
*/
public class NPanel extends JPanel {


    public JTextField tfUsername; // 유저 이름 등록란
    public JButton signUp;        // 게임 스타트, 서버에 이름 등록

    public NPanel() {
        setLayout(new BorderLayout());
        JPanel wordPanel = new JPanel(new BorderLayout());
        MyListener ml = new MyListener(this);

        tfUsername = new JTextField();

        // 간결하게 테두리 작성
        tfUsername.setBorder(BorderFactory.createLineBorder(new Color(244,  244, 242), 5));
        tfUsername.setBackground(new Color(254, 254, 252));
        wordPanel.add(new JLabel(" NAME "), BorderLayout.WEST);
        wordPanel.add(tfUsername, BorderLayout.CENTER);

        // 유저 등록 버튼 생성
        signUp = new JButton("Sign");
        signUp.setActionCommand("Sign");
        signUp.addActionListener(ml); // 나중에 리스너 연결


        // 패널에 맞게 입력칸, 버튼 추가
        add(wordPanel, BorderLayout.CENTER);
        add(signUp, BorderLayout.EAST);
    }
}
