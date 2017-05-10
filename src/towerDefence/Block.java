package towerDefence;

import java.awt.*;

/* Rectangle states the x,y, and z of rectangle */
@SuppressWarnings("serial")
public class Block extends Rectangle {
	public Rectangle towerSquare;
	public int towerSquareSize = 130;
	public int groundID;
	public int airID;
	public int loseTime = 100, loseFrame = 0;
	public int shotMob = -1;

	public boolean shooting = false;

	public Block(int x, int y, int width, int height, int groundID, int airID)
	{
		setBounds(x, y, width, height);
		towerSquare = new Rectangle(x - (towerSquareSize / 2), y - (towerSquareSize / 2),
				width + (towerSquareSize), height + (towerSquareSize));
		this.groundID = groundID;
		this.airID = airID;
	}

	public void draw(Graphics g)
	{
		g.drawImage(Screen.tileset_ground[groundID], x, y+1, width, height, null);

		if (airID != Value.airAir)
		{
			g.drawImage(Screen.tileset_air[airID], x, y+1, width, height, null);
		}
	}

	public void physic()
	{
		if (shotMob != -1 && towerSquare.intersects(Screen.mobs[shotMob]))
		{ // if the mob intersects towerSquare
			shooting = true; // lets tower shoot mob
		} else
		{
			shooting = false;
		}
		if (!shooting)
		{
			shooting = false; // no shooting
			if (airID == Value.airTowerLazer)
			{ // if an airID has a Lazer Tower add other towers to this if statement
				for (int i = 0; i < Screen.mobs.length; i++)
				{ // do this for all mobs
					if (Screen.mobs[i].inGame)
					{ // mobs have to be in game
						if (towerSquare.intersects(Screen.mobs[i]))
						{ // if the mob intersects towerSquare
							shooting = true; // lets tower shoot mob
							shotMob = i;
						}
					}
				}
			}
		}
		if (shooting)
		{
			if (loseFrame >= loseTime)
			{
				Screen.mobs[shotMob].loseHealth(1);
				loseFrame = 0;
			} else
			{
				loseFrame += 1;
			}
			if (Screen.mobs[shotMob].isDead())
			{
				if(!Screen.mobs[shotMob].isDead)
					Screen.killed += 1;
				Screen.mobs[shotMob].isDead = true;

				shooting = false;
				shotMob = -1;

				Screen.hasWon();
			}
		}
	}

	public void getMoney(int mobID)
	{
		Screen.coinage += Value.deathReward[mobID];
	}

	public void fight(Graphics g)
	{
		if (Screen.isDebug)
		{
			if (airID == Value.airTowerLazer)
			{
				g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width,
						towerSquare.height);
			}
		}
		if (shooting)
		{
			g.setColor(new Color(255, 255, 0));
			g.drawLine(x + (width / 2), y + (height / 4), Screen.mobs[shotMob].x
					+ (Screen.mobs[shotMob].width / 2), Screen.mobs[shotMob].y
					+ (Screen.mobs[shotMob].height / 2));
		}

	}
}
