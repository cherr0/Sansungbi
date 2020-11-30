package client;

import GUI.ClientPanel;
import GUI.MyFrame;
import VO.AcidRain;
import VO.DrawWord;
import VO.Message;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {

    public MyFrame f;                        // 기본 프레임
    public MyListener ml;

    String title = "산성비";

    // 클라이언트별 Info
    private int score;              // 점수
    private String name;            // 이름
    private String oldName;         // 변경 시 이전 이름
    private int nameChange = 1;     // 첫 접속 후 이름 등록 시에는 삽입, 그 이후는 변경
    private int myState;            // 현재 상태 표시
    private int myidx = 0;          // 서버에서 자신의 번호


    // 상대 점수
    private ArrayList<AcidRain> others; // 다른 유저들의 이름, 점수
    private ArrayList<String> users = new ArrayList<String>();

    // 콘솔 출력 thread 테스트용 리스트와 스레드
    private ArrayList<String> typeList;
    private ArrayList<String> wList = new ArrayList<>();


    // 통신 관련 변수
    private Socket s;
    public ObjectInputStream ois;
    public ObjectOutputStream oos;

    // 단어 받아오는 리스트
    ArrayList<AcidRain> rList;


    // 스레드 시작 flag
    private boolean startFlag = false;


    // 생성자
    public Client() {

        setGUI(); // 객체 생성 시 GUI 셋팅
        System.out.println("GUI 셋팅 완료");

        try{
            myState = Message.IS_CONNECTED;
            s = new Socket(getLocalIP(),5050);
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.flush();    // 출력 스트림을 비움
            ois = new ObjectInputStream(s.getInputStream());
            System.out.println("입출력 셋팅 완료");
            selectWordTypeName();   // wordType 가져와서 넣어줌
            System.out.println("wordType 셋팅 완료");

            searchUser();   // 첫 접속 시 유저 리스트 요청
            // 요청해서 준비된 리스트가 readerThread 시작되면서 바로 받음
            new ReaderThreadClient(this,ois).start();
            System.out.println("readerThread 시작 완료");


        }catch (Exception e){
            System.out.print("클라이언트 접속 오류: " + e);
        }
    }


    void setGUI() {
        f = new MyFrame(this, title);
    }

    // 추후 추가
    void updateUserScore() {

    }

    public void nameInsertUpdate() {
        String name = f.nPanel.tfUsername.getText().trim(); // 입력한 이름 가져옴
        System.out.println("이름 받아옴 " + name);
        switch (nameChange) {
            case 0:     // 두번째부터 update
                rename(name);   // 실제 클라이언트 이름 변경
                updateUser();   // 리스트 변경
                break;
            case 1:     // 이름을 받아와서 처음이면 insert
                insertUser(name);
                nameChange = 0;
                break;
        }

    }

    // 첫 접속 시 유저 갱신
    void searchUser(){
        Message msg = new Message();
        msg.setType(11);
        try{
            oos.writeObject(msg);
            System.out.println("서버에 유저 리스트 요청");
        }catch(IOException e){
            System.out.println("유저 리스트 요청 오류: " + e);
        }
    }

    // 유저 등록
    void insertUser(String name) {
        AcidRain rain = new AcidRain();
        rain.setName(name);     // 현재 이름 지정
        rain.setIp(getLocalIP());
        this.name = name;

        // 서버 소켓 연동
        Message msg = new Message();
        msg.setType(0);             // 0 : 서버에 유저 등록 메세지 타입
        msg.setAcidrain(rain);      // 유저의 이름 정보가 담겨있는 AcidRain 객체 메세지에 담아 전송

        try {
            oos.writeObject(msg);       // 담겨진 msg 객체를 서버로 보냄
            System.out.println("이름 전송 성공");
        }catch(IOException e) {
            System.out.println("유저 이름 전송 오류: " + e);
        }
    }

    // 유저 이름 업데이트
    void updateUser() {
        AcidRain rain = new AcidRain();
        rain.setName(name);
        System.out.println("DB로 보내기전 name: " + name + ", oldName: " + oldName );

        Message msg = new Message();
        msg.setType(22);
        msg.setAcidrain(rain);
        msg.setNameString(oldName);

        try{
            oos.writeObject(msg);
            System.out.println("updateUserName MSG sent well");

        }catch(IOException e){
            System.out.println("updateUserName MSG sent error: " + e);
        }
    }

    // 이름 변경 메소드
    void rename(String newName) {
        if(name.isEmpty()) return;
        oldName = name;
        name = newName;
        System.out.println("바꾸기 전 이름 : " + oldName);
        System.out.println("바뀐 이름 : " + name);

        updateUserName(name);   // DB 상의 이름 변경
        setFrameName(name);     // 객체에 저장된 이름 변경
    }

    // 내부 유저이름 변경
    void updateUserName(String newName) {
        if(users.isEmpty()) return;	// 텅빔이면 걍 나감
        System.out.println("화면상에서 newName 으로 바꾼다");
        System.out.println("newName 바꾸기 전 구이름: " + oldName);
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).equals(oldName)){
                users.set(i, newName);
            }
        }
        System.out.println("화면상에서 newName 으로 바꿨다");

    }

    public void setFrameName(String name){
        this.name = name;
    }


    // ip 얻어오기
    public String getLocalIP() {

        String localIP = "127.0.0.1";

        try {
            localIP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("Local IP 얻기 실패" + e);
        }

        return localIP;

    }

    // 게임 시작 준비
    void isReady() {
        myState = Message.IS_READY;
        f.cPanel.setPanelState(ClientPanel.PANEL_STATE_ISREADY);
        System.out.println("isReady() 메소드 시작");
        if(selectWords()){  // true : 타입지정실패, false : 타입 지정 완료
            JOptionPane.showMessageDialog(null, "타입을 잘못 지정하셨습니다",
                    "Message", JOptionPane.ERROR_MESSAGE);
            // parentComponent(null) = 화면 중앙에 출력
            f.ePanel.typeSelect.setText("");
            return;     // 타입 지정을 잘못하였기에 리턴
        }
        // selectWords()에서 지나고나면 ReaderThreadClient에서 입력을 받아서 실행됨


        try{ // 다른 유저가 준비될 때 까지 잠시 기다림
            Thread.sleep(3000);
        }catch(InterruptedException e){
            System.out.println("isReady sleep err:" + e);
        }


        // 애니메이션 시작 flag
        f.cPanel.onOffThread();

        f.ePanel.start.setEnabled(false);
    }

    boolean selectWords() {
        int typeidx = 0;

        for(int i=0 ; i<typeList.size() ; i++) {
            String typeSelect = f.ePanel.typeSelect.getText();  // 클라이언트가 정한 타입을 가져옴
            if(typeSelect.equals(typeList.get(i))) {
                typeidx = i + 1;
            }
        }

        if(typeidx==0){     //타입이 0이면 리스트에 없는 것을 적었기 때문에 잘못 지정한 것
           System.out.println("타입을 잘못 지정하셨습니다");
            return true;    // 지정된 타입이 아닌 다른 것을 적으면 리턴
        }

        System.out.println("typeidx : " + typeidx);

        AcidRain acidRain = new AcidRain();

        acidRain.setTypeidx(1);     // select
        acidRain.setTypeidx(typeidx);

        Message msg = new Message();
        msg.setType(1);
        msg.setAcidrain(acidRain);
        msg.setPanelState(f.cPanel.getPanelState());
        System.out.println("msg의 typeidx: " + msg.getAcidrain().getTypeidx());

        try{
            oos.writeObject(msg);
            System.out.println("단어 타입에 맞는 리스트 서버에 요청 완료");
        }catch(IOException e){
            System.out.println("msg sent error: " + e);
        }
        return false;
    }

    // Panel에 보낼 것
    public void sendPanelDrawWordList(ArrayList<DrawWord> dwList){
        System.out.println("서버에서 dwList를 받았다");
        f.cPanel.setDrawList(dwList);
        // dwList를 보내면 panel의 state를 received로 바꾼다
        // 그리고 그걸 서버로 보낸다
        // 서버에서 모든 user들의 panelState가 received면 start로 state를 바꿔준다
        // 그래야 시작 가능

    }

    public void sendPanelStateToServer(int panelState){
        Message msg = new Message();
        msg.setType(33);
        msg.setPanelState(panelState);
    }



    // 텍스트 입력을 받아온 후 비교하러 전송
    public void matchWord(String inputEntry){
        f.cPanel.matchWord(inputEntry);
    }

    // 단어 입력
    void enterWords() {
        // 입력란에 입력한 단어 가져오기
        String input = f.sPanel.tfEntry.getText();

        if(input == null){
            f.sPanel.tfEntry.setText("");
            return;
        }
        System.out.println("단어 입력 Check,input : " + input);
        Message msg = new Message();
        AcidRain rain = new AcidRain();
        rain.setName(name);
        msg.setAcidrain(rain);
        msg.setType(34);
        System.out.println("입력단어 서버로 보내는 중");
        msg.setEntryString(input);

        try{
            oos.writeObject(msg);
        }catch(IOException e){
            System.out.println("entryMsg send err : " + e);
        }

        // 단어 전송했으면 비워주기
        f.sPanel.tfEntry.setText("");
    }



    public void showUserList(String[] nameList){
        f.ePanel.userList.setText("");
        String[] list = nameList;
        for(String name : list){
            f.ePanel.userList.append(name + "\n");
        }
    }

    public void setPanelState(int panelState) {
        f.cPanel.setPanelState(panelState);
    }

    // 게임 시에 사용할 단어 타입 설정
    void selectWordTypeName() {

        Message msg = new Message();
        msg.setType(4);

        String tempR = null;


        try{

            oos.writeObject(msg);   // 보드말고 메세지로 객체화 시켜 보낸다

            System.out.println("타입 리스트 요청 완료");

            msg = (Message) ois.readObject();

            System.out.println(msg.getType());

            rList = msg.getList();
            System.out.println("rList is empty?" + (rList == null ? " O " : " X "));

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
        JOptionPane.showMessageDialog(f, "Round Over","Weak Acid Rain Alert", JOptionPane.INFORMATION_MESSAGE);
        f.ePanel.start.setEnabled(true);
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
