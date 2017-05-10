package towerDefence;

import java.awt.event.*;
import java.awt.*;

public class KeyHandle implements MouseMotionListener, MouseListener {

    public int mouseClicked()
    {
	return 0;
	// TODO Auto-generated method stub

    }

    public void mouseEntered(MouseEvent e)
    {
	// TODO Auto-generated method stub

    }

    public void mouseExited(MouseEvent e)
    {
	// TODO Auto-generated method stub

    }

    public void mousePressed(MouseEvent e)
    {
	Screen.store.click(e.getButton());
    }

    public void mouseReleased(MouseEvent e)
    {
	// TODO Auto-generated method stub

    }

    public void mouseDragged(MouseEvent e)
    {
	Screen.mse = new Point((e.getX())
		- ((Frame.size.width - Screen.myWidth) / 2), (e.getY())
		- ((Frame.size.height - Screen.myHeight) / 2));

    }

    public void mouseMoved(MouseEvent e)
    {
	Screen.mse = new Point((e.getX())
		- ((Frame.size.width - Screen.myWidth) / 2), (e.getY())
		- ((Frame.size.height - Screen.myHeight) / 2));
    }

    public void mouseClicked(MouseEvent e)
    {
	// TODO Auto-generated method stub

    }

}
