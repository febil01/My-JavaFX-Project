package JavaApplication;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class signupController implements Initializable {
	@FXML
	private AnchorPane signup;
	@FXML
	private Label lbl_close,errEmail,errPass,errPhone,errFname,errLname,errAdd,errConfirmPass;
	@FXML
	private Button btn_signin;
	@FXML
	private TextField txtfname,txtlname,txtpass,txtphone,txtemail,txtpassconfirm;
	@FXML
	private javafx.scene.control.TextArea txtadd;
	
	
	public static Stage signin;
	public static Parent signinroot;
	String patternString="^[a-zA-Z0-9_]+[@]+[a-zA-Z]+[.]+[a-zA-Z]+$";
	
	Pattern p=Pattern.compile(patternString);

	Boolean checknull=true,checkerror=false,checkemail=false,checkphone=false,checkpass=false,checkfname=false,checklname=false,checkadd;
	Alert alert=new Alert(AlertType.NONE);
	
	
	public void validatenullcheck() {
		
		if(txtemail.getText().trim().isEmpty()) {
			errEmail.setText("Please enter your email");
			txtemail.setStyle("-fx-border-color:red");
			checknull=false;
		}
		if(txtpass.getText().trim().isEmpty()) {	
			errPass.setText("Please enter your password");
			txtpass.setStyle("-fx-border-color:red");
			txtpassconfirm.setStyle("-fx-border-color:red");
			checknull=false;
		}
		if(txtfname.getText().trim().isEmpty()) {
			errFname.setText("Please enter your first name");
			txtfname.setStyle("-fx-border-color:red");
			checknull=false;
		}
        if(txtlname.getText().trim().isEmpty()) {
			errLname.setText("Please Enter your last name");
			txtlname.setStyle("-fx-border-color:red");
			checknull=false;
		}
		if(txtadd.getText().trim().isEmpty()) {
			errAdd.setText("Please enter your address");
			txtadd.setStyle("-fx-border-color:red");
			checknull=false;
		}
		if(txtphone.getText().trim().isEmpty()) {
			errPhone.setText("Please enter your phone number");
			txtphone.setStyle("-fx-border-color:red");
			checknull=false;
		
		}
	}
	
	
	public void validate(int check) {
		if(check==1) {
			
			Matcher matcher=p.matcher(txtemail.getText());
			if(matcher.find() || txtemail.getLength()==0) {
				errEmail.setText("");
				txtemail.setStyle(null);
				checkemail=true;
			}
			else {
				txtemail.setStyle("-fx-border-color:red");
				errEmail.setText("Enter a valid email");
				checkemail=false;
			}
		}
		
		else if(check==2)
		{
			if(txtphone.getLength()==10 || txtphone.getLength()==0) {
				errPhone.setText("");
				txtphone.setStyle(null);
				checkphone=true;
			}
			else {
				errPhone.setText("Enter a 10 digit phone number");
				txtphone.setStyle("-fx-border-color:red");
				checkphone=false;
			}
		}
		
		else if(check==3)
		{
			if(!txtpass.getText().equals(txtpassconfirm.getText())) {
				errPass.setText("Passwords do not match");
				txtpass.setStyle("-fx-border-color:red");
				txtpassconfirm.setStyle("-fx-border-color:red");
				checkpass=false;
			}
			else {
				errPass.setText("");
				txtpass.setStyle(null);
				txtpassconfirm.setStyle(null);
				checkpass=true;
			}
		}
		
		else if(check==4)
		{
			if(!(txtfname.getLength() >= 2 && txtfname.getLength() <=32 && txtfname.getLength()>0)) {
				errFname.setText("First name must be between 2-32 characters");
				txtfname.setStyle("-fx-border-color:red");
				checkfname=false;
			}
			else {
				errFname.setText("");
				txtfname.setStyle(null);
				checkfname=true;
			}
		}
		
		else if(check==5) {
			if(!(txtlname.getLength() >=2 && txtlname.getLength() <=32 && txtlname.getLength()>0)) {
				errLname.setText("Last name must be between 2-32 characters");
				txtlname.setStyle("-fx-border-color:red");
				checklname=false;
			}
			else {
				errLname.setText("");
				txtlname.setStyle(null);
				checklname=true;
			}
		}
		
		else if(check==6) {
			if(txtadd.getLength()>0) {
				txtadd.setStyle(null);
				errAdd.setText("");
			}
		}
		
		
		checkerror=(checkemail & checkphone & checkpass & checkfname & checklname) ? true : false;
	}
		
	public static void setstage(Stage stage,Parent parent) {
	signin=stage;	
	signinroot=parent;
	}
	
	public void dbsignup() {
		String url="jdbc:mysql://localhost:3306/pcshop";
		String user="root";
		String password="123456";	
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection(url, user, password);
			PreparedStatement pr;
			pr=con.prepareStatement("INSERT INTO userinfo(Email,Password,First_Name,Last_Name,Address,phone) values(?,?,?,?,?,?)");
			pr.setString(1, txtemail.getText());
			pr.setString(2, txtpass.getText());
			pr.setString(3, txtfname.getText());
			pr.setString(4, txtlname.getText());
			pr.setString(5, txtadd.getText());
			pr.setString(6, txtphone.getText());
			int noerror=pr.executeUpdate();
			if(noerror!=0) {
				Alert a=new Alert(AlertType.CONFIRMATION);
				a.setContentText("Updated succesfully");
				a.show();
			}

		}
		catch(Exception e) {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("An error has occured");
			alert.setContentText("Email already exists");
			alert.show();
		}
	}
	
	public void signup() {
		signup.requestFocus();
		validatenullcheck();
		if(checkerror & checknull) {
			dbsignup();
			return;
		}
		else {
			alert.setAlertType(AlertType.ERROR);
			alert.setTitle("Enter details");
			alert.show();
		}
		checknull=true;
		checkerror=false;
	}

	public void ButtonClose(MouseEvent event) {
		if(event.getButton()==MouseButton.PRIMARY)
		{
		Stage stage = (Stage) lbl_close.getScene().getWindow();
		stage.close();
		}
	}
	
	public void emailcheck() {
		validate(1);
	}
	
	public void phonecheck() {
		validate(2);
	}
	
	public void passcheck()	{
		validate(3);
	}
	
	public void fnamecheck() {
		validate(4);
	}
	
	public void lnamecheck() {
		validate(5);
	}
	
	public void addcheck() {
		validate(6);
	}
	
	public void Buttonsignin(MouseEvent event) {
		if(event.getButton()==MouseButton.PRIMARY) {
			initialize(null, null);
     		Stage stage=(Stage) signup.getScene().getWindow();
			stage.close();
			signinroot.requestFocus();
			signin.show();
	}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtemail.setText("");
		txtadd.setText("");
		txtfname.setText("");
		txtlname.setText("");
		txtphone.setText("");
		txtpass.setText("");
		txtpassconfirm.setText("");
		errEmail.setText("");
		errPass.setText("");
		errPhone.setText("");
		errFname.setText("");
		errLname.setText("");
		errAdd.setText("");
		
		errEmail.setAlignment(Pos.BASELINE_RIGHT);
		errPass.setAlignment(Pos.BASELINE_RIGHT);
		errFname.setAlignment(Pos.BASELINE_RIGHT);
		errLname.setAlignment(Pos.BASELINE_RIGHT);
		errPhone.setAlignment(Pos.BASELINE_RIGHT);
		errAdd.setAlignment(Pos.BASELINE_RIGHT);
		errConfirmPass.setAlignment(Pos.BASELINE_RIGHT);
	}
	
}