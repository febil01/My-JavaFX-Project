package JavaApplication;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class paymentController implements Initializable{

	    @FXML
	    private RadioButton radioCash;

	    @FXML
	    private TextField holder;

	    @FXML
	    private TextField number;

	    @FXML
	    private TextField mm;

	    @FXML
	    private Label lblcard;

	    @FXML
	    private TextField yy;

	    @FXML
	    private RadioButton radioCard;

	    @FXML
	    private javafx.scene.control.TextArea lblAddress;

	    @FXML
	    private Label lblTotal;

	    @FXML
	    private Label lblPhone;

	    @FXML
	    private Label lblCustomer;
	    @FXML
	private AnchorPane parentroot;
	int uid;
	ArrayList<String> cartitems=new ArrayList<String>();
	String s;
	int length;
	String url="jdbc:mysql://localhost:3306/pcshop";
	String user="root";
	String Password="123456";
	int item_id=0;
	double total_amt=0;
	int quantity=0;
	
	public int getid(String cat) {
		int id=1;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection(url, user, Password);
			Statement stt=con.createStatement();
			ResultSet rs;
			String sql="SELECT pid from partsinfo where pname="+"'"+cat+"'";
			rs=stt.executeQuery(sql);
			while(rs.next()) {
				id=rs.getInt("pid");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	return id;
	}
	
	public void getitems(ArrayList<String> getcart,String total,int id) {
		this.cartitems=getcart;
		this.uid=id;
		lblTotal.setText(total);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con =DriverManager.getConnection(url, user, Password);
			Statement stt=con.createStatement();
			ResultSet rs;
			rs=stt.executeQuery("Select * FROM userinfo where uid="+""+id);
			while(rs.next()) {
				lblAddress.setText(rs.getString("address"));
				lblPhone.setText(rs.getString("phone"));
				String name=rs.getString("first_name") +" "+ rs.getString("last_name");
				lblCustomer.setText(name);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@FXML
	public void buy(MouseEvent event) {
		try {
		PreparedStatement pr = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con =DriverManager.getConnection(url, user, Password);
		for(int i=0;i<cartitems.size();i+=4) {
			pr=con.prepareStatement("INSERT INTO orders(user_id,item_id,quantity,total_amt,mode_of_payment) values(?,?,?,?,?)");
			Double d=Double.parseDouble(cartitems.get(i+1));
			int q=d.intValue();
			pr.setInt(1, uid);
			pr.setInt(2,getid(cartitems.get(i)));
			pr.setInt(3, q);
			pr.setDouble(4, Double.parseDouble(cartitems.get(i+3)));
			pr.setString(5, s);
			pr.execute();
		}
			change();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void change() {
		try {
			AnchorPane pane=FXMLLoader.load(getClass().getClassLoader().getResource("JavaFXML/success.fxml"));
			parentroot.getChildren().setAll(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	@FXML
	void check(ActionEvent event) 
	{
		if(radioCash.isSelected()) {
		holder.setDisable(true);
		number.setDisable(true);
		mm.setDisable(true);
		yy.setDisable(true);
		s="Cash";
	}
	if(radioCard.isSelected()) {
		holder.setDisable(false);
		mm.setDisable(false);
		number.setDisable(false);
		yy.setDisable(false);
		s="Card";
	}
	
	}
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			s="cash";
			lblAddress.setEditable(false);
			ToggleGroup toggleGroup=new ToggleGroup();
			radioCard.setToggleGroup(toggleGroup);
			radioCash.setToggleGroup(toggleGroup);
			holder.setDisable(true);
			number.setDisable(true);
			mm.setDisable(true);
			yy.setDisable(true);
			toggleGroup.selectToggle(radioCash);
		
	}
	}