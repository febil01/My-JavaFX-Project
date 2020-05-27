package JavaApplication;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
//import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

public class HomeController implements Initializable {

	@FXML
	private TextField searchbox;
	@FXML
	private TilePane tileProfile;
	
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
	}

}
