package csc2b.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class BUKAHandler implements Runnable
{
	private BufferedReader br;
	private PrintWriter pw;
	private DataOutputStream dos;
	private FileInputStream  fis;
	private Socket newConnectionToClient;
	
	
	
	
	
	
    public BUKAHandler(Socket s)
    {	
    	this.newConnectionToClient = s;
    	
	//Bind streams
    	try {
			br = new BufferedReader(new InputStreamReader(newConnectionToClient.getInputStream()));
			pw = new PrintWriter(newConnectionToClient.getOutputStream());
			dos = new DataOutputStream(newConnectionToClient.getOutputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public void run()
    {
	//Process commands from client	
    	
    	try {
			String name = br.readLine();
			String Password = br.readLine();
			
			if(matchUser(name,Password))
			{
				pw.println("Login successfuly!!");
				pw.flush();
				
				while(true)
				{ 
					String  command = br.readLine();
					
					switch(command)
					{
					
					case "LIST":
					{
						
						ArrayList<String> ArryList = getFileList();
						
						for(String s : ArryList)
						{
							pw.println(s);
							pw.flush();
							
							System.out.println(s);
							
							
						}
						
						
						
						break;
					}
					case "Down":
					{
						String IDNamaServer = br.readLine();
						//now validating the if 
						String FileNameId = idToFile(IDNamaServer); //finding if the file name exist or not 
						
						//now sending the file size & name of the file 
						
						//since the file is with the ud we have to split it 
						String[] Filename_split = FileNameId.split(" ",2);
						
						String FileName = Filename_split[1];
						
						File myfile = new File("data/server/"+FileName);
						pw.println(FileName.length());
						pw.flush();
						
						fis = new FileInputStream(myfile);
						byte[] buffer = new byte[2048];
						int n =0;
						
						while((n=fis.read(buffer))>0)
						{
							dos.write(buffer,0,n);
							dos.flush();
							
						}
						
						System.out.println("Done writin byes");
						
						
						
						
						break;
					}
					case "UP":
					{
						
						
						break;
					}
					case "OFF":
					{
						
						newConnectionToClient .close();
						pw.close();
						br.close();
						
						break;
	
					}
					}
					
					
					
					
				}
				
				
			}
			else
			{
				
				pw.println("Login Unscessfully!! wrong details");
				pw.flush();
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    }
    
    private boolean matchUser(String username,String password)
    {
	boolean found = false;
	File userFile = new File("data/server/users.txt");
	try
	{
		//Code to search users.txt file for match with username and password
	    Scanner scan = new Scanner(userFile);
	    while(scan.hasNextLine()&&!found)
	    {
		String line = scan.nextLine();
		String lineSec[] = line.split(" ",2);
		
		String Name = lineSec[0];
		String Password = lineSec[1];
		
		if(Name.equals(username) && Password.equals(password))
		{
			
			found = true;
			
			
		}
		else
		{
			
			found = false;
			
		}
    		
		//***OMITTED - Enter code here to compare user*** 
		
	    }
	    scan.close();
	}
	catch(IOException ex)
	{
	    ex.printStackTrace();
	}
	
	return found;
    }
    
    private ArrayList<String> getFileList()
    {
		ArrayList<String> result = new ArrayList<String>();
		//Code to add list text file contents to the arraylist.
		File lstFile = new File("data/server/PdfList.txt");
		try
		{
			Scanner scan = new Scanner(lstFile);

			//***OMITTED - Read each line of the file and add to the arraylist***
			while(scan.hasNextLine())
			{
				
				String Massage = scan.nextLine();
				
				result.add(Massage);
				
				
				
			}
			scan.close();
		}	    
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		return result;
    }
    
    private String idToFile(String ID)
    {
    	String result = "";
    	//Code to find the file name that matches strID
    	File lstFile = new File("data/server/PdfList.txt");
    	try
    	{
    		Scanner scan = new Scanner(lstFile);
    		String line = "";
    		//***OMITTED - Read filename from file and search for filename based on ID***
    		while(scan.hasNextLine())
    		{
    			line = scan.nextLine();
    			if(line.equals(ID))
    			{
    				System.out.println("The file name was found, there exist");
    				return result =ID;  //return the string 
    				
    			}
    			
    			
    		}
    		
    		System.out.println("no matching id");  //i would write this ti guid id had i chance 
    		scan.close();
    	}
    	catch(IOException ex)
    	{
    		ex.printStackTrace();
    	}
    	return result;
    }
}
