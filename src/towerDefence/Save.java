package towerDefence;

import java.io.*;
import java.util.*;

public class Save {
    public void loadSave(File loadPath)
    { // Takes file "loadPath"
	try
	{
	    Scanner loadScanner = new Scanner(loadPath); // can scan file - the loadPath - goes
							 // through mission1.td

	    while (loadScanner.hasNext())
	    {
		Screen.killsToWin = loadScanner.nextInt(); // the first number is the number of
							   // kills needed to win

		for (int y = 0; y < Screen.room.block.length; y++)
		{
		    for (int x = 0; x < Screen.room.block[0].length; x++)
		    {
			Screen.room.block[y][x].groundID = loadScanner
				.nextInt(); // puts tiles in the ground layer
		    }
		}
		for (int y = 0; y < Screen.room.block.length; y++)
		{
		    for (int x = 0; x < Screen.room.block[0].length; x++)
		    {
			Screen.room.block[y][x].airID = loadScanner.nextInt(); // puts tiles in the
									       // air layer
		    }
		}
	    }

	    loadScanner.close();
	} catch (Exception e)
	{
	}
    }
}
