package client;

import GUI.*;
import VO.AcidRain;
import VO.Message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MyListener implements ActionListener {

    MyFrame f;
    Client c;
    ClientPanel cp;
    EPanel ep;
    SPanel sp;
    NPanel np;

    // 통신 관련 변수
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    // 콘솔 출력 thread 테스트용 리스트, 스레드
    private ArrayList<String> typeList;
    private ArrayList<String> wordList = new ArrayList<String>();
    private ThreadTest ttest;

    public MyListener(Client c, ObjectInputStream ois, ObjectOutputStream oos) {

    }

    public MyListener(ClientPanel cp) {
        this.cp = cp;
    }

    public MyListener(MyFrame f){
        this.c = f.client;
        this.f = f;
        this.cp = f.cPanel;
        this.ep = f.ePanel;
        this.sp = f.sPanel;
        this.np = f.nPanel;
        this.ois = c.ois;
        this.oos = c.oos;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         String cmd = e.getActionCommand();

         // 버튼별로 커맨드를 지정하여 그것에 맞는 행동 실행
         switch(cmd){
             case "chat" :
                 break;
             case "start":
                 c.isReady();
                 break;
             case "enter":
                 c.enterWords();
                 break;
             case "sign":
                 System.out.println("sign 버튼 입력 받음");
                 c.nameInsertUpdate();
                 break;
         }
    }
}

