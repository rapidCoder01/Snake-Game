package com.GameSnake;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener{

	private final int dot_size=10;
	
	private final int All_dots= 900;
	private final int x[]= new int[All_dots];
	private final int y[]= new int[All_dots];
	
	private int dots;
	private  final int random_position=20;
	private int apple_x;
	private int apple_y;
	
	private Image apple;
	private Image dot;
	private Image head;
		
	private Timer timer;
	
	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	
	private boolean inGame = true;
	
	private JButton b;

	

	public Board()
	{
		addKeyListener(new TAdapter());
		setPreferredSize(new Dimension(300, 300));
		setBackground(Color.BLACK);
		setFocusable(true);
		
		loadImages();
		Working();
	}
	
	public void loadImages() {
		ImageIcon i1 =new ImageIcon(ClassLoader.getSystemResource("com/GameSnake/icons/apple.png"));
		apple = i1.getImage();
		ImageIcon i2 =new ImageIcon(ClassLoader.getSystemResource("com/GameSnake/icons/dot.png"));
		dot = i2.getImage();
		ImageIcon i3 =new ImageIcon(ClassLoader.getSystemResource("com/GameSnake/icons/head.png"));
		head = i3.getImage();
	}
	public void Working()
	{	
		dots=3;
		
		for(int z=0; z < dots; z++)
		{
			x[z] = 50 - z*dot_size;
			y[z] = 50;
		}
		
		locateApple();
		
		timer = new Timer(140, this);
		timer.start();
	}
	
	public void locateApple()
	{
		int rd = (int) (Math.random() * random_position);// 0 and 1 => 0.6*20 = 12*10 = 120
		apple_x = (rd * dot_size);
		
		rd = (int) (Math.random() * random_position);// 0 and 1 => 0.6*20 = 12*10 = 120
		apple_y = (rd * dot_size);
	}
	
	public void hitApple() {
		
		if((x[0] == apple_x) && (y[0] == apple_y)){
			dots++;
			locateApple();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);  
	}
	
	public void draw(Graphics g) {
		if(inGame) {
			g.drawImage(apple, apple_x, apple_y, this);
			
			for(int z = 0; z < dots; z++) {
				if(z == 0) {
					
					g.drawImage(head, x[z], y[z], this);
				}
				else {
					g.drawImage(dot, x[z], y[z], this);
				}
			}
			
			//Toolkit.getDefaultToolkit().sync();
		}
		else {
			gameOver(g);
			//restart();
		}
	}
	
	/*public void restart() {
		
		JButton b=new JButton("Restart");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent re) {
				if(re.getSource() == b) {
					if(! inGame)
					{
						new Snake_Game();
					}
					
				}
				repaint();
				}
			});
		b.setFont(new Font("Tahoma", Font.BOLD, 15));
		b.setBounds(150, 100, 15, 15);
		}*/

	
	public void gameOver(Graphics g) {
		String msg = "Game Over";
		Font font = new Font("SAN_SERIF",Font.BOLD, 16);
		FontMetrics metrices = getFontMetrics(font);
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(msg, (300 - metrices.stringWidth(msg)) / 2 ,300/2);
	}
	
	
	public void checkCollision() {
		for(int z = dots; z > 0; z--) 
		{
			if((z > 4) && (x[0] == x[z]) && (y[0] == y[z]))
			{
				inGame = false;
			}
		}
		if(y[0] >= 300) {
			inGame = false;
		}
		if(x[0] >= 300) {
			inGame = false;
		}
		if(y[0] < 0) {
			inGame = false;
		}
		if(x[0] < 0) {
			inGame = false;
		}
		if(!inGame)
		{
			timer.stop();
		}
	}
	
	public void move()
	{
		for(int z=dots; z > 0; z--)
		{
			x[z] = x[z - 1];
			y[z] = y[z - 1];
		}
		if(leftDirection)
		{
			x[0] = x[0] - dot_size;// lets x=240 -10 = 230
			
		}
		if(rightDirection)
		{
			x[0] += dot_size;// lets x=240 +10 = 230
			
		}
		if(upDirection)
		{
			y[0] -= dot_size;
			
		}
		if(downDirection)
		{
			y[0] += dot_size;
			
		}
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		if(inGame) {
			hitApple();
			checkCollision();
			move();
		}
		repaint();
	}
	
	private class TAdapter extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent ke) {
			int key = ke.getKeyCode();
			
			if(key == KeyEvent.VK_LEFT && (!rightDirection)) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}
			if(key == KeyEvent.VK_RIGHT && (!leftDirection)) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}
			if(key == KeyEvent.VK_UP && (!downDirection)) {
				upDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
			if(key == KeyEvent.VK_DOWN && (!upDirection)) {
				downDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
		}
		
	}
}
