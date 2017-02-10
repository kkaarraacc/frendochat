package friendochat;

import java.lang.reflect.Method;
import java.util.ResourceBundle;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;
import java.net.URL;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ChatWindowController implements Initializable {
	
	private Label userLabel;
	
	private Label focusLabel;
	
	private int focusId = -1;
	
	@FXML
	private TextField chatInputField;
	
	@FXML
	private GridPane panesGrid;
	
	@FXML
	private GridPane contactGrid;
	
	@FXML
	private ScrollPane chatScrollPane;
	
	@FXML
	private ScrollPane contactScrollPane;
	
	
	  // There must be an initialization first if any post processing is done to the scene
	  // otherwise an event with initialize it, resulting in the very first event not responding to input
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		
	}

	  // EVENTS //	
	public void test(MouseEvent e) {
		
		Pane temp = (Pane) e.getSource();
		int id = Integer.parseInt(temp.getId());
		System.out.println(id);
		chatScrollPane.setContent(Contact.getContactList().get(id).getChatGrid());  // setContent() will straight up replace whatever was there before with the new node
		
		for (Contact  contact : Contact.getContactList()) {
			
			if (contact.hasFocus()) {
				
				contact.setFocus(false);
				contact.setFocusColor(Color.TRANSPARENT);
			}
		}
		Contact.getContactList().get(id).setFocus(true);
		Contact.getContactList().get(id).setFocusColor(Settings.Colors.getFocusColor());
		focusLabel.setText(Contact.getContactList().get(id).getContactName());
		focusId = id;
	}
	
	public void onEnter(KeyEvent e) {
		
		chatInputField.setOnKeyPressed((event) -> { 
			if(event.getCode() == KeyCode.ENTER) {
				
				if (!chatInputField.getText().contentEquals("")) {
					
					  // if the focus contact is a user chat
					if (!Contact.getContactList().get(focusId).getIsMUC()) {
						try {
							ConnectionHandler.getChatManager().getThreadChat(Contact.getContactList().get(focusId).getThreadId()).sendMessage(chatInputField.getText());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						Contact.getContactList().get(focusId).log.addChatLine(true, chatInputField.getText());
						Contact.getContactList().get(focusId).chatPane.addLine();
						System.out.println(Contact.getContactList().get(focusId).log.getCurrentLine(0) + ": " + Contact.getContactList().get(focusId).log.getCurrentLine(1) + " " + Contact.getContactList().get(focusId).log.getCurrentLine(2));
					}
					
					  // if the focus contact is a MUC
					else if (Contact.getContactList().get(focusId).getIsMUC()) {
						
						  //while(connectionHandler.getConnection().isConnected()) {   // testbitch
						try {
							ConnectionHandler.getMucManager().getMultiUserChat("returnofthebroy@conference.ubuntu").sendMessage(chatInputField.getText());
						} catch (NotConnectedException e1) {
							e1.printStackTrace();
				        }
						//}
					}
				chatInputField.setText("");
				}
			}
		}); 
	}
	

	private void drawContactSlot(Rectangle focus, Text text, Pane pane, int id) throws Exception {
		
		int row = getNumberOfRows(contactGrid);
		GridPane.setHalignment(focus, HPos.LEFT);
		GridPane.setValignment(focus, VPos.CENTER);
		GridPane.setHalignment(text, HPos.LEFT);
		GridPane.setValignment(text, VPos.CENTER);
		pane.setId(String.valueOf(id));
		contactGrid.add(focus, 0, row);
		contactGrid.add(text, 0, row);
		contactGrid.add(pane, 0, row);
	}

	private Integer getNumberOfRows(GridPane gridPane) throws Exception {
		
		Method method = gridPane.getClass().getDeclaredMethod("getNumberOfRows");
		method.setAccessible(true);
		Integer rows = (Integer) method.invoke(gridPane);
		
		return rows;
	}
	
	private void autoScrollDown() {
	
	}
	
	
	public void setContactList() {
		
		int i = 0;
		
		  // Add MUC
		Contact.getContactList().add(new Contact("returnofthebroy", i));
		Contact.getContactList().get(i).setIsMUC(true);
		i++;
		
		  // add userchats
		for (String  contactJID : ConnectionHandler.getConnectionHandler().getContactJIDs()) {
			
			Contact.getContactList().add(new Contact(contactJID, i));
			i++;
		}
	}
	
	public void drawContacts() {
		
		chatInputField.setFont(Settings.Fonts.getChatInputFont());
		  // setup user label
		userLabel = new Label(Settings.getUserName());
		userLabel.setFont(Settings.Fonts.getUserNameFont());
		GridPane.setHalignment(userLabel, HPos.LEFT);
		GridPane.setValignment(userLabel, VPos.CENTER);
		panesGrid.add(userLabel, 0, 0);
		  // setup focus label
		focusLabel = new Label();
		focusLabel.setFont(Settings.Fonts.getFocusNameFont());
		GridPane.setHalignment(focusLabel, HPos.CENTER);
		GridPane.setValignment(focusLabel, VPos.CENTER);
		panesGrid.add(focusLabel, 1, 0);
		  //get contacts and draw them
		for (Contact  contact : Contact.getContactList()) {
			
			try {
				drawContactSlot(contact.getContactSlotFocus(), contact.getContactSlotText(), contact.getContactSlotPane(), contact.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			contact.getContactSlotPane().setOnMousePressed(e -> test(e));
		}
	}
	
	  //*****************************************************************//
	  // Create a listener for each contact                              //
	  // this is where you specify what to do with the incoming messages //
	  //*****************************************************************//
	public void initChat() {
		
	
		  //* Create message listener for each contact in the list *//
		for (Contact  contact : Contact.getContactList()) {
			
			  //**************************//
			  //** USER CHAT PROCESSING **//
			if (contact.getIsMUC() == false) {
				
				  // Initialize chatScrollPane content, later do a get log method here
				chatScrollPane.setContent(contact.getChatGrid()); 
				
				  // Add listener ( "contactname@hostname" )
				Chat newChat = ConnectionHandler.getChatManager().createChat(contact.getContactJID(), new ChatMessageListener(){
					
					@Override
					public void processMessage(Chat chat, Message message) {
							
							  // Add message to log
							contact.log.addChatLine(false, message.getBody());
							
							  // Add message to the grid
							Platform.runLater(new Runnable() {   // addLine conflicts with the javafx threads
								@Override                        // so you have to wrap what you want to do in this 
								public void run() {              // runLater block of code
									contact.chatPane.addLine();	
								}
							});
							
							  //>>>>>>>>>>>>>>>>>//
							  //>>DEBUG PRINTLN<<//
							System.out.println(contact.log.getCurrentLine(0) + ": " + contact.log.getCurrentLine(1) + " " + contact.log.getCurrentLine(2));
							
							  // Highlight if contact isn't selected already
							if (focusId != contact.getId())
								contact.setFocusColor(Settings.Colors.getHighlightColor());
					}
				});
				
				contact.setThreadId(newChat.getThreadID());
			} 
			
			  //********************//
			  //** MUC PROCESSING **//
			else if (contact.getIsMUC() == true) {
				
				  // Figure out a way to handle the log shit
				
				  //* Create message listener for each multiuserchat in the list *//
				MultiUserChat newMUC = ConnectionHandler.getMucManager().getMultiUserChat("returnofthebroy@conference.ubuntu");
				
				  //* Add listener *//
				newMUC.addMessageListener(new MessageListener() {
					@Override
					public void processMessage(Message message) {
						
						  // Add message to log
						contact.log.addMUCLine(message.getFrom(), message.getBody());
						
						  // Add message to the grid
						Platform.runLater(new Runnable() {   // addLine() conflicts with the main javafx thread
							@Override                        // so you have to wrap what you want to do in this 
							public void run() {              // runLater() block of code
								contact.chatPane.addLine();	
							}
						});
						
						  // Highlight if contact isn't selected already
						if (focusId != contact.getId())
							contact.setFocusColor(Settings.Colors.getHighlightColor());
						
						  //>>DEBUG PRINTLN<<//
						System.out.println(message.getFrom() + " " + message.getBody() );
					}
				});
				
				  //* Join the MUC *//
				
				  // Set default history to zero since history is handled by logs
				DiscussionHistory history = new DiscussionHistory();
			    history.setMaxStanzas(0);
				
			      // Join MUC
				try {
					newMUC.join(Settings.getUserName(), null, history, SmackConfiguration.getDefaultPacketReplyTimeout());
				} catch (NoResponseException e) {
					e.printStackTrace();
				} catch (XMPPErrorException e) {
					e.printStackTrace();
				} catch (NotConnectedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

}