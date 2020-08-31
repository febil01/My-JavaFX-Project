package JavaApplication;
import java.awt.ScrollPane;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


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
	int k;
	@FXML
	private ComboBox<String> comboC;
	static String current;
	
	ObservableList<String> type=FXCollections.observableArrayList("Default category","Processors","Motherboards","Monitors","Keyboards");
	
	String url="jdbc:mysql://localhost:3306/pcshop";
	String user="root";
	String password="123456";
	ArrayList<String> imagepath=new ArrayList<String>();
	ArrayList<String> pname=new ArrayList<String>();
	ArrayList<Double> price=new ArrayList<Double>();
	ArrayList<String> searchlist=new ArrayList<String>();
	int gridlength=0;
	int suggestcount;
	int no=0;
	String sql;
	ArrayList<Integer> suggestions=new ArrayList<Integer>();
	
	public void searchString(KeyEvent event)
	{	
		if(searchbox.getText().equals(null)) {
		gridHome.getChildren().clear();
		display(gridlength);
		return;
	}

		if(event.getCode()==KeyCode.ENTER) {
		suggestcount=0;
		for(int i=0;i<gridlength;i++) {
			if(searchlist.get(i).toLowerCase().equals(searchbox.getText().toLowerCase())) {
				suggestions.add(i);
				suggestcount++;
			}
		}
		display(suggestcount);
		}
	
	}
	public void home(String cat)
	{	gridlength=0;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url, user, password);
		Statement stt=con.createStatement();
		ResultSet rs;
		if(cat.equals("Default")) {
			rs=stt.executeQuery("Select * FROM partsinfo");
		}
		else
		{
			sql="Select * FROM partsinfo WHERE category='%'";
			sql=sql.replace("%", cat);
			rs=stt.executeQuery(sql);
		}
		while(rs.next()) {
			pname.add(rs.getString("pname"));
			imagepath.add(rs.getString("path"));
			price.add(rs.getDouble("price"));
			gridlength++;
		}
		}
		catch(Exception e) {			
		return;
		}	
		display(gridlength);
	}

	public void display(int length)
	{
		gridHome.getChildren().clear();
		ImageView image[]=new ImageView[length];
		StackPane spane[]=new StackPane[length];
		Button button[]=new Button[length];
		Label product[]=new Label[length],amount[]=new Label[length];
		VBox vbox[]=new VBox[length];
		k=0;

        int rowCount=length/5,columnCount=5;
		RowConstraints row = new RowConstraints();
		ColumnConstraints column = new ColumnConstraints();
        
		if(length <=5)
		{
			columnCount=length;
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
			for(int j=0;j<columnCount;j++)
			{
				spane[k]=new StackPane();
				
				vbox[k]=new VBox();				
				vbox[k].setSpacing(10);
				
				spane[k].setPrefWidth(251);
				spane[k].setPrefHeight(236);
				spane[k].setScaleShape(true);
				spane[k].setCenterShape(true);
				spane[k].setCacheShape(true);
				
		        RowConstraints row1 = new RowConstraints();
				ColumnConstraints column1 = new ColumnConstraints();
	
				column1.setHalignment(HPos.CENTER);
				row1.setValignment(VPos.CENTER);
				
				column1.setHgrow(Priority.ALWAYS);
				row1.setVgrow(Priority.ALWAYS);
				
				image[k]=new ImageView(imagepath.get(k));
				image[k].setFitWidth(195);
				image[k].setFitHeight(140);
				image[k].setPreserveRatio(true);
				image[k].setSmooth(true);
				image[k].setPickOnBounds(true);
		
				button[k]=new Button();
				button[k].setText("Add to Cart");
				button[k].setPrefWidth(100);
				button[k].setPrefHeight(32);
				button[k].setAlignment(Pos.CENTER);
				
				product[k]=new Label();
				product[k].setPrefWidth(189);
				product[k].setPrefHeight(18);
				product[k].setAlignment(Pos.CENTER);
				product[k].setText(pname.get(k));
				
				product[k].setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
				@Override
			    public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				for(int z=0;z<gridlength;z++) {
					if(mouseEvent.getButton()==MouseButton.PRIMARY && product[z].isHover()) {
						change(product[z].getText());
				}
			    }
				}
			    });
				
				amount[k]=new Label();
				amount[k].setPrefWidth(189);
				amount[k].setPrefHeight(18);
				amount[k].setAlignment(Pos.CENTER);
				amount[k].setText("Rs."+price.get(k));
				
				vbox[k].getChildren().addAll(image[k],amount[k],product[k],button[k]);
				spane[k].getChildren().add(vbox[k]);
				vbox[k].setAlignment(Pos.CENTER);
				
				gridHome.add(spane[k], j, i);
				k++;
			}
		}
	k=0;
	pname.clear();
	imagepath.clear();
	price.clear();
	
	}
	
	void change(String item) {
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/javaFXML/ProductDescription.fxml"));
		Stage primaryStage=new Stage();
		try {
			primaryStage.setScene(new Scene((Pane) loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ProductDescriptionController controller=loader.<ProductDescriptionController>getController();
		controller.set(item);
		Stage stage=(Stage) gridHome.getScene().getWindow();
		stage.close();
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		comboC.setItems(type); 
		comboC.getSelectionModel().selectFirst();
		//comboC.setc
		comboC.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				if(arg2!=null) {
					switch(arg2) {
					case "Monitors":	home("Monitors");break;
					case "Keyboards":	home("Keyboards");break;
					case "Processors":	home("Processors");break;
					case "Motherboards":home("Motherboards");break;
					case "Default category":home("Default"); break;
					}
				}
			}
		});
		home("Default");
		searchlist=pname;
		//TextFields.bindAutoCompletion(search, pname);
	}

}
