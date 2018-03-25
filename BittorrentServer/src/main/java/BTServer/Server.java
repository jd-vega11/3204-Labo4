package BTServer;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;

import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;

public class Server 
{
	public static void main(String[] args) throws Exception
	{
		if(args[0] == null || args[0] == "")
		{
			throw new Exception("[INPUT ERROR] Debe introducir por parametro la ruta de la carpeta donde tiene almacenados los archivos .torrent");					
		}
		// First, instantiate a Tracker object with the port you want it to listen on.
		// The default tracker port recommended by the BitTorrent protocol is 6969.		
		try {
			Tracker tracker = new Tracker(new InetSocketAddress(6969));
		

		// Then, for each torrent you wish to announce on this tracker, simply created
		// a TrackedTorrent object and pass it to the tracker.announce() method:
		FilenameFilter filter = new FilenameFilter() {
	
		  public boolean accept(File dir, String name) {
		    return name.endsWith(".torrent");
		  }
		};

		for (File f : new File(args[0]).listFiles(filter)) {
		  tracker.announce(TrackedTorrent.load(f));
		}
		

		// Once done, you just have to start the tracker's main operation loop:
		tracker.start();
		
		// You can stop the tracker when you're done with:
		//tracker.stop();	
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("[SERVER ERROR] " + e.getMessage());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("[SERVER ERROR] " + e.getMessage());
		}
	}	

}
