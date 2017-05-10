import java.awt.*;

public class GameOver {
	private Rectangle button = new Rectangle(292, 300, 100, 30);
	private boolean restart = false;
	private Screen screen;
	
	KeyHandle h = new KeyHandle();
	
	public void gameOver() {
		this.screen = new Screen(null);		
	}
	
	public void click(int mouseButton) {
		if(mouseButton == 1) {
			if(button.contains(Screen.mse)) {
				restart = true;
				Frame.main(null);
			}
		}
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(240, 20, 20)); //creates new color red
		g.fillRect(0, 0, Screen.myWidth, Screen.myHeight); //makes a background using mywidth and myheight to set it apporopriately 
		g.setColor(new Color(255, 255, 255)); //creates new color white
		g.setFont(new Font("Courier New", Font.BOLD, 14));// creates font for game over
		g.drawString("Game Over!", 300, 275); // displays the text game over at position 300, 275
		g.setColor(new Color(100, 100, 100));
		
		if(button.contains(Screen.mse)) {
			g.setColor(new Color(255, 255, 255, 100));
			g.fillRect(button.x, button.y, button.width, button.height);
			//click(h.mousePressed(null));
		}
		
		g.fillRect(292, 300, 100, 30); // creates rectangle at position 300, 300 with width 85 height 30
		g.setColor(new Color(0, 0, 0));
		g.drawString("Play Again?", 300, 320);
		
		if(restart == true) {
			//g.draw(Screen.paintComponent(g));
		}
	}
}
	

