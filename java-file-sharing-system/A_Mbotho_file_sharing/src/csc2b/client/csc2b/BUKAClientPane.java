package csc2b.client.csc2b;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class BUKAClientPane extends GridPane //You may change the JavaFX pane layout
{
	//STREAMS TO WRITE OVER A NETWORK
	private Socket s;
	private PrintWriter pw;
	private BufferedReader br;
	private DataInputStream dis;
	
	
	//MY LABELS 
	private Label lblName;
	private Label lblPassword;
	private Label lblLogin;
	//private Label UserName;
	
	//MY TEXTFIELD
	
	private TextField txtName;
	private TextField txtPassword;
	private TextField txtLogin;
	private TextField txtIDname;
	
	//TEXTARES
	
	private TextArea areaList;
	private TextArea areaStatues;
	
	
	//BUTTONS 
	
	private Button btnLogin;
	private Button btnList;
	private Button btnDownlist;
	private Button btnLOGFF;
	private Button btnUpload;
		

	
	
    public BUKAClientPane()
    {
	//Create client connection
    	ClientConnection();
    	
	//Create buttons for each command
    	MYGUID();
    	
	//Use buttons to send commands
    	EventHANDLEbuttons();
    	
    }
    
    private void  EventHANDLEbuttons()
    {
    	//event handling login button
    	btnLogin.setOnAction((e)->{
   	  String name = txtName.getText();
	  String Password = txtPassword.getText();
	  
	 
	  
	  
	  //now sending the name and Password to client 
	  
	  pw.println(name);
	  pw.flush();
	  pw.println(Password);
	  pw.flush();
	
	  //reading respose form the server 
	  try {
		String Response = br.readLine();
		areaStatues.setText(Response);
		
		
		
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	 
	   
	   
   });
    	btnList.setOnAction((e)->{
    		
    		pw.println("LIST");
    		pw.flush();
    		
    		//reading the array of list 
    		
    		try {
				String List_ONE = br.readLine();
				String List_two = br.readLine();
	    		String List_Three = br.readLine();
	    		
	    		String[] mylist = {List_ONE,List_two,List_Three};
	    		
	    		StringBuilder displaytext = new StringBuilder();
	    		
	    		 for(String s: mylist)
	    		 {
	    			 displaytext.append(s).append("\n");
	    			 
	    		 }
	    		 areaList.setText(displaytext.toString().trim());
	    		 
	    		
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		
    		
    	});
    	
    	btnDownlist.setOnAction((e)->{
    		
    		pw.println("Down");
    		pw.flush();
    		
    		
    		
    		//now sending the File id 
    		
    		String massage = txtIDname.getText();
    		
    		pw.println(massage);
    		pw.flush();
    		
    		
    		File myfile = new File("data/client/" + massage);
    		
    		
    		
    		//now reading the file size 
    		try {
    			FileOutputStream fos = new FileOutputStream(myfile);
    			
				String Temp_Filesize = br.readLine();
				int filesize = Integer.parseInt(Temp_Filesize);
			
				byte[] buffer = new byte[1024];
				
				int n=0;  //keep count of the number of bytes 
				int total =0;
				while(total != filesize)
				{
					
					n=dis.read(buffer,0,buffer.length);
					
					fos.write(buffer,0,n);
					
					fos.flush();
					
					
					
				}
			 System.out.println("Done reading");
			 
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		
    		
    		
    	});
    	
    	btnLOGFF.setOnAction((e)->{
    		pw.println("OFF");
    		pw.flush();
    	try {
    		if(s!= null)
    		{
    			s.close();
    			pw.close();
    			br.close();
    		}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    		
    		
    	});
    	
    }
    private void MYGUID()
    {
    	
    	
    	setHgap(10);
    	setVgap(10);
    	setAlignment(Pos.BASELINE_LEFT);
    	
    	
    	  lblName = new Label("Name :");
    	 lblPassword = new Label("Password :");
    	 lblLogin = new Label("LOGED IN :");
    	 
    	 //Initiate textfied
    	 
    	 txtName  = new TextField();
    	 txtPassword =new TextField();
    	 txtLogin = new TextField();
    	 txtIDname = new TextField();
    	 
    	 //Initiate area field
    	 
    	 areaList = new TextArea();
    	areaStatues = new TextArea();
    	
    	//Initiate GUI 
    	
     btnLogin = new Button("LOGIN"); 
     btnList = new Button("LIST");
     btnDownlist = new Button("Downlaod");
     btnLOGFF = new Button("LOGOUT");
     btnUpload = new Button("UPLOAD");
     
     
    		
    		
    	 
    	 add(lblName,0,0);
    	 add(txtName,1,0);
    	 add(lblPassword,0,2);
    	 add(txtPassword,1,2);
    	 add(btnLogin,1,3);
    	 add(lblLogin,0,4);
    	 add(areaStatues,1,4);
    	 add(btnList,1,5);
    	 add(areaList,1,6);
    	 add(txtIDname,1,7);
    	 add(btnDownlist,2,7);
    	 add(btnUpload,1,8);
    	 add(btnLOGFF,1,9);	
    }
    
    private void ClientConnection()
    {
    	
    	try {
			s  = new Socket("localhost", 40);
			pw = new PrintWriter(s.getOutputStream(),true);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			dis = new DataInputStream(s.getInputStream());
			
			System.out.println("client ready to make connection");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
	   System.out.println("Oops no connection!");
	   
	   
		}
    	
    	
    }
    
    
}
