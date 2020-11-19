package client;

import GUI.ClientPanel;
import GUI.EPanel;
import GUI.MyFrame;
import VO.AcidRain;
import VO.Message;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {

    private MyFrame f;                                    // 기본 프레임
    private ClientPanel cp;
    private EPanel ep;
    String title = "산성비";

    // 클라이언트별 Info
    private int score;              // 점수
    private String name;            // 이름
    private String oldName;         // 변경 시 이전 이름
    private int nameChange = 1;     // 첫 접속 후 이름 등록 시에는 삽입, 그 이후는 변경
    private int mtState;            // 현재 상태 표시
    private int myidx = 0;          // 서버에서 자신의 번호


    // 상대 점수
    private ArrayList<AcidRain> others; // 다른 유저들의 이름, 점수
    private ArrayList<String> users = new ArrayList<String>();

    // 콘솔 출력 thread 테스트용 리스트와 스레드
    private ArrayList<String> typeList;
    private ArrayList<String> wList = new ArrayList<>();
    private ThreadTest ttest;


    // 통신 관련 변수
    // 추후 사용 예정
    private Socket s;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    // 단어 받아오는 리스트
    ArrayList<AcidRain> rList;


    // 스레드 시작 flag
    private boolean startFlag = false;


    // 생성자
    public Client() {
        setGUI(); // 객체 생성 시 GUI 셋팅

        try{

        }catch (Exception e){
            System.out.print("클라이언트 접속 오류: " + e);
        }
    }

    // 추후 연결
    void setGUI() {
        MyFrame f = new MyFrame(title);
    }

    void insertUser() {
        // 입력한 유저이름을 가져옴
        f.nPanel.tfUsername.getText();

        AcidRain rain = new AcidRain();
        rain.setUsername(name);
        rain.setIp(getLocalIP());

        // 서버 소켓 연동
        Message msg = new Message();
        msg.setType(0);
        msg.setAcidrain(rain);

        try {
            oos.writeObject(msg);
            System.out.println("전송 성공");
        }catch(IOException e) {
            System.out.println("유저 이름 전송 오류: " + e);
        }
    }

    void selectWords() {
        // 0=insert     1=select     2=update     3=delete
        int typeidx = 0;

        for(int i=0 ; i<typeList.size() ; i++) {
            String typeSelect = f.ePanel.typeSelect.getText();
            if(typeSelect.equals(typeList.get(i))){
                typeidx = i+1;
            }
        }
        System.out.println("typeidx : " + typeidx);

        AcidRain acidRain = new AcidRain();

        acidRain.setTypeidx(1);     // select
        acidRain.setTypeidx(typeidx);

        Message msg = new Message();
        msg.setType(1);
        msg.setAcidrain(acidRain);
        msg.setPanelState(cp.getPanelState());
        System.out.println("msg의 typeidx: " + msg.getAcidrain().getTypeidx());

        try{
            oos.writeObject(msg);
            System.out.println("msg sent well");
        }catch(IOException e){
            System.out.println("msg sent error: " + e);
        }
    }

    void updateUserScore() {

    }

    void updateUserName() {
        // 유저 이름 업데이트
        AcidRain acidRain = new AcidRain();
        acidRain.setUsername(name);
        System.out.println("");
    }
    // localhost ip 얻어오기
    public String getLocalIP() {

        String localIP = "127.0.0.1";

        try {
            localIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("Local IP 얻기 실패" + e);
        }

        return localIP;

    }

    // 게임 시에 사용할 단어 타입 설정
    void selectWordTypeName() {
        AcidRain acidrain = new AcidRain();
        acidrain.setTypeidx(4);

        Message msg = new Message();
        msg.setType(4);
        msg.setAcidrain(acidrain);

        String tempR = null;

        try{
            oos.writeObject(msg);   // 보드말고 메세지로 객체화 시켜 보낸다
            System.out.println("메세지 전송 완료");

            msg = (Message) ois.readObject();

            rList = msg.getList();
            System.out.println("rList is empty?" + (rList == null ? "O" : "X"));

            for(int i=0 ; i<rList.size() ; i++) {
                tempR = rList.get(i).getTypename();
                System.out.println("tempR : " + tempR);
            }

        }catch(IOException e){
            System.out.println("msg selectDefault err: " + e);
        }catch(ClassNotFoundException e){
            System.out.println("msg 잘못 받음. selectDefault err: " + e);
        }

        typeList = new ArrayList<String>();

        for(int i=0 ; i<rList.size() ; i++) {
            System.out.println(rList.get(i).getTypename());
            f.ePanel.typeList.append(rList.get(i).getTypename() + "\n");

            // ePanel의 typeList와 다른 것
            //
            typeList.add(i, rList.get(i).getTypename());
        }
    }

    // 게임이 끝날경우 알리기
    // start 버튼 활성화
    public void gameIsOver(){
        JOptionPane.showConfirmDialog(f, "Round Over","Weak Acid Rain Alert", JOptionPane.INFORMATION_MESSAGE);
        ep.start.setEnabled(true);
    }

    public void printOnMyConsole(String s){
        System.out.println("전달받은 스트링: " + s);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // 클라이언트 종료 시 통신 관련 객체 닫아주기
   public void exitClient() {
        Message msg = new Message();
        msg.setType(9);

        try{
            if(oos != null){
                oos.writeObject(msg);
            }
        }catch(IOException e){
            System.out.println("exitClient err: " + e);
        }finally{
            if(ois != null){
                try{
                    ois.close();
                }catch(IOException e){}
            }

            if(oos != null){
                try{
                    oos.close();
                }catch(IOException e){}
            }

            if(s != null){
                try{
                    s.close();
                }catch(IOException e){}
            }
        }

        // 창 사라지게하고 끄기
        f.setVisible(false);
        System.exit(0);
    }
}
