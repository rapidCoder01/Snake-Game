package com.GameSnake;

import java.awt.Color;

import javax.swing.*;


public class Snake_Game extends JFrame{

	public Snake_Game()
	{
		super("Snake Game");
		add(new Board());
		pack();
		
		setLocationRelativeTo(null);
		setResizable(false);
		//JButton b=new JButton("Restart");
		
	}
	public static void main(String[] args)
	{
		new Snake_Game().setVisible(true);
		
	}
}
