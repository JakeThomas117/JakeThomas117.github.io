import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

import java.io.IOException;

import java.util.ArrayList;

import javax.swing.JFrame;


public class Snake {

	//ENGINE VARIABLES
	private static KeyBoardListener KL;
	private static JFrame Frame;
	private static Canvas Canv;
	private static Graphics GR;

	//GAME WORLD VARIABLES
	private static int width, height, scaleX, scaleY;
	private static boolean gameOver;

	//Snake VARIABLES
	private static int posX, posY, dir;
	private static ArrayList<int[]> Tail;
	private static int[] prev;

	//FRUIT VARIABLES
	private static int fposX, fposY;

	private static void setup() {
		//Game World Variable Initialization
		gameOver = false;
		width = 800;
		height = 800;
		scaleX = width / 32;
		scaleY = height / 32;

		//Engine Variable Initialization and JFrame Setup
		Frame = new JFrame("Snake");
		Frame.setLocationRelativeTo(null);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Canv = new Canvas();
		Canv.setSize(width, height);
		Frame.add(Canv);
		Frame.pack();
		Frame.setResizable(false);

		KL = new KeyBoardListener();
		Frame.addKeyListener(KL);

		GR = Canv.getGraphics();

		Frame.setVisible(true);

		//Snake and Fruit Variable Initialization
		Tail = new ArrayList<int[]>();
		prev = new int[3];

		posX = width / 2;
		posY = height / 2;
		dir = 0;

		randFruitPos();
	}

	private static void reset() {
		gameOver = false;
		posX = width / 2;
		posY = height / 2;
		dir = 0;
		randFruitPos();

		Tail.clear();

	}

	private static void input() {
		Frame.requestFocus();

		if (KL.up() && dir != 2) {
			dir = 0;
		}
		if (KL.right() && dir != 3) {
			dir = 1;
		}
		if (KL.down() && dir != 0) {
			dir = 2;
		}
		if (KL.left() && dir != 1) {
			dir = 3;
		}
	}

	private static void logic() {
		//Shifting Tail Positions
		if (Tail.size() > 0) {
			for (int i = Tail.size() - 1; i > 0; i--) {
				Tail.get(i)[0] = Tail.get(i - 1)[0];
				Tail.get(i)[1] = Tail.get(i - 1)[1];
			}
			Tail.get(0)[0] = posX;
			Tail.get(0)[1] = posY;
		}

		//Moving Snake Head
		switch (dir) {
			case 0:
				posY -= scaleY;
				break;
			case 1:
				posX += scaleX;
				break;
			case 2:
				posY += scaleY;
				break;
			case 3:
				posX -= scaleX;
				break;
		}

		//GameOver State Checking
		for (int i = 0; i < Tail.size(); i++) {
			if (posX == Tail.get(i)[0] && posY == Tail.get(i)[1]) {
				gameOver = true;
			}
		}

		if (posX < 0 || posX > width - 1 || posY < 0 || posY > height - 1) {
			gameOver = true;
		}

		//Fruit Checking, Fruit Randomization, and Elongating the Snake
		if (posX == fposX && posY == fposY) {
			int[] adding = {prev[0], prev[1]};
			Tail.add(adding);
			randFruitPos();
		}

		//Previous position calculation
		if (Tail.size() > 0) {
			prev[0] = Tail.get(Tail.size() - 1)[0];
			prev[1] = Tail.get(Tail.size() - 1)[1];
		} else {
			prev[0] = posX;
			prev[1] = posY;
		}
	}

	//Randomizes new fruit position in world
	//Still doesn't work properly with certain dimentions
	private static void randFruitPos() {
		fposX = ((int)(Math.random() * width / scaleX) * scaleX);
		fposY = ((int)(Math.random() * height / scaleY) * scaleY);
	}

	//Renders each object into the frame
	private static void draw() {
		//background
		GR.setColor(Color.black);
		GR.fillRect(0, 0, width, height);

		//Snake head
		GR.setColor(Color.white);
		GR.fillRect(posX + 1, posY + 1, scaleX - 2, scaleY - 2);

		//Snake Tail
		for (int i = 0; i < Tail.size(); i++) {
			GR.fillRect(Tail.get(i)[0] + 1, Tail.get(i)[1] + 1, scaleX - 2, scaleY - 2);
		}

		//fruit
		GR.setColor(Color.red);
		GR.fillOval(fposX, fposY, scaleX, scaleY);
	}

	//GameOver State
	private static void gameOver() {
		GR.setColor(Color.red);
		GR.fillRect(0, 0, width, height);

		GR.setColor(Color.black);
		GR.setFont(Font.decode("Arial-BOLD-32"));
		GR.drawString("GAMEOVER", (width / 2) - 96, (height / 2) - 16);

		GR.setFont(Font.decode("Arial-BOLD-16"));
		GR.drawString("r to restart", (width / 2) - 64, (height / 2) + 32);
	}

	public static void main(String[] args) {
		setup();

		int then = (int) System.currentTimeMillis();
		int now;
		while (true) {
			while(!gameOver) {
				now = (int) System.currentTimeMillis();
				input();

				if(now - then > 150) {
					logic();
					draw();
					then = (int) System.currentTimeMillis();
				}
			}
			gameOver();
			while (!KL.r()) {
				System.out.println("GameOver - awaiting restart...");
			}
			reset();
		}
	}
}
