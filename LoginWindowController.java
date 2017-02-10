package friendochat;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginWindowController implements Initializable {
	
	@FXML
	private AnchorPane loginWindow;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Text exceptionText;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public void onLoginButton(MouseEvent e) throws Exception {
		
		validateLogin();
	}
	
	public void onEnterKeyPassword(KeyEvent e) {
		
		passwordField.setOnKeyPressed((event) -> { 
			if(event.getCode() == KeyCode.ENTER) {
				
				try {
					validateLogin();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	private void validateLogin() throws Exception {
		
		exceptionText.setVisible(false); // set animation timer on this later to make it work on time
		
		if (!ConnectionHandler.getConnectionHandler().login(usernameField.getText(), passwordField.getText())) {
			exceptionText.setVisible(true);
		} else {
			Settings.setUserName(usernameField.getText());
			loginWindow.getScene().getWindow().hide();
			startChatWindow();
		}
	}
	
	private void startChatWindow() throws Exception {
		
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatWindow.fxml"));
		Parent root = loader.load();
		
		ChatWindowController chatWindowController = loader.getController();
		ConnectionHandler.getConnectionHandler().updateRoster();
		Settings.setUserName(usernameField.getText());
		// completely do all of the drawing and shit here
		// just get the componenets with getters
		// the controller shouldn't be littered with shit
		
		
		chatWindowController.setContactList();
		chatWindowController.drawContacts();
		chatWindowController.initChat();
		
		stage.setScene(new Scene(root));
		stage.show();
	}
}
