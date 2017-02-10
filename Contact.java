package friendochat;


import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Contact {
	
	private static ArrayList<Contact> contactList = new ArrayList<Contact>();
	
	public ContactSlot contactSlot;
	public ChatPane chatPane;
	public Log log;
	private String contactJID;
	private String contactName;
	private int id;
	private String threadId;
	private int focusHeight = 50;
	private int focusWidth = 200;
	private int chatBubbleHeight = 50;
	private boolean hasFocus = false;
	private boolean isMUC = false;
	
	public Contact(String contactJID, int id) {
		
		this.contactJID = contactJID;
		contactName = contactJID.replace("@" + Settings.getHostName(), "");
		contactSlot = new ContactSlot();
		this.id = id;
		chatPane = new ChatPane();
		log = new Log();
		
		
	}
	
	public static ArrayList<Contact> getContactList() {
		
		return contactList;
	}
	
	public void setIsMUC(boolean isMUC) {
		
		this.isMUC = isMUC;
	}
	
	public boolean getIsMUC() {
		
		return isMUC;
	}
	
	public void setThreadId(String threadId) {
		
		this.threadId = threadId;
	}
	
	public String getThreadId() {
		
		return threadId;
	}
	
	
	public Text getContactSlotText() {
		
		return contactSlot.text;
	}
	
	public Rectangle getContactSlotFocus() {
		
		return contactSlot.focus;
	}
	
	public Pane getContactSlotPane() {
		
		return contactSlot.pane;
	}
	
	public GridPane getChatGrid() {
		
		return chatPane.chatGrid;
	}
	
	public String getContactJID() {
		
		return contactJID;
	}
	
	public String getContactName() {
		
		return contactName;
	}
	
	public void setContactJID(String contactName) {
		
		this.contactJID = contactName;
	}
	
	public boolean hasFocus() {
		
		return hasFocus;
	}
	
	public void setFocus(boolean hasFocus) {
		
		this.hasFocus = hasFocus;
	}
	
	public int getId() {
		
		return id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
	
	public void setFocusColor(Color color) {
		
		contactSlot.focus.setFill(color);
	}
	
	//////////////////
	// Contact Slot //
	//////////////////
	public class ContactSlot {
		
		private Pane pane;
		private Rectangle focus;
		private Text text;
		
		private ContactSlot() {
			
			initFocusRectangle();
			initContactText();
			initContactpane();
		}
		
		private void initFocusRectangle() {
			
			focus = new Rectangle(focusWidth, focusHeight, Color.TRANSPARENT);
			focus.setOpacity(Settings.UI.getFocusOpacity());
			focus.setArcWidth(Settings.UI.getFocusArcwidth());
			focus.setArcHeight(Settings.UI.getFocusArcheight());
			focus.setId(String.valueOf(id));
		}
		
		private void initContactText() {
			
			text = new Text(contactName);
			text.setFont(Settings.Fonts.getContactSlotFont());
			text.setId(String.valueOf(id));
		}
		
		private void initContactpane() {
			
			pane = new Pane();
			pane.setId(String.valueOf(id));
		}
	}
	
	///////////////
	// Chat Pane //
	///////////////
	public class ChatPane {
		
		private GridPane chatGrid = new GridPane();
		private Label contactLabel = new Label();
		public FontMetrics textFieldMetrics;
		
		private ChatPane () {
			
			initChatGrid();
		}
		
		public GridPane getChatGrid() {
			
			return chatGrid;
		}
		
		public Label getContactLabel() {
			
			return contactLabel;
		}
		
		
		public void addLine() {
			
			Text chatText = new Text();
			Label timeLabel = new Label();
			Rectangle chatBubble = new Rectangle();
			chatText = setChatText(log.getCurrentLine(1), chatText);
			timeLabel = setTimeLabel(log.getCurrentLine(2), timeLabel);
			chatBubble = setChatBubble(chatBubble, chatBubbleHeight, Settings.UI.getChatBubbleOpacity(), Settings.UI.getChatBubbleArchWidth(), Settings.UI.getChatBubbleArchHeight(), Settings.Colors.getChatBubbleColor(), log.getCurrentLine(1));
			if (log.getCurrentLine(0).contentEquals(Settings.getUserName()))
				try {
					drawUserLine(chatText, timeLabel, chatBubble);
				} catch (Exception e) {
					e.printStackTrace();
				}
			else
				try {
					drawContactLine(chatText, timeLabel, chatBubble);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
		}
		
		private void initChatGrid() {
			
			chatGrid.setId(String.valueOf(id));
			chatGrid.setHgap(5);
			chatGrid.setVgap(5);
			ColumnConstraints c1 = new ColumnConstraints();
			c1.setMinWidth(100);
			c1.setPrefWidth(100);
			c1.setHgrow(Priority.SOMETIMES);
			chatGrid.getColumnConstraints().add(c1);
			ColumnConstraints c2 = new ColumnConstraints();
			c2.setMinWidth(300);
			c2.setPrefWidth(650);
			c2.setHgrow(Priority.SOMETIMES);
			chatGrid.getColumnConstraints().add(c2);
			ColumnConstraints c3 = new ColumnConstraints();
			c3.setMinWidth(100);
			c3.setPrefWidth(100);
			c3.setHgrow(Priority.SOMETIMES);
			chatGrid.getColumnConstraints().add(c3);
			RowConstraints r1 = new RowConstraints();
			r1.setVgrow(Priority.SOMETIMES);
			chatGrid.getRowConstraints().add(r1);
		}
		
		private Label setContactLabel(Label label) {
			
			GridPane.setHalignment(contactLabel, HPos.RIGHT);
			label.setPadding(new Insets(0, 10, 0, 0));
			label.setAlignment(Pos.CENTER_RIGHT);
			label.setFont(Settings.Fonts.getLabelFont());
			label.setText(log.getCurrentLine(0));
			
			return label;
		}
		
		
		
		private void drawUserLine(Text chatText, Label timeLabel, Rectangle chatBubble) throws Exception {
			
			int row = getNumberOfRows(chatGrid);
			GridPane.setHalignment(chatText, HPos.RIGHT);
			GridPane.setHalignment(chatBubble, HPos.RIGHT);
			chatGrid.add(chatBubble,  1,  row);
			chatGrid.add(chatText, 1, row);
			chatGrid.add(timeLabel, 2, row);
		}
		
		private void drawContactLine(Text chatText, Label timeLabel, Rectangle chatBubble) throws Exception {
			
			
			Label buddyLabel = new Label();
			buddyLabel = setContactLabel(buddyLabel);
			int row = getNumberOfRows(chatGrid);
			GridPane.setHalignment(buddyLabel, HPos.RIGHT);
			GridPane.setValignment(buddyLabel, VPos.CENTER);
			chatGrid.add(buddyLabel, 0, row);
			chatGrid.add(chatBubble,  1,  row);
			chatGrid.add(chatText, 1, row);
			chatGrid.add(timeLabel, 2, row);
		}
		
		private Text setChatText(String string, Text text) {
			
			text.setText(string);
			text.setFont(Settings.Fonts.getChatFont());
			text.setCursor(Cursor.TEXT);
			
			return text;
		}
		
		private Label setTimeLabel(String string, Label label) {
			
			label.setPadding(new Insets(0, 0, 0, 10));
			label.setText(string);
			label.setFont(Settings.Fonts.getTimeFont());
			
			return label;
		}
		
		private Rectangle setChatBubble(Rectangle rect, int height, double opacity, int arcwidth, int archeight, Color color, String string) {
			
			textFieldMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(Settings.Fonts.getChatFont());
			rect.setWidth(textFieldMetrics.computeStringWidth(string));
			rect.setHeight(height);
			rect.setFill(color);
			rect.setOpacity(opacity);
			rect.setArcWidth(arcwidth);
			rect.setArcHeight(archeight);
			
			return rect;
		}
		
		private Integer getNumberOfRows(GridPane gridPane) throws Exception {
			
			Method method = gridPane.getClass().getDeclaredMethod("getNumberOfRows");
			method.setAccessible(true);
			Integer rows = (Integer) method.invoke(gridPane);
			
			return rows;
		}
	}
	
	/////////
	// Log //
	/////////
	public class Log {
		
		List<String[]> log = new ArrayList<String[]>();
		private String[] buffer = new String[3];
		private int index = -1;
		public SimpleDateFormat timeFormat = new SimpleDateFormat ("hh:mm:ss");
		private String time;
		
		public Log () {
			
		}
		
		public void addChatLine(boolean isUser, String message) {
			
			if(message != "") {
				getTime();
				if (isUser)
					buffer[0] = Settings.getUserName();
				else
					buffer[0] = contactName;
				buffer[1] = message;
				buffer[2] = time;
				log.add(buffer);
				index++;
			}
		}
		
		public void addMUCLine(String contact, String message) {
			
			if(message != "") {
				getTime();
				buffer[0] = contact.replace(contactJID + "@conference." + Settings.getHostName() + "/", "");
				buffer[1] = message;
				buffer[2] = time;
				System.out.println(buffer[0] + ": " + buffer[1] + " " + buffer[2]);
				log.add(buffer);
				index++;
			}
		}
		
		public int getCurrentIndex () {
			
			return index;
		}
		
		public void getTime() {
			
			Date date = new Date();
			time = timeFormat.format(date);
		}
		
		public String getCurrentLine(int index) {
			
			return buffer[index];
		}
	}
	
}
