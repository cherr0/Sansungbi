package client;

import GUI.*;
import VO.Message;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MyListener implements ActionListener {

    MyFrame f;
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

    public MyListener(ClientPanel cp) {
        this.cp = cp;
    }

    public MyListener(EPanel ep){
        this.ep = ep;
    }

    public MyListener(SPanel sp){
        this.sp = sp;
    }

    public MyListener(NPanel np){
        this.np = np;
    }

    public MyListener(MyFrame f){
        this.f = f;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         String cmd = e.getActionCommand();

         // 버튼별로 커맨드를 지정하여 그것에 맞는 행동 실행
         switch(cmd){
             case "chat" :
                 break;
             case "Start":
                 isReady();
                 break;
         }
    }

    // 게임 시작 준비
    void isReady() {
        cp.setPanelState(ClientPanel.PANEL_STATE_ISREADY);
        System.out.println("isReady() 메소드 시작");

        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            System.out.println("isReady sleep err:" + e);
        }

        // 애니메이션 시작 flag
        cp.onOffThread();

        ep.start.setEnabled(false);
    }

    void enterWords() {
        // 입력란에 입력한 단어 가져오기
        String input = sp.tfEntry.getText();

        if(input == null){
            sp.tfEntry.setText("");
            return;
        }
        System.out.println("단어 입력 Check,input : " + input);
        Message msg = new Message();
        msg.setType(34);
        System.out.println("입력단어 서버로 보내는 중");
        msg.setEntryString(input);

        try{
            oos.writeObject(msg);
        }catch(IOException e){
            System.out.println("entryMsg send err : " + e);
        }

        // 단어 전송했으면 비워주기
        sp.tfEntry.setText("");
    }


    // 단어 맞는지 확인
    public void matchWord(String input){
        cp.matchWord(input);
    }
}
