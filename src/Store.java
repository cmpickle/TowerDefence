import java.awt.*;

public class Store {
	private int shopWidth = 8;
	private int buttonSize = 52; // size of the buttons
	private int cellSpace = 2; //space between buttons
	private int awayFromRoom = 40; //distance from room(Game window)
	private int iconSize = 20;
	private int iconSpace = 3;
	private int iconTextY = 5;
	private int itemIn = 4;
	private int heldID = -1;
	private int realID = -1;
	private int[] buttonID = {Value.airTowerLazer, Value.airAir, Value.airAir, Value.airAir, Value.airAir, Value.airAir, Value.airAir, Value.airTrashCan};
	private int[] buttonPrice = {10, 0, 0, 0, 0, 0, 0, 0};
	
	private Rectangle[] button = new Rectangle[shopWidth];
	private Rectangle buttonHealth;
	private Rectangle buttonCoins;
	
	private boolean holdsItem = false;
	
	public Store() {
		define();
	}
	
	public void click(int mouseButton) {
		if(mouseButton == 1) {
			for(int i=0;i<button.length;i++) {
				if(button[i].contains(Screen.mse)) {
					if(buttonID[i] != Value.airAir) {
						if(buttonID[i] == Value.airTrashCan) { //Delete item (ID)
							holdsItem = false;
						} else {
							heldID = buttonID[i];
							realID = i;
							holdsItem = true;
						}
					}
				}
			}
			
			if(holdsItem) {
				if(Screen.coinage >= buttonPrice[realID]) {
					for(int y=0; y<Screen.room.block.length; y++) {
						for(int x=0; x<Screen.room.block[0].length; x++) {
							if(Screen.room.block[y][x].contains(Screen.mse)) {
								if(Screen.room.block[y][x].groundID != Value.groundRoad && Screen.room.block[y][x].airID == Value.airAir) {
									Screen.room.block[y][x].airID = heldID;
									Screen.coinage -= buttonPrice[realID];
								}
							}
						}	
					}
				}
			}
		}
	}
	
	public void define() {
		for(int i=0; i<button.length; i++) {
			button[i] = new Rectangle((Screen.myWidth/2) - ((shopWidth * (buttonSize + cellSpace))/2) + ((buttonSize + cellSpace)*i), (Screen.room.block[Screen.room.worldHeight-1] [0].y) + Screen.room.blockSize + awayFromRoom, buttonSize, buttonSize);
		}
		
		buttonHealth = new Rectangle(Screen.room.block[0][0].x -1, button[0].y, iconSize, iconSize);
		buttonCoins = new Rectangle(Screen.room.block[0][0].x -1, button[0].y + button[0].height-iconSize, iconSize, iconSize);
	}
	
	public void draw(Graphics g) {		
		for(int i=0; i<button.length; i++) {
			if(button[i].contains(Screen.mse)) {
				g.setColor(new Color(255, 255, 255, 100));
				g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
			}
			
			g.drawImage(Screen.tileset_res[0], button[i].x, button[i].y, button[i].width, button[i].height, null);	
			if(buttonID[i] != Value.airAir) {
				g.drawImage(Screen.tileset_air[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn, button[i].width - (itemIn*2), button[i].height - (itemIn*2), null);
			}
			if(buttonPrice[i] > 0) {
				g.setColor(new Color(255, 255,255));
				g.setFont(new Font ("Courier New", Font.BOLD, 14));
				g.drawString("$" + buttonPrice[i], button[i].x + 2, button[i].y + 12); //put the amount for each shop item and precede by $
			}
		}
		
		g.drawImage(Screen.tileset_res[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null); //draw the health image
		g.drawImage(Screen.tileset_res[2], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height, null); //draw the coin image
		g.setFont(new Font("Courier New", Font.BOLD, 14));
		g.setColor(new Color(255, 255,255));
		g.drawString("" +Screen.health, buttonHealth.x + buttonHealth.width + iconSpace, buttonHealth.y + buttonHealth.width - iconTextY); //string for current health
		g.drawString("" +Screen.coinage, buttonCoins.x + buttonCoins.width + iconSpace, buttonCoins.y + buttonCoins.width - iconTextY); //string for current coins
		
		if (holdsItem) {
			g.drawImage(Screen.tileset_air[heldID], Screen.mse.x - ((button[0].width - (itemIn*2))/2) + itemIn, Screen.mse.y - ((button[0].width - (itemIn*2))/2) + itemIn, button[0].width - (itemIn*2), button[0].height - (itemIn*2), null); //hold an item from the shop on the mouse
		}
	}
}
