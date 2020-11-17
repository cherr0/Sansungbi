package GUI;

import VO.AcidRain;
import VO.Message;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {

    private JFrame f;                                    // 기본 프레임


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





    private ClientPanel gamePanel;

    // 통신 관련 변수
    // 추후 사용 예정
    private Socket s;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    // 단어 받아오는 리스트
    ArrayList<AcidRain> rList;


    // 스레드 시작 flag
    private boolean startFlag = false;

    public Client() {
        setGUI(); // 객체 생성 시 GUI 셋팅

        try{

        }catch (Exception e){
            System.out.print("클라이언트 접속 오류: " + e);
        }
    }

    void setGUI() {

    }

    public String getLocalIP() {

        String localIP = "127.0.0.1";

        try {
            localIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("Local IP 얻기 실패" + e);
        }

        return localIP;

    }

    void selectWordTypeName() {
        AcidRain acidrain = new AcidRain();
        acidrain.setTypeidx(4);

        Message msg = new Message();
        msg.setType(4);
        msg.setAcidrain(acidrain);
    }
}
