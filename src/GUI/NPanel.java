package GUI;

import client.MyListener;

import javax.swing.*;
import java.awt.*;


/* North Panel
   사용자 이름 등록, 수정란
*/
public class NPanel extends JPanel {


    public JTextField tfUsername; // 유저 이름 등록란
    public JButton signUp;        // 서버에 이름 등록

    public NPanel(MyListener ml) {
        setLayout(new BorderLayout());
        JPanel wordPanel = new JPanel(new BorderLayout());

        tfUsername = new JTextField();

        // 간결하게 테두리 작성
        tfUsername.setBorder(BorderFactory.createLineBorder(new Color(244,  244, 242), 5));
        tfUsername.setBackground(new Color(254, 254, 252));
        wordPanel.add(new JLabel(" NAME "), BorderLayout.WEST);
        wordPanel.add(tfUsername, BorderLayout.CENTER);

        // 유저 등록 버튼 생성
        signUp = new JButton("sign");
        signUp.setActionCommand("sign");
        signUp.addActionListener(ml);


        // 패널에 맞게 입력칸, 버튼 추가
        add(wordPanel, BorderLayout.CENTER);
        add(signUp, BorderLayout.EAST);
    }
}
