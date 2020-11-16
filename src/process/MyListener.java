package process;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
         String cmd = e.getActionCommand();

         // 버튼별로 커맨드를 지정하여 그것에 맞는 행동 실행
         switch(cmd){
             case "chat" :
                 break;
         }
    }

    private void isReady() {

    }
}
