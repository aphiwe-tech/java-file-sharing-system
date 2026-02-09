package csc2b.server;

import java.io.IOException;
import java.net.ServerSocket;

public class BUKACLASS {
	
	
	private ServerSocket ss;
	private boolean running = false;
	
	
	public BUKACLASS(int port)
	{
		try {
			ss = new ServerSocket(port);
			
			System.out.println("server ready to accept connections");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 running = true;
	}
	
	public void Servestart()
	{
		
		while(running)
		{
			
			try {
				Thread thr = new Thread(new BUKAHandler(ss.accept()));
				thr.start();
				
				System.out.println("connection Established on a server");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//dd
			
		}
		
	}
 
  
}
