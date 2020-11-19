package client;

import VO.DrawWord;
import VO.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ReaderThreadClient extends Thread {
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

                switch (msgType){
                    case 101:
                        dwList = selectWords(msg);

                }

            }
        }catch(IOException e){
            System.out.println("readerThread err: " + e);
        }catch(ClassNotFoundException e){
            System.out.println("Msg readObject err: " + e);
        }
    }
}
