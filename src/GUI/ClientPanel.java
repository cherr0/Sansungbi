package GUI;

import VO.DrawWord;
import client.Client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class ClientPanel extends JPanel {
    private ArrayList<String> typenameList; // 타입 이름 저장 리스트
    private ArrayList<String> wordList;     // 단어 저장 리스트
    private ArrayList<DrawWord> drawList;   // 단어와 좌표값 저장용 리스트

    //패널 쓰레드 pause flag
    public static int PANEL_STATE_CLOSED = 0;
    public static int PANEL_STATE_OPEN = 1;
    public static int PANEL_STATE_ISREADY = 2;
    public static int PANEL_STATE_START_SIGN_FIRED = 3;
    public static int PANEL_STATE_INGAME = 4;

    private int panelState = PANEL_STATE_CLOSED;       // 현재 패널 상태

    private int x;
    private int y = -15;
    private int level = 1;

    private Random r = new Random();
    // 글자 랜덤 자리선정 시 사용

    Font font = new Font("아리따-돋움4.0(OTF)-SemiBold", Font.PLAIN, 22);

    // 애니메이션 쓰레드 관련 변수
    private boolean onAir = false;
    private AniThread at;
    private DrawWord word;
    private int deltaY;         //난이도에 따라 지속적으로 증가하는 값
    private Color c = new Color(245,245,255);


    // 스레드 종료용
    private int wordCnt;
    private Client client;

    // 생성자
    // 각 서버의 클라이언트 정보를 받아옴
    // 받아온 후 오픈 상태로 표시
    public ClientPanel(Client client) {
        this.client = client;
        panelState = PANEL_STATE_OPEN;

        // 테두리 작성
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
    }


    // 리스트에 단어 등록
    public void setDrawList(ArrayList<DrawWord> drawList) {
        this.drawList = drawList;
        wordList = new ArrayList<String>();

        String str = "";

        //
        for(int i=0 ; i < this.drawList.size() ; i++) {
            str = this.drawList.get(i).getText();
            wordList.add(str);
        }

        if(!wordList.isEmpty()){
//            panelState = PANEL_STATE_START_SIGN_FIRED;
            System.out.println("panelState: " + panelState);
        }
        System.out.println("drawList 길이: " + this.drawList.size());
    }

    // 애니메이션 시작 flag
    public void onOffThread() {
        if(!onAir){
            onAir = true;
            at = new AniThread();

            System.out.print("게임 시작");
            at.start();
        }else {
            onAir = !onAir;
            System.out.println("게임 종료");
        }
    }

    // 단어 매치
    // drawList 내부의 단어들을 확인해보고 맞는게 있다면 지움
    // 지속적으로 돌리고 리스트가 비워지면 다음단계로 진행
    public void matchWord(String s) {
        for(int i=0 ; i < drawList.size() ; i++){
            if(s.equals(drawList.get(i).getText())){
                drawList.remove(i);
                System.out.println(s + "입력!");
            }
            checkEmptyList();
            // 추후 기능 추가 구현
        }
    }

    // 바닥에 닿을 경우 라이프 감소
    // 패널 하단으로 떨어질 경우 해당 단어를 삭제하고 라이프 감소
    public void wordTouchedHeight() {
        for(int i=0; i < drawList.size() ; i++) {
            if(drawList.get(i).getY() >= getHeight()){
                System.out.println(drawList.get(i).getText() + "땅에 닿음");
                drawList.remove(i);
            }
            checkEmptyList();
            // 추후 기능 추가 구현
        }
    }

    // 리스트 비어있는지 확인
    public boolean checkEmptyList() {
        if(drawList.isEmpty()){
            System.out.println("리스트 비어있음");
            return false;
        }else {
            return true;
        }
    }


    // 단어 움직이기
    public void drawWords() {
        for(int i= 0; i < drawList.size() ; i++) {
            DrawWord temp = drawList.get(i);
            temp.yMover();
            drawList.set(i, temp);
        }
    }


    // ========== 페인트 관련 ============
    void setGraphicsSetting(Graphics g) {
        g.setColor(c);
        g.fillRect(0,0,getWidth(),getHeight());
    }

    @Override
    public void paint(Graphics g){
        setGraphicsSetting(g);

        g.setFont(font);
        g.setColor(new Color(200, 170, 220));
        if(drawList != null){
            for(int i=0 ; i < drawList.size() ; i++){
                String text = drawList.get(i).getText();
                int x = drawList.get(i).getX();
                int y = drawList.get(i).getY();
                g.drawString(text, x, y);
            }
        }
    }

    //호출 시에만 그림이 그려짐
    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public int getLevel() {
        return level;
    }
    public void setLevel() {
        this.level = level;
    }
    public ArrayList<String> getWordList() {
        return wordList;
    }

    public int getPanelState() {
        return panelState;
    }

    public void setPanelState(int panelState) {
        this.panelState = panelState;
    }


    // 내부 애니메이션 스레드
    class AniThread extends Thread {
        int dx;
        int dy = 20;

        public AniThread() {}

        // 게임 시작될 시 돌아가는 메소드
        public void run() {
            while(onAir){
                // 시작 flag는 서버에서 보내줘야 함
                if(panelState == PANEL_STATE_START_SIGN_FIRED){
                    // 단어 움직이기
                    drawWords();

                    // 바닥에 닿았는지 확인
                    wordTouchedHeight();

                    // 재배치
                    repaint();

                    try{
                        sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                } // if end
            } // while end
        } // run() end
    } // class Thread end
}
