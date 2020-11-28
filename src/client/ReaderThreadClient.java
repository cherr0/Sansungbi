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
        Message msg = null;     // 서버와 주고받을 객체
        ArrayList<DrawWord> dwList = null;      // 단어 타입, 단어 리스트를 넣을 리스트

        int msgType = 0;
        int panelState = 0;
        String inputEntry = "";

        try{
            while(true){
                msg = (Message) ois.readObject();

                msgType = msg.getType();
                // 5 유저리스트 refresh, 6: userScore refresh
                switch (msgType){
                    case 101:   //단어 타입 리스트 서버에서 받음
                        dwList = selectWords(msg);      // 서버에 요청한 단어 타입 리스트를 내부 리스트에 담음
                        client.sendPanelDrawWordList(dwList);   // 리스트를 클라이언트에 보냄
                        break;
                    case 11:    // 유저 리스트 갱신
                        String[] nameList = msg.getuListString().split(",");    // , 단위로 이름을 나눠서 배열에 추가
                        System.out.println("유저 리스트: " + Arrays.toString(nameList)); // 콘솔에 유저 리스트 출력
                        client.showUserList(nameList);      // 클라이언트 상의 리스트에 서버에서 받은 유저 리스트를 보여줌
                        break;
                    case 12:    // 서버에서 DrawWordList 받아온다
                        dwList = msg.getDWList();       // 서버에서 받은 단어 리스트를 리스트에 넣음
                        client.sendPanelDrawWordList(dwList);
                        // server에서 panelstate를 받아온 다음 클라를 통해 넣는다
                        panelState = msg.getPanelState();
                        client.setPanelState(panelState);
                        break;
                    case 14:    // 다른 유저가 맞춘 단어를 클라이언트의 패널에서 삭제한다
                        inputEntry = msg.getEntryString();      // 서버에서 보낸 삭제된 단어를 변수에 넣음
                        client.matchWord(inputEntry);           // 클라이언트에서 그 단어가 있는지 찾아서 삭제함
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
