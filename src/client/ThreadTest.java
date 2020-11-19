package client;

import GUI.ClientPanel;

import java.util.ArrayList;

public class ThreadTest extends Thread {
    private ArrayList<String> list;

    String[] words = {"무지개", "멱살", "줄넘기", "기지개",
    "게살버거", "새우버거" , "모니터", "프린터", "자바", "스프링"};

    private boolean onAir = false;
    private int level;

    Client client;
    ClientPanel cPanel;

    public void startGame(int level, Client client){
        this.client = client;
        this.level = level;

        list = new ArrayList<String>();

        for(int i=0 ; i<words.length ; i++) {
            list.add(words[i]);
        }

        onAir = true;
    }

    public void endGame(){
        onAir = false;
    }

    public void sendString(String s){
        client.printOnMyConsole(s);
    }

    public void run() {
        while(onAir) {
            for(int i=0 ; i<list.size() ; i++) {
                sendString(list.get(i));

                try{
                    Thread.sleep(1000 * level);
                }catch(InterruptedException e){
                    System.out.println("스레드의 Thread.sleep err: " + e);
                }
            }
        }

        onAir = false;
        System.out.println("done");
    }
}
