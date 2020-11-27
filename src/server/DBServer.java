package server;

import GUI.ClientPanel;
import VO.AcidRain;
import VO.DrawWord;
import VO.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

// 매번 만들어지는 클라이언트에 대응하는 소켓을 가진 Comm 스레드
// 묶어서 관리할 객체
public class DBServer {

    ArrayList<User> userList = new ArrayList<User>();
    ArrayList<DrawWord> dwList;
    Random random = new Random();
    int currentLevel = 1;

    ServerSocket ss;
    Socket s;


    public DBServer() {
        ss = null;
        s = null;
        try{
            ss = new ServerSocket(5050);
            System.out.println("서버 생성 성공!");
        }catch (IOException e) {
            System.out.println("서버 소켓 에러: " + e);
            return; // 서버 소켓 생성 실패 시 메소드 종료
        }


        while(true) {
            User user = null;
            String remoteIP = "";
            try{
                System.out.println("서버 접속 대기중");
                s = ss.accept();
                remoteIP = s.getInetAddress().getHostAddress(); // IP 가져옴

                System.out.println(remoteIP + "가 서버에 접속했습니다.");


                // 통신 스레드
                // 하나의 클라이언트마다 하나의 객체 생성
                user = new User(this, s);
                userList.add(user);     // 리스트에 유저 추가
                user.start();           // 스레드 하나 만들었으니 시작

            }catch(IOException e){
                System.out.println("소켓 생성 실패" + e);
            }
        }

    }


    // 접속한 유저리스트 받아옴
    public String getUserList() {
        if(userList.isEmpty()) return "";
        String list = "";
        StringBuilder sb = new StringBuilder();

        for(User ur : userList){
            sb.append(ur.name).append(",");
        }



        list = sb.toString();
        list = list.substring(0, list.length()-1 );
        System.out.println(list);
        return list;
    }


    // 서버에 놔두는 단 하나의 DrawWord 리스트를 각 user에게 전달해줌
    public void createDrawWordList(ArrayList<AcidRain> list){
        dwList = new ArrayList<DrawWord>();
        ArrayList<AcidRain> rList = list;

        int xCoord = 0;
        int yCoord = 0;
        int deltaY = 0;

        for(int i=0 ; i< list.size() ; i++) {
            xCoord = random.nextInt(450);
            // 0~450 사이 x 좌표값 랜덤하게 설정
            yCoord = random.nextInt(600)-600;
            // 화면 제일 위에서 보여줘야 하지만 떨어지는 순서는 랜덤이기 때문에 -600 지정
            deltaY = random.nextInt(10) + 15 * currentLevel;
            // 각 단어마다 랜덤한 속도로 떨어지고 단계에 따라 속도를 늘어나게 함

            dwList.add(new DrawWord(xCoord, yCoord,
                    rList.get(i).getWord(), deltaY));
        }
        System.out.println("createDrawWordList 메소드 실행 끝");
    }

    // ready 상태인 클라이언트들에게 drawWordList를 뿌림
    public void sendDrawWordListToAll() {
        for(User user : userList){
            user.sendDWList(101, dwList);
        }
    }

    // 클라이언트에게 유저리스트 전송
    public void sendUserList(User user){
        user.sendMessage(11, getUserList());
    }

    // 유저 퇴장 시 user객체 제거
    public void exitUser(User user){
        userList.remove(user);
    }

    // 모든 유저에게 해당하는 내용 전송
    public void sendMsgToAll(int protocol, String str){
        for(User user : userList){
            user.sendEntryMessage(protocol,str);
        }
    }

    public void sendInputEntryToAll(String str){
        System.out.println("Send Entry Check : " + str);
        for(User user : userList){
            user.sendEntryMessage(14, str);
        }
    }

    public void sendUserListToAll() {
        String ul = getUserList();
        for(User user : userList){
            user.sendMessage(11, ul);
        }
    }


    public int checkPanelStateAndTypeName(Message msg) {
        System.out.println("msg의 typeidx: " + msg.getAcidrain().getTypeidx());
        int userSize = 0;
        int typeIdx = 0;
        for(User user : userList){
            if(user.getPanelState() == ClientPanel.PANEL_STATE_ISREADY){
                userSize++;
                typeIdx = msg.getAcidrain().getTypeidx();
                System.out.println("type idx: " + typeIdx);
            }
        }
        if(userSize != userList.size()){
            System.out.println("모든 유저가 준비를 완료해야 합니다.");
            return 0;
        }
        System.out.println("모든 유저 준비 완료");
        System.out.println("panelStateAndTypeName 끝나기 직전 idx : " + typeIdx);
        return typeIdx;
    }

    public static void main(String[] args){
        new DBServer();
    }
}
