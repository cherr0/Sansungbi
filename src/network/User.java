package network;

import DB.RainDAO;
import VO.AcidRain;
import VO.DrawWord;
import VO.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class User extends Thread {

    DBServer server;
    Socket s;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    RainDAO dao;

    // 클라이언트별 info
    String name;
    static int nameCnt = 0;
    // 객체가 여러번 만들어져도 static 이라 하나만 설정됨
    int panelState = 0;

    boolean onAir = true;

    // DrawList 생성
    ArrayList<DrawWord> drawWordList;
    Random random;
    int currentLevel = 1;



    // 생성자
    // 모든 state == isReady인 유저들에게 DrawWordList 전송
    public User(DBServer server, Socket s){
        name = "user" + ++nameCnt;      // 유저 접속번호 표시용
        this.server = server;
        this.s = s;
        random = new Random();
        dao  = new RainDAO();

        try{
            ois = new ObjectInputStream( s.getInputStream() );
            oos = new ObjectOutputStream( s.getOutputStream() );

            oos.flush();
        }catch(IOException e){
            System.out.println("User 생성자 오류: " + e);

        }
    }


    //======= 클라이언트 별 전송 파트 =======//

    public void sendMessage(int msgType, String str){
        try{
            Message msg = new Message();

            msg.setType(msgType);
            msg.setuListString(str);
            oos.writeObject(msg);
        }catch(IOException e){
            System.out.println("sendMessage 에러: " + e);
        }
    }

    public void sendEntryMessage(int msgType, String entry){
        try{
            Message msg = new Message();

            msg.setType(msgType);
            msg.setEntryString(entry);
            System.out.println("EntryCheck");
            oos.writeObject(msg);
        }catch(IOException e){
            System.out.println("sendEntryMessage Err: " + e);
        }
    }

    // 각 클라이언트에게 만들어진 DrawList 전송
    public void sendDWList(int msgType, ArrayList<DrawWord> dwList) {
        try{
            Message msg = new Message();

            msg.setType(msgType);
            msg.setDWList(dwList);
            oos.writeObject(msg);
        }catch(IOException e){
            System.out.println("Send DrawWordList Message err: " + e);
        }
    }


    // panel state 변경
    public void sendPanelState(int msgType, int panelState) {
        try{
            Message mes = new Message();

            mes.setType(msgType);
            mes.setPanelState(panelState);
            oos.writeObject(mes);

        }catch(IOException e){
            System.out.println("Send PanelState Message err: " + e);
        }
    }


    //============ C R U D =============//
    // Create, Read, Update, Delete

    // 이름 변경 (최초 1회 등록 이후, 등록 대신 변경
    void changeName(AcidRain acidRain) {
        this.name = acidRain.getUsername();
    }

    // 유저 등록
    void insertUser(AcidRain acidRain) {
        dao.insertUser(acidRain);

        changeName(acidRain);
    }

    // 이름 변경
    void updateUserName(AcidRain acidRain, String oldName) {
        dao.updateUserName(acidRain, oldName);

        changeName(acidRain);
    }

    // 유저 스코어 등록
    void updateUserScore(AcidRain acidRain) {
        dao.updateUserScore(acidRain);
    }

    // 유저 삭제
    void deleteUser(AcidRain acidRain) {
        dao.deleteUser(acidRain);
    }


    // AcidRain 객체의 word 받아오기
    Message selectWords(AcidRain acidRain) {
        Message msg = new Message();
        msg = dao.selectWords(acidRain);
        System.out.println("selectWord 메소드 실행");

        return msg;
    }


    void selectWordTypeName(AcidRain acidRain) {
        Message msg = dao.selectWordTypeName(acidRain);

        try{
            oos.writeObject(msg);
            System.out.println("리스트 사이즈 : " + msg.getList().size());
        }catch(IOException e){
            System.out.println("서버에서 받아온 typename 에러 : " + e );
        }
    }

    // ====== 스레드 실행 메소드 ======= //
    public void run() {
        Message msg = null;
        int msgType = 0;
        String entryTemp = "";

        try{
            while(onAir){
                msg = (Message) ois.readObject();
                msgType = msg.getType();

                switch(msgType){
                    case 0:     // 유저 등록
                        insertUser(msg.getAcidrain());

                    case 1:
                        Message tempMsg = msg;
                        int typeIdx = 0;
                        panelState = msg.getPanelState();
                }
            }
        }catch (IOException e){
            System.out.println("입출력 err : " + e);
        }catch (ClassNotFoundException  e){
            System.out.println("클래스 찾을 수 없음 err : " + e);
        }
    }

    public int getPanelState() {
        return this.panelState;
    }

    public void setPanelState(int panelState) {
        this.panelState = panelState;
    }

}
