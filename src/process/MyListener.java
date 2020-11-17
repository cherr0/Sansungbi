package process;

import GUI.ClientPanel;
import GUI.EPanel;
import GUI.NPanel;
import GUI.SPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyListener implements ActionListener {

    ClientPanel cp;
    EPanel ep;
    SPanel sp;
    NPanel np;

    // 콘솔 출력 thread 테스트용 리스트, 스레드
    private ArrayList<String> typeList;
    private ArrayList<String> wordList = new ArrayList<String>();
//    private ThreadTest ttest;

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

    @Override
    public void actionPerformed(ActionEvent e) {
         String cmd = e.getActionCommand();

         // 버튼별로 커맨드를 지정하여 그것에 맞는 행동 실행
         switch(cmd){
             case "chat" :
                 break;
         }
    }

    void isReady() {
        cp.setPanelState(ClientPanel.PANEL_STATE_ISREADY);
        System.out.println("isReady() 메소드 시작");

    }

    void selectWords() {
        int typeidx = 0;

    }
}
