package GUI;

import VO.AcidRain;
import VO.Message;

import javax.swing.*;

public class Client {

    private JFrame f; // 기본 프레임
    private JPanel cPanel;  // 각각 기능별로 나눈 패널
    private JTextField tfTypeSelect, tfChat, tfUsername; // 단어입력란, 채팅입력란, 유저등록란
    private JTextArea userList; // 유저 표시 Area
    private JButton start, signup;


    public Client() {

        setGUI(); // GUI 셋팅

        try{


        }catch (Exception e){
            System.out.print("클라이언트 접속 오류: " + e);
        }
    }

    void setGUI() {

    }

    void selectWordTypeName() {
        AcidRain acidrain = new AcidRain();
        acidrain.setTypeidx(4);

        Message msg = new Message();
        msg.setType(4);
        msg.setAcidrain(acidrain);
    }
}
