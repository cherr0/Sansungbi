package client;

import VO.DrawWord;
import VO.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class ReaderThreadClient extends Thread {
    // 클라이언트에서 돌아가는 스레드
    // 서버에서 보낸 메세지를 읽어 행동 수행

    private Client client;
    private ObjectInputStream ois;


    // 생성자
    public ReaderThreadClient(Client client, ObjectInputStream ois){
        this.client = client;
        this.ois = ois;
    }


    // ====== C R U D ====== //

    public ArrayList<DrawWord> selectWords(Message msg){
        Message tempMsg = new Message();
        ArrayList<DrawWord> tempDWList = msg.getDWList();

        return tempDWList;
    }

    public void run() {
        Message msg = null;
        ArrayList<DrawWord> dwList = null;

        int msgType = 0;
        int panelState = 0;
        String inputEntry = "";

        try{
            while(true){
                msg = (Message) ois.readObject();

                msgType = msg.getType();
                // 5 유저리스트 refresh, 6: userScore refresh
                switch (msgType){
                    case 101:
                        dwList = selectWords(msg);
                        client.sendPanelDrawWordList(dwList);
                        break;
                    case 11:    // 유저 리스트 갱신
                        String[] nameList = msg.getuListString().split(",");
                        System.out.println("유저 리스트: " + Arrays.toString(nameList)); // 콘솔에 유저 리스트 출력
                        client.showUserList(nameList);
                        break;
                    case 12:    // 서버에서 DrawWordList 받아온다
                        dwList = msg.getDWList();
                        client.sendPanelDrawWordList(dwList);
                        // server에서 panelstate를 받아온 다음 클라를 통해 넣는다
                        panelState = msg.getPanelState();
                        client.setPanelState(panelState);
                        break;
                    case 14:
                        inputEntry = msg.getEntryString();
                        client.matchWord(inputEntry);
                        break;
                } //switch end
            }// while end
        }catch(IOException e){
            System.out.println("readerThread err: " + e);
        }catch(ClassNotFoundException e){
            System.out.println("Msg readObject err: " + e);
        }
    }
}
