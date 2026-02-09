package csc2b.server;

public class Server
{
    public static void main(String[] argv)
    {
	//Setup server socket and pass on handling of request
    	
    	BUKACLASS mybuka = new BUKACLASS(40);
    	
    	mybuka.Servestart();
    }
}
