package towerDefence;

import java.awt.*;

import javax.swing.*;

import java.awt.image.*;
import java.io.*;

@SuppressWarnings("serial")
public class Screen extends JPanel implements Runnable {
    public Thread thread = new Thread(this);

    /* Used to import tilesets for the game */
    public static Image[] tileset_ground = new Image[100];
    public static Image[] tileset_air = new Image[100];
    public static Image[] tileset_res = new Image[100];
    public static Image[] tileset_mob = new Image[100];

    public static int myWidth, myHeight;
    public static int coinage = 10, health = 100;
    public static int killed = 0, killsToWin = 20, level = 1, maxLevel = 3;
    public static int winFrame = 0, winTime = 4000;

    private boolean isFirst = true;
    public static boolean isDebug = true;
    private static boolean isWin = false;

    public static Point mse = new Point(0, 0);
    public static Room room;
    public static Save save;
    public static Store store;
    public static GameOver over;
    public static Win win;

    public static Mob[] mobs = new Mob[20];

    public Screen(Frame frame)
    {
	frame.addMouseListener(new KeyHandle()); // Listener for mouse buttons
	frame.addMouseMotionListener(new KeyHandle()); // Listener for motion of the mouse

	thread.start();
    }

    public static void hasWon()
    {
	if (killed == killsToWin)
	{
	    isWin = true;
	    killed = 0;
	    level += 1;
	    coinage = 10; // if you don't want money to carry over
	}
    }

    public void define()
    {
	room = new Room();
	save = new Save();
	store = new Store();
	over = new GameOver();
	win = new Win();

	for (int i = 0; i < tileset_ground.length; i++)
	{
	    tileset_ground[i] = new ImageIcon("res/tileset_ground.png")
		    .getImage();
	    tileset_ground[i] = createImage(new FilteredImageSource(
		    tileset_ground[i].getSource(), new CropImageFilter(0,
			    26 * i, 26, 26)));
	}
	for (int i = 0; i < tileset_air.length; i++)
	{
	    tileset_air[i] = new ImageIcon("res/tileset_air.png").getImage();
	    tileset_air[i] = createImage(new FilteredImageSource(
		    tileset_air[i].getSource(), new CropImageFilter(0, 26 * i,
			    26, 26)));
	}
	/* used to import tileset resources for the store */
	tileset_res[0] = new ImageIcon("res/cell.png").getImage(); // importing the cell res
	tileset_res[1] = new ImageIcon("res/heart.png").getImage();// importing the heart res
	tileset_res[2] = new ImageIcon("res/coin.png").getImage(); // importing the coin res

	tileset_mob[0] = new ImageIcon("res/mob1.png").getImage(); // importing the mobs

	save.loadSave(new File("Save/mission" + level + ".td")); // load mission 1

	for (int i = 0; i < mobs.length; i++)
	{
	    mobs[i] = new Mob(); // calls the Mob constructor to create a mob
	}
    }

    public void paintComponent(Graphics g)
    {
	if (isFirst && health > 0)
	{
	    myWidth = getWidth();
	    myHeight = getHeight();
	    define();

	    isFirst = false;
	}

	g.setColor(new Color(70, 70, 70)); // background color for the app
	g.fillRect(0, 0, getWidth(), getHeight());
	g.setColor(new Color(0, 0, 0)); // creates new color black
	g.drawLine(room.block[0][0].x - 1, 0, room.block[0][0].x - 1,
		room.block[room.worldHeight - 1][0].y + room.blockSize); // Drawing the left line
	g.drawLine(room.block[0][room.worldWidth - 1].x + room.blockSize, 0,
		room.block[0][room.worldWidth - 1].x + room.blockSize,
		room.block[room.worldHeight - 1][0].y + room.blockSize); // Drawing the right line
	g.drawLine(room.block[0][0].x, room.block[room.worldHeight - 1][0].y
		+ room.blockSize+1, room.block[0][room.worldWidth - 1].x
		+ room.blockSize, room.block[room.worldHeight - 1][0].y
		+ room.blockSize+1); // Drawing the bottom line
	@SuppressWarnings("unused")
	int temp = room.block[0][0].x;
	@SuppressWarnings("unused")
	int temp2 = room.block[0][0].y;
	@SuppressWarnings("unused")
	int temp3 = room.worldWidth*room.blockSize;
	@SuppressWarnings("unused")
	int temp4 = room.block[0][0].y;
	g.drawLine(room.block[0][0].x, room.block[0][0].y, room.worldWidth*room.blockSize, room.block[0][0].y); //Drawing the top line

	room.draw(g); // Drawing the room.

	for (int i = 0; i < mobs.length; i++)
	{
	    if (mobs[i].inGame)
	    {
		mobs[i].draw(g);
	    }
	}
	
	room.drawFight(g);

	store.draw(g); // Drawing the store.

	if (health < 1)
	{
	    over.draw(g);
	}

	if (isWin)
	{
	    // if(level == maxLevel) {
	    // win.draw(g);
	    // } else {
	    // win.draw(g);
	    g.setColor(new Color(20, 180, 20)); // creates new color red
	    g.fillRect(0, 0, Screen.myWidth, Screen.myHeight); // makes a background using mywidth
							       // and myheight to set it
							       // apporopriately
	    g.setColor(new Color(255, 255, 255)); // creates new color white
	    g.setFont(new Font("Courier New", Font.BOLD, 14));// creates font for game over
	    g.drawString("You Win!", 300, 275); // displays the text game over at position 300, 275
	    g.setColor(new Color(100, 100, 100));
	    // }
	}

	/*
	 * for(int i=0; i<mobs.length;i++) { if(mobs[i].deleted && health > 0) { winner = true; }
	 * else { winner = false; } } if(winner) { win.draw(g); }
	 */
    }

    public int spawnTime = 5000, spawnFrame = 0; // spawnTime = Rate of spawn for mobs //spawnFrame
						 // = wait until 1st spawn

    public void mobSpawner()
    { // mob spawner to spawn the mobs
	if (spawnFrame >= spawnTime)
	{
	    for (int i = 0; i < mobs.length; i++)
	    {
		if (!mobs[i].inGame)
		{ // if the mob is not in the game
		    if (!mobs[i].deleted())
		    { // keeps the mob from respawining
		      // if deleted`
			mobs[i].spawnMob(Value.mobBob); // mob will be spawned
			break;
		    }
		}
	    }
	    spawnFrame = 0;
	} else
	{
	    spawnFrame += 1;
	}
    }

    public void run()
    {
	while (true)
	{
	    if (!isFirst && health > 0 && !isWin)
	    {
		room.physic();
		mobSpawner();
		for (int i = 0; i < mobs.length; i++)
		{
		    if (mobs[i].inGame)
		    { // checks if the mob is in the game
			mobs[i].physic(); // applies physics to the mob
		    }
		}
	    } else
	    {
		if (isWin)
		{
		    if (winFrame >= winTime)
		    {
			isWin = false;
			define();

			winFrame = 0;
		    } else
		    {
			winFrame += 1;
		    }
		}
	    }

	    repaint();

	    try
	    {
		Thread.sleep(1);
	    } catch (InterruptedException e)
	    {
		e.printStackTrace();
	    }
	}
    }
}
