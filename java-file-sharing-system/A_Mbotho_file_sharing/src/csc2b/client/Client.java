package csc2b.client;
import csc2b.client.csc2b.BUKAClientPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application
{
    public static void main(String[] args)
    {
  launch(args);
  
  //please do uloading of this file to a server 
  
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		//create the ClientPane, set up the Scene and Stage
		
		BUKAClientPane myGrid = new BUKAClientPane();
		
		Scene myscene = new Scene(myGrid,500,500);
		primaryStage.setTitle("Buka");
		primaryStage.setScene(myscene);
		primaryStage.show();
		
		
				
	}
}
