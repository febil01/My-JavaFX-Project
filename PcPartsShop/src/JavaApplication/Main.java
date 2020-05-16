package JavaApplication;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
	private double xOffset = 0;
	private double yOffset = 0;
	public static void main(String[] args)
	{
	launch(args);
	}

	
@Override
public void start(Stage stage) throws Exception{

    
Parent rootLogin=FXMLLoader.load(getClass().getResource("/javaFXML/login.fxml"));
Parent rootSignup=FXMLLoader.load(getClass().getResource("/javaFXML/signup.fxml"));
Parent rootHome=FXMLLoader.load(getClass().getResource("/javaFXML/Home.fxml"));

Scene signup=new Scene(rootSignup);
Scene login=new Scene(rootLogin);
Scene home=new Scene(rootHome);

rootLogin.requestFocus();
rootSignup.requestFocus();
rootHome.requestFocus();

Stage loginStage=new Stage();
Stage signupStage=new Stage();
Stage homeStage=new Stage();

loginStage.initStyle(StageStyle.UNDECORATED);
signupStage.initStyle(StageStyle.UNDECORATED);

loginStage.setScene(login);
signupStage.setScene(signup);
homeStage.setScene(home);

homeStage.setMaximized(true);

login.setOnMousePressed(new EventHandler<MouseEvent>() {
@Override
public void handle(MouseEvent event) {
   xOffset = event.getSceneX();
   yOffset = event.getSceneY();
}
});

login.setOnMouseDragged(new EventHandler<MouseEvent>() {
@Override
public void handle(MouseEvent event) {
   loginStage.setX(event.getScreenX() - xOffset);
   loginStage.setY(event.getScreenY() - yOffset);
}
});

signup.setOnMousePressed(new EventHandler<MouseEvent>() {
@Override
public void handle(MouseEvent event) {
   xOffset = event.getSceneX();
   yOffset = event.getSceneY();
}
});

signup.setOnMouseDragged(new EventHandler<MouseEvent>() {
@Override
public void handle(MouseEvent event) {
   signupStage.setX(event.getScreenX() - xOffset);
   signupStage.setY(event.getScreenY() - yOffset);
}
});

JavaApplication.loginController.getStage(signupStage,rootLogin);
JavaApplication.signupController.setstage(loginStage,rootSignup);
loginStage.show();
homeStage.show();
}

}
