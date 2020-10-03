
package JavaApplication;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.javafx.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
int count=0;
ArrayList<String> scart=new ArrayList<String>();

public void set(String data,ArrayList<String> scart) {
	this.scart=scart;
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

public ArrayList<String> returncart(){
	for(int i=0;i<scart.size();i++) {
		if(scart.get(i).equals(product.getText())) {
			Double temp=count+Double.parseDouble(scart.get(i+1));
			scart.set(i+1,""+temp);
		}		
	}
	return this.scart;
}

@FXML
public void updatecart(MouseEvent event){
count++;
}

public void previous(MouseEvent event) {
	Stage stage=(Stage) apane.getScene().getWindow();
	stage.close();

}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {				
		description.setEditable(false);
	}
}
