import java.awt.*;

public class Win {
	public Rectangle button = new Rectangle(292, 300, 100, 30);
	public boolean restart = false;
	
	public void gameOver() {
		
	}
	
	public void draw(Graphics g) {
		g.setColor(new Color(20, 180, 20)); //creates new color red
		g.fillRect(0, 0, Screen.myWidth, Screen.myHeight); //makes a background using mywidth and myheight to set it apporopriately 
		g.setColor(new Color(255, 255, 255)); //creates new color white
		g.setFont(new Font("Courier New", Font.BOLD, 14));// creates font for game over
		g.drawString("You Win!", 300, 275); // displays the text game over at position 300, 275
		g.setColor(new Color(100, 100, 100));
		
		if(button.contains(Screen.mse)) {
			g.setColor(new Color(255, 255, 255, 100));
			g.fillRect(button.x, button.y, button.width, button.height);
		}
		
		g.fillRect(292, 300, 100, 30); // creates rectangle at position 300, 300 with width 85 height 30
		g.setColor(new Color(0, 0, 0));
		g.drawString("Play Again?", 300, 320);
		}
}
