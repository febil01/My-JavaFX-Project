package JavaApplication;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class paymentController implements Initializable{
@FXML
RadioButton radioCash,radioCard;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ToggleGroup toggleGroup=new ToggleGroup();
		radioCash.setToggleGroup(toggleGroup);
		radioCard.setToggleGroup(toggleGroup);
		toggleGroup.selectedToggleProperty().addListener((observable, oldVal, newVal) -> {
			 String option = ((RadioButton)toggleGroup.getSelectedToggle()).getText();
			/*
			switch (newVal.getToggleGroup().toString()) {
			case 1: {
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + newVal);
			}
		*/
		});

}
}
