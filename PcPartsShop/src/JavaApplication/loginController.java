package JavaApplication;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class loginController implements Initializable {
public static Stage signupstage;
public static Parent signuproot;
@FXML
private BorderPane mainBorder;
@FXML
private AnchorPane login,signup;
@FXML
private Label lbl_close;
@FXML
private TextField txtemail,txtpassword;
@FXML
private Label lblerror;

public void dbsignin(MouseEvent event)
{
if(event.getButton()==MouseButton.PRIMARY)
{
dbsignin();
}
}

public void dbsignin()
{
	signuproot.requestFocus();
	String url="jdbc:mysql://localhost:3306/pcshop";
	String user="root";
	String password="123456";
	try
	{
	    Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url, user, password);
		PreparedStatement pr=con.prepareStatement("SELECT * FROM userinfo WHERE Email=? AND Password=?");
		pr.setString(1, txtemail.getText());
		pr.setString(2, txtpassword.getText());
		ResultSet rs=pr.executeQuery();
		if(rs.next())
		{
			System.out.println("Valid User");
		}
		else
		{
			signuproot.requestFocus();
			lblerror.setText("Invalid login credentials.");
			txtemail.setStyle("-fx-border-color:red");
			txtpassword.setStyle("-fx-border-color:red");
			}
	}

	catch(Exception e)
	{
		lblerror.setText("Connection to server failed.");
		e.printStackTrace();
	}
}


public static void getStage(Stage stage,Parent parent)
{
	signupstage=stage;
	signuproot=parent; 
}
public void ButtonClose(MouseEvent event) {
	if(event.getButton()==MouseButton.PRIMARY)
	{
	Stage stage = (Stage) lbl_close.getScene().getWindow();
	stage.close();
	}
}

public void enter(KeyEvent event)
{
	if(event.getCode()==KeyCode.ENTER)
	{
		dbsignin();
	}
}
public void Buttonsignup(MouseEvent event)
{
	login.requestFocus();
	initialize(null, null);
	if(event.getButton()==MouseButton.PRIMARY)
	{	ButtonClose(event);
		Stage stage=(Stage) login.getScene().getWindow();
		stage.close();
		signuproot.requestFocus();
		signupstage.show();
		
	}
}
public void reseterror(MouseEvent event)
{
	if(txtemail.isFocused())
	{
	txtemail.setStyle(null);
	}
	if(txtpassword.isFocused())
	{
	txtpassword.setStyle(null);
    }
}
@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	lblerror.setText("");
	txtemail.setText("");
	txtpassword.setText("");
	login.requestFocus();
	txtemail.setStyle(null);
	txtpassword.setStyle(null);	
	
	}

}
