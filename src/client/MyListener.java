package client;

import GUI.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class MyListener implements ActionListener, KeyListener {

    MyFrame f;
    Client c;
    ClientPanel cp;
    EPanel ep;
    SPanel sp;
    NPanel np;

    public MyListener(MyFrame f){
        this.c = f.client;
        this.f = f;
        this.cp = f.cPanel;
        this.ep = f.ePanel;
        this.sp = f.sPanel;
        this.np = f.nPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         String cmd = e.getActionCommand();

         // 버튼별로 커맨드를 지정하여 그것에 맞는 행동 실행
         switch(cmd){
             case "chat" :
                 break;
             case "start":
                 System.out.println("start 버튼 입력 받음");
                 c.isReady();
                 break;
             case "word":
                 System.out.println("word 버튼 입력 받음");
                 c.enterWords();
                 break;
             case "sign":
                 System.out.println("sign 버튼 입력 받음");
                 c.nameInsertUpdate();
                 break;
         }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // 좌판의 어떤 키로 문자가 눌렸을 때 실행
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // 키보드가 눌렸을 때 실행
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            System.out.println("word 버튼 입력 받음");
            c.enterWords();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // 키보드의 키가 눌렸다 때었을 때 실행


    }
}

