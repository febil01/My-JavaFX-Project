
package JavaApplication;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProductDescriptionController implements Initializable {
@FXML
private Label product,price;
@FXML 
private javafx.scene.control.TextArea description;
@FXML
private ImageView image;
@FXML
private AnchorPane apane;
String item;
String s;
String url="jdbc:mysql://localhost:3306/pcshop";
String user="root";
String Password="123456";
Parent p;
public void set(String data) {
	item=data;
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection(url, user, Password);
		Statement stt=con.createStatement();
		String statement="SELECT * FROM partsinfo WHERE pname='"+item+"'";
		ResultSet rs=stt.executeQuery(statement);
		while(rs.next()) {
			product.setText(""+rs.getString("pname"));
			price.setText("Rs."+rs.getDouble("price"));
			description.setText(""+rs.getString("description"));
			image.setImage(new Image(""+rs.getString("path")));
		}
		
	}
	catch(Exception e) {
		System.out.println("Not found");
	}
}

public void previous(MouseEvent event) {
	try {
		p=FXMLLoader.load(getClass().getResource("/javaFXML/Home.fxml"));
	} 
	catch (Exception e) {
		e.printStackTrace();
			}
	Stage stage=(Stage) apane.getScene().getWindow();
	stage.getScene().setRoot(p);
}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {				
		description.setEditable(false);
	}
}
