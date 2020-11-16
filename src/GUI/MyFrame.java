package GUI;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;

public class MyFrame extends JFrame {
	
    public MyFrame(String name){
    	setTitle(name);
    	setSize(800, 596);
    	setLocationRelativeTo(null);		// GUI Ã¢À» È­¸é Áß¾Ó¿¡ ¶ç¿ò
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setLayout(new CardLayout());
    	
    	
    	
    	setVisible(true);
    }
}
