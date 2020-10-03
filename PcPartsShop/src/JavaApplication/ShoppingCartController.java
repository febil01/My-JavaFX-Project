package JavaApplication;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ShoppingCartController implements Initializable{	
	@FXML
	private GridPane cart;
	@FXML
	private Label lblTotal;
	Stage home=new Stage();
	int col=2,row=1;
	double price;
	int count=3,k=0;
	RowConstraints rows = new RowConstraints();
	ColumnConstraints columns = new ColumnConstraints();
	StackPane s[]=new StackPane[100];
	Label setdata[]=new Label[100];
	TextField t[]=new TextField[100];
	Button b[]=new Button[100];
	HBox h[]=new HBox[100];
	ArrayList<String> items=new ArrayList<String>();
	Alert a=new Alert(AlertType.ERROR);
	
	public void initdata(ArrayList<String> item) {
		this.items=item;
	}
	
	public void setcart() {
		int starti=0,headindex=0;
		count=0;
		k=0;
		String headingtext[]= {"Product name","Quantity","Amount","Total"};
		for(int i=0;i<=0;i++) {
			for(int j=0;j<=3;j++) {
				setdata[count]=new Label();
				s[count]=new StackPane();	
				setdata[count].setPrefWidth(120);
				setdata[count].setAlignment(Pos.CENTER);
				setdata[count].setText(headingtext[headindex]);
				s[count].getChildren().add(setdata[count]);	
				s[count].setPrefWidth(251);
				s[count].setPrefHeight(15);
				s[count].setScaleShape(true);
				s[count].setCenterShape(true);
				s[count].setCacheShape(true);
				cart.add(s[count], j, i);
				count++;
				headindex++;
			}
		}
		
		for(int i=1;i<=items.size()/4;i++)
		{
			for(int j=0;j<4;j++) {
				setdata[count]=new Label();
				if(j==1) {
				t[k]=new TextField();
				b[k]=new Button();
				b[k].setOnMouseClicked(new EventHandler<MouseEvent>(){
					@Override
					public void handle(MouseEvent mouseEvent) {
						for(int z=0; z<items.size()/4;z++) {
						if(mouseEvent.getButton()==MouseButton.PRIMARY && b[z].isHover()) {
							String temp=t[z].getText();
							if(!temp.matches("(0|[1-9]\\d*)")) {
								a.show();
								return;
							}
							updatecart(z, Double.parseDouble(t[z].getText()));
							setcartno();
						}
						}
					}
				});
				
				h[k]=new HBox();
				h[k].setAlignment(Pos.CENTER);
				t[k].setText(""+items.get(starti));
				t[k].setMaxWidth(30);
				t[k].setAlignment(Pos.CENTER);
				b[k].setText("update cart");
				h[k].getChildren().addAll(t[k],b[k]);
				}
				
				s[count]=new StackPane();
				s[count].setPrefWidth(251);
				s[count].setPrefHeight(15);
				s[count].setScaleShape(true);
				s[count].setCenterShape(true);
				s[count].setCacheShape(true);
				
				setdata[count].setAlignment(Pos.CENTER);
				setdata[count].setPrefWidth(120);
				
				setdata[count].setText(items.get(starti));
				if(j==1) {
					s[count].getChildren().addAll(h[k]);	
					k++;
				}
				else {
				s[count].getChildren().add(setdata[count]);	
				
				}
				cart.setVgap(20);
				cart.add(s[count], j, i);
				count++;
				starti++;
		}
		}
		calctotal();
	}
	
	@FXML
	void checkout(MouseEvent event) {
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/javaFXML/payment.fxml"));
		Stage primaryStage=new Stage();
		try {
			primaryStage.setScene(new Scene((Pane) loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Stage stage=(Stage) cart.getScene().getWindow();
		stage.close();
		primaryStage.show();
	}
	
	void setcartno() {
		
	}
	
	void calctotal() {
		double totalAmt=0;
		for(int i=3;i<items.size();i+=4) {
			totalAmt=totalAmt+Double.parseDouble(items.get(i));
		}
		lblTotal.setText("Total: Rs."+totalAmt);
	}
	
	public void updatecart(int index,double quantity) {
		index=index*4;
		if(quantity==0) {
			
			this.items.remove(index);
			this.items.remove(index);
			this.items.remove(index);
			this.items.remove(index);
			cart.getChildren().clear();
			setcart();
	}
		else {
			items.set(index+1,""+quantity);
			Double v1=Double.parseDouble(items.get(index+1));
			Double v2=Double.parseDouble(items.get(index+2));
			double v3=v1*v2;
			items.set(index+3,""+v3);
			cart.getChildren().clear();
			setcart();
		}
	}	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setcart();
	}
}
