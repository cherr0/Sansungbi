package GUI;

import client.MyListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/*
    South Panel
    채팅 입력, 단어 입력 창
 */
public class SPanel extends JPanel {

    public JPanel wordPanel, chatPanel;      // 단어 입력 패널, 채팅 입력 패널,
    public JTextField tfEntry, tfChat;     // 단어입력란, 채팅입력란
    public JButton chatBtn, wordBtn;       // 채팅 버튼, 단어 버튼
    // border 윤곽 설정
    private Border border = BorderFactory.createLineBorder(new Color(230,230,238),5);

    public SPanel(MyListener ml) {
        // 레이아웃 설정

        setLayout(new GridLayout(2,1));
        wordPanel = new JPanel(new BorderLayout());
        chatPanel = new JPanel(new BorderLayout());
        setBorder(border);


        wordBtn = new JButton("ENTER");
        chatBtn = new JButton("ENTER");
        tfEntry = new JTextField();
        tfChat = new JTextField();

        wordBtn.setActionCommand("word");
        chatBtn.setActionCommand("chat");
        chatBtn.addActionListener(ml); // 추후 수정 예정
        wordBtn.addActionListener(ml);

        // 단어입력 레이아웃
        wordPanel.add(new JLabel("Write Here >"), BorderLayout.WEST);
        wordPanel.add(tfEntry, BorderLayout.CENTER);
        wordPanel.add(wordBtn, BorderLayout.EAST);

        // 채팅 레이아웃
        chatPanel.add(new JLabel("Enter Chat >"), BorderLayout.WEST);
        chatPanel.add(tfChat, BorderLayout.CENTER);
        chatPanel.add(chatBtn, BorderLayout.EAST);

        add(wordPanel);
        add(chatPanel);
    }
}
