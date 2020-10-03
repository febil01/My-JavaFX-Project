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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
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
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public class HomeController implements Initializable {
	@FXML
	private HomeController controller;
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
	Boolean start=true;
	@FXML
	private HomeController homeController;
	
	Boolean checkduplicate=false;
	ObservableList<String> type=FXCollections.observableArrayList("Default category","Processors","Motherboards","Monitors","Keyboards");
	Alert a=new Alert(null);
	String url="jdbc:mysql://localhost:3306/pcshop";
	String user="root";
	String Password="123456";
	
	ArrayList<String> imagepath=new ArrayList<String>();
	ArrayList<String> pname=new ArrayList<String>();
	ArrayList<Double> price=new ArrayList<Double>();
	ArrayList<String> searchlist=new ArrayList<String>();
	int gridlength=0;
	int suggestcount;
	int no=0;
	String sql;
	ArrayList<Integer> suggestions=new ArrayList<Integer>();
	Stage cart=new Stage();
	ArrayList<String> shoppingcart=new ArrayList<String>();
	ShoppingCartController cartcontroller;
	
	@FXML
	private Label cartcounter;
	
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
	
	public ArrayList<String> returncart(){
		return this.shoppingcart;
	}
	
	public void home(String cat)
	{	gridlength=0;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url, user, Password);
		Statement stt=con.createStatement();
		ResultSet rs;
		int id=1;
		
		if(cat.equals("Default")) {
			rs=stt.executeQuery("Select * FROM partsinfo");
		}
		
		else {	
			id=getid(cat);
			sql="Select * FROM partsinfo WHERE category_id=" + id;
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
		Alert fail=new Alert(AlertType.ERROR);
		fail.setContentText("Connection to server failed");
		fail.setTitle("Communications link failure");
		fail.show();
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
		Label product[]=new Label[length],amount[]=new Label[length],qty[]=new Label[length];
		TextField qval[]=new TextField[length];
		VBox vbox[]=new VBox[length];
		k=0;

        int rowCount=length/5,columnCount=5;
		//RowConstraints row = new RowConstraints();
		//ColumnConstraints column = new ColumnConstraints();
        
		if(length <=5)
		{
			columnCount=length;
			rowCount=1;
		}
		
		/*
		for (int i = 0; i < columnCount; i++) {
            gridHome.getColumnConstraints().add(column);
        }
	    for (int i = 0; i < rowCount; i++) {
            gridHome.getRowConstraints().add(row);
        }
	    */
		
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
				button[k].setId(""+k);
				
				product[k]=new Label();
				product[k].setPrefWidth(400);
				product[k].autosize();
				product[k].setPrefHeight(18);
				product[k].setAlignment(Pos.CENTER);
				product[k].setText(pname.get(k));
				
				qty[k]=new Label();
				qty[k].setText("qty:");
				qty[k].setAlignment(Pos.CENTER);
				qty[k].setMaxHeight(12);
				
				qval[k]=new TextField();
				qval[k].setText("1");
				qval[k].setAlignment(Pos.CENTER);
				qval[k].setMaxWidth(100);
				qval[k].setPrefHeight(12);

				product[k].setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
				@Override
			    public void handle(javafx.scene.input.MouseEvent mouseEvent) {
				for(int z=0;z<gridlength;z++) {
					if(mouseEvent.getButton()==MouseButton.PRIMARY && product[z].isHover()) {
						change(product[z].getText());
						break;
				}
			    }
				}
			    });
				
				button[k].setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
				@Override
					public void handle(javafx.scene.input.MouseEvent mouseEvent) {
						for(int z=0;z<gridlength;z++) {
						if(mouseEvent.getButton()==MouseButton.PRIMARY && button[z].isHover()) {
						start=false;
						if(qval[z].getText().equals("0")) {
							return;
						}
						String tempVal=qval[z].getText();
						if(!tempVal.matches("(0|[1-9]\\d*)")) {
							Alert a=new Alert(AlertType.WARNING);
							a.show();
							return;
						}
						String temp=amount[z].getText().substring(3);
						checkduplicate(product[z].getText(),qval[z].getText(),Double.parseDouble(temp));
						changecart(product[z].getText(), Double.parseDouble(qval[z].getText()),Double.parseDouble(temp));
						updatecartno();
						break;
						
				}
				}
				}
				}
				);
				
				
				amount[k]=new Label();
				amount[k].setPrefWidth(189);
				amount[k].setPrefHeight(18);
				amount[k].setAlignment(Pos.CENTER);
				amount[k].setText("Rs."+price.get(k));
				vbox[k].getChildren().addAll(image[k],product[k],amount[k],button[k],qty[k],qval[k]);
				spane[k].getChildren().add(vbox[k]);
				vbox[k].setAlignment(Pos.CENTER);
				gridHome.setVgap(40);
				gridHome.add(spane[k], j, i);
				k++;
			}
		}
	k=0;
	pname.clear();
	imagepath.clear();
	price.clear();
	}
	void checkduplicate(String pname,String q,Double d) {
		Double updateQty=Double.parseDouble(q);
		for(int i=0;i<shoppingcart.size();i++) {
			if(shoppingcart.get(i).equals(pname)) {
				updateQty+=Double.parseDouble(shoppingcart.get(i+1));
				d=updateQty*d;
				shoppingcart.set(i+1,""+updateQty);
				shoppingcart.set(i+3,""+d);
				checkduplicate=true;
				return;
			}
		}
		checkduplicate=false;
	}
	
	void changecart(String name, Double quantity,Double price) {

		if(checkduplicate==false) {
		Double amount=quantity*price;
		shoppingcart.add(name);
		shoppingcart.add(""+quantity);
		shoppingcart.add(""+price);
		shoppingcart.add(""+amount);
		}
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/javaFXML/ShoppingCart.fxml"));
		try {
			cart.setScene(new Scene((Pane) loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		cartcontroller=loader.<ShoppingCartController>getController();
		cartcontroller.initdata(shoppingcart);
		cartcontroller.initialize(null, null);
		checkduplicate=false;
	}
	@FXML
	public void showcart(MouseEvent event) {
		if(event.getButton()==MouseButton.PRIMARY) {
		if(start==true) {
			a.setAlertType(AlertType.WARNING);
			a.setContentText("Cart is empty");
			a.show();
			return;
		}
		cartcounter.setVisible(false);
        cart.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
    			updatecartno();
    			cartcounter.setVisible(true);
            }
        });
		cart.setResizable(false);
		cart.show();
	}
	}
	
	void updatecartno() {
		int cartno,c=0;
		if(shoppingcart.size()==0) {
			cartno=0;
			cartcounter.setText(""+cartno);
			return;
		}
		for(int i=1;i<shoppingcart.size();i+=4) {
			Double d=Double.parseDouble(shoppingcart.get(i));
			c=c+d.intValue();
			cartcounter.setText(""+c);
		}
	}
	
	public int getid(String cat) {
		int id=1;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection(url, user, Password);
			Statement stt=con.createStatement();
			ResultSet rs;
			String sql="SELECT cid from categories where cname="+"'"+cat+"'";
			rs=stt.executeQuery(sql);
			while(rs.next()) {
				id=rs.getInt("cid");
			}
		}
		catch (Exception e) {
			
		}
	return id;
	}
	
	void changelist(ArrayList<String> s) {
		shoppingcart=s;
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
		//ArrayList<String> t=new ArrayList<String>();
		controller.set(item,shoppingcart);
		
        primaryStage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                shoppingcart=controller.returncart();
                checkduplicate=true;
                changecart(null, null, null);
            }
        });
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		a.initModality(Modality.APPLICATION_MODAL);
		a.setAlertType(AlertType.CONFIRMATION);
		a.setContentText("Add item to cart?");
		comboC.setItems(type); 
		comboC.getSelectionModel().selectFirst();
		start=true;
		comboC.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				if(arg2!=null) {
					switch(arg2) {
					case "Monitors":	home("monitors");break;
					case "Keyboards":	home("keyboards");break;
					case "Processors":	home("processors");break;
					case "Motherboards":home("motherboards");break;
					case "Default category":home("Default"); break;
					}
				}
			}
		});
		cart.setTitle("Shopping cart");
		cart.setResizable(false);
		cart.initModality(Modality.APPLICATION_MODAL);
		home("Default");
		searchlist=pname;
	}

}
