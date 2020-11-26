package GUI;

import client.Client;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClosingPopUp extends WindowAdapter {
    private Client c;
    private MyFrame f;

    public ClosingPopUp(Client client) {
        this.c = client;
        this.f = client.f;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int opNum = JOptionPane.showConfirmDialog(f, "정말로 게임을 나가시겠습니까?",
                "Quit", JOptionPane.OK_CANCEL_OPTION);
        if(opNum == JOptionPane.OK_OPTION){
            c.exitClient();
        }
    }
}
