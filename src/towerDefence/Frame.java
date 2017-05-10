package towerDefence;

import java.awt.*;

import javax.swing.*;

public class Frame extends JFrame {
    /**
     * @author Cameron Pickle
     */
    private static final long serialVersionUID = 1L;
    public static String title = "Tower Bacon Alpha";
    public static Dimension size = new Dimension(700, 550);

    public static void main(String args[])
    {
	@SuppressWarnings("unused")
	Frame frame = new Frame();
    }

    public Frame()
    {
	setTitle(title);
	setSize(size);
	setResizable(false);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	init();
    }

    public void init()
    {
	setLayout(new GridLayout(1, 1, 0, 0));

	Screen screen = new Screen(this);
	add(screen);

	setVisible(true);
    }
}
