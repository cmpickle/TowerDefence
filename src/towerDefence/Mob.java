package towerDefence;

import java.awt.*;

@SuppressWarnings("serial")
public class Mob extends Rectangle {
	/* Sets information about the mobs */
	public int xC, yC; // x and y coorinates for the mob
	public int health = 52; // health of the mob
	public int healthSpace = 3; // distance of health bar from mob
	public int healthHeight = 6; // height of health bar
	public int mobSize = 52; // size of the mob
	public int mobWalk = 0;
	public int mobID = Value.mobAir;

	/* Directions for the mobs */
	public int upward = 0, downward = 1, right = 2, left = 3;
	public int direction = right;

	/* bools to test mob direction for movement */
	public boolean inGame = false;
	public boolean deleted = false;
	public boolean isDead = false;
	public boolean hasUpward = false;
	public boolean hasDownward = false;
	public boolean hasLeft = false;
	public boolean hasRight = false;

	public Mob()
	{

	}

	public void spawnMob(int mobID)
	{
		for (int y = 0; y < Screen.room.block.length; y++)
		{
			if (Screen.room.block[y][0].groundID == Value.groundRoad)
			{
				setBounds(Screen.room.block[y][0].x, Screen.room.block[y][0].y,
						mobSize, mobSize);
				xC = 0;
				yC = y;
			}
		}

		this.mobID = mobID;

		inGame = true;
	}

	public void deleteMob()
	{
		inGame = false;
		direction = -1;
		// health = 52;
		mobWalk = 0;
		deleted = true;
		Screen.room.block[0][0].getMoney(mobID);
	}

	public boolean deleted()
	{
		if (deleted)
		{
			return true;
		} else
		{
			return false;
		}
	}

	public void loseMyHealth()
	{
		Screen.health -= 1;
	}

	public int walkFrame = 0, walkSpeed = 15;

	public void physic()
	{ // controls the physics of the mobs
		if (walkFrame >= walkSpeed)
		{
			if (direction == right)
			{
				x += 1;
			} else if (direction == left)
			{
				x -= 1;
			} else if (direction == upward)
			{
				y -= 1;
			} else if (direction == downward)
			{
				y += 1;
			}

			mobWalk += 1;

			if (mobWalk == Screen.room.blockSize)
			{
				if (direction == right)
				{
					xC += 1;
					hasRight = true;
				} else if (direction == left)
				{
					xC -= 1;
					hasLeft = true;
				} else if (direction == upward)
				{
					yC -= 1;
					hasUpward = true;
				} else if (direction == downward)
				{
					yC += 1;
					hasDownward = true;
				}

				if (!hasUpward)
				{
					try
					{
						if (Screen.room.block[yC + 1][xC].groundID == Value.groundRoad)
						{
							direction = downward;
						}
					} catch (Exception e)
					{
					}
				}

				if (!hasDownward)
				{
					try
					{
						if (Screen.room.block[yC - 1][xC].groundID == Value.groundRoad)
						{
							direction = upward;
						}
					} catch (Exception e)
					{
					}
				}

				if (!hasLeft)
				{
					try
					{
						if (Screen.room.block[yC][xC + 1].groundID == Value.groundRoad)
						{
							direction = right;
						}
					} catch (Exception e)
					{
					}
				}

				if (!hasRight)
				{
					try
					{
						if (Screen.room.block[yC][xC - 1].groundID == Value.groundRoad)
						{
							direction = left;
						}
					} catch (Exception e)
					{
					}
				}

				if (Screen.room.block[yC][xC].airID == Value.airCave)
				{
					deleteMob();
					loseMyHealth();
				}

				hasUpward = false;
				hasDownward = false;
				hasLeft = false;
				hasRight = false;
				mobWalk = 0;
			}

			walkFrame = 0;
		} else
		{
			walkFrame += 1;
		}
	}

	public void loseHealth(int amount)
	{
		health -= amount;

		checkDeath();
	}

	public void checkDeath()
	{
		if (health == 0)
		{
			deleteMob();
		}
	}

	public boolean isDead()
	{
		if (inGame)
		{
			return false;
		} else
		{
			return true;

		}
	}

	public void draw(Graphics g)
	{
		g.drawImage(Screen.tileset_mob[mobID], x, y, width, height, null);

		/* health bar */
		g.setColor(new Color(180, 50, 50));
		g.fillRect(x, y - (healthSpace + healthHeight), width, healthHeight); // health bar
		g.setColor(new Color(50, 180, 50));
		g.fillRect(x, y - (healthSpace + healthHeight), health, healthHeight); // health bar

		g.setColor(new Color(0, 0, 0));
		g.drawRect(x, y - (healthSpace + healthHeight), health - 1, healthHeight - 1); // outline for health bar
	}
}
