package JavaApplication;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class profileController implements Initializable {
	@FXML
	private Label lblname;
	String url="jdbc:mysql://localhost:3306/pcshop";
	String user="root";
	String Password="123456";
	public void setname(int id)
	{   
		String name = null;
		try 
		{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url, user, Password);
		Statement stt=con.createStatement();
		ResultSet rs;
		rs=stt.executeQuery("Select * FROM userinfo where pid="+id);
		while(rs.next()) {
		name=rs.getString("first_name") + rs.getString("last_name");
		lblname.setText(name);
		}
		
		}
		catch (Exception e) {
			
		}
	}
	public void signout(KeyEvent event) {
		System.exit(0);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

}
