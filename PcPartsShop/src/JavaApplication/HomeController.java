package JavaApplication;

import java.awt.ScrollPane;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class HomeController implements Initializable {

	@FXML
	private TextField searchbox;
	@FXML
	private TilePane tileProfile;
	@FXML
	private GridPane gridHome;
	@FXML
	private AnchorPane content;
	@FXML
	private ImageView image;
	@FXML
	private Button button;
	@FXML
	private Label label;
	@FXML
	private ScrollPane scroll;

	/*
	public void profile(MouseEvent event) {
		if(event.getButton()==MouseButton.PRIMARY) {
			if(tileProfile.isVisible()) {
				tileProfile.setVisible(false);
			}
			else {
				tileProfile.setVisible(true);
			}
		}
	}
	
*/
	public void home()
	{
		String url="jdbc:mysql://localhost:3306/pcshop";
		String user="root";
		String password="123456";	
		ArrayList<String> imagepath=new ArrayList<String>();
		ArrayList<String> pname=new ArrayList<String>();
		int gridlength=0;
		
		try
		{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url, user, password);
		Statement stt=con.createStatement();
		ResultSet rs=stt.executeQuery("Select * FROM partsinfo");
		while(rs.next())
		{
			pname.add(rs.getString("pname"));
			imagepath.add(rs.getString("image"));
			gridlength++;
		}
		}
		catch(Exception e)
		{			
		System.out.println("oops");
		return;
		}	
		
		ImageView im[]=new ImageView[gridlength];
		StackPane a[]=new StackPane[gridlength];
		Button b[]=new Button[gridlength];
		Label l[]=new Label[gridlength];
		VBox v[]=new VBox[gridlength];
	    int k=0;
        int rowCount=gridlength/5,columnCount=5;
		RowConstraints row = new RowConstraints();
		ColumnConstraints column = new ColumnConstraints();
        
		if(gridlength <=5)
		{
			columnCount=gridlength;
			rowCount=1;
		}
		
		for (int i = 0; i < columnCount; i++) {
            gridHome.getColumnConstraints().add(column);
        }
	    for (int i = 0; i < rowCount; i++) {
            gridHome.getRowConstraints().add(row);
        }
	    
		
		for(int i=0;i<rowCount;i++)
		{
			System.out.println(" "+i);
			for(int j=0;j<columnCount;j++)
			{
				System.out.println(" "+j);
				a[k]=new StackPane();
				
				v[k]=new VBox();				
				v[k].setSpacing(5);
				
				a[k].setPrefWidth(251);
				a[k].setPrefHeight(236);
				a[k].setScaleShape(true);
				a[k].setCenterShape(true);
				a[k].setCacheShape(true);
				
		        RowConstraints row1 = new RowConstraints();
				ColumnConstraints column1 = new ColumnConstraints();
	
				column1.setHalignment(HPos.CENTER);
				row1.setValignment(VPos.CENTER);
				
				column1.setHgrow(Priority.ALWAYS);
				row1.setVgrow(Priority.ALWAYS);
				
				im[k]=new ImageView(imagepath.get(k));
				im[k].setFitWidth(195);
				im[k].setFitHeight(140);
				im[k].setPreserveRatio(true);
				im[k].setSmooth(true);
				im[k].setPickOnBounds(true);

		
				b[k]=new Button();
				b[k].setText("Add to Cart");
				b[k].setPrefWidth(100);
				b[k].setPrefHeight(32);
				b[k].setAlignment(Pos.CENTER);
				
				l[k]=new Label();
				l[k].setPrefWidth(189);
				l[k].setPrefHeight(18);
				l[k].setAlignment(Pos.CENTER);
				l[k].setText(pname.get(k));
								
				v[k].getChildren().addAll(im[k],l[k],b[k]);
				a[k].getChildren().add(v[k]);
				v[k].setAlignment(Pos.CENTER);
				
				gridHome.add(a[k], j, i);
				k++;
			}
		}
	}		

	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		home();
	}
}