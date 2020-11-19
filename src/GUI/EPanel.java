package GUI;

import client.MyListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class EPanel extends JPanel {

    private MyListener ml = new MyListener(this);

    public JPanel listPanel, playerPanel, typePanel, startPanel;    // 유저목록 패널, 게임 타입 확인 패널, 게임시작 패널
    public JTextArea userList, typeList;               // 접속한 유저목록, 단어 타입 목록
    public JTextField typeSelect;                      // 단어 타입 선택
    public JButton start;                      // 게임 스타트 버튼

    // border 윤곽 설정
    Border border = BorderFactory.createLineBorder(new Color(230,230,238),7);


   public EPanel() {
       setLayout(new BorderLayout());

       //플레이어 리스트 패널
       playerPanel = new JPanel(new BorderLayout());
       playerPanel.add(new JLabel("- - - players list - - -"),BorderLayout.NORTH);

       userList = new JTextArea();
       userList.setBorder(border);
       userList.setBackground(new Color(242,253,252));
       userList.setEditable(false);

       playerPanel.add(userList, BorderLayout.CENTER);



       // 게임 타입 리스트 패널
       typePanel = new JPanel(new BorderLayout());

       typeList = new JTextArea();
       typeList.setBackground(new Color(238,238,248));
       typeList.setBorder(border);
       typeList.setEditable(false);

       typePanel.add(typeList,BorderLayout.CENTER);




       // 타입 설정, 스타트 패널
       startPanel = new JPanel(new BorderLayout());
       startPanel.setBorder(border);

       typeSelect = new JTextField();
       // 커맨드, 리스너 추가
       typeSelect.setActionCommand("G");
       typeSelect.addActionListener(ml);
       typeSelect.setBorder(border);
       typeSelect.setBackground(new Color(232,240,220));

       start = new JButton("START GAME");
       start.setActionCommand("Start");
       start.addActionListener(ml);
       start.setBorder(border);

       startPanel.add(typeSelect,BorderLayout.NORTH);
       startPanel.add(start,BorderLayout.CENTER);

       // 플레이어, 게임 타입 리스트 묶어주기
       listPanel = new JPanel(new GridLayout(2,1));
       listPanel.add(playerPanel);
       listPanel.add(typePanel);

       // 설정한 패널 합치기
       add(listPanel,BorderLayout.CENTER);
       add(startPanel,BorderLayout.SOUTH);
   }
}
