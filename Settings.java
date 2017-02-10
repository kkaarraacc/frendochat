package friendochat;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Settings {

	private static String hostName = "ubuntu";
	private static String domainName = "24.144.8.148";
	private static String userName;
	
	public static String getDomainName() {
		return domainName;
	}
	public static void setDomainName(String domainName) {
		Settings.domainName = domainName;
	}

	public static String getHostName() {
		return hostName;
	}
	public static void setHostName(String hostName) {
		Settings.hostName = hostName;
	}

	public static String getUserName() {
		return userName;
	}
	public static void setUserName(String userName) {
		Settings.userName = userName;
	}

	/////////
	// U I //
	/////////
	public static class UI {
		
		private static double focusOpacity = 0.5;
		private static int focusArcwidth = 10;
		private static int focusArcheight = 10;
		private static double chatBubbleOpacity = 0.3;
		private static int chatBubbleArchWidth = 20;
		private static int chatBubbleArchHeight = 10;
		
		public static double getFocusOpacity() {
			return focusOpacity;
		}
		public static void setFocusOpacity(double focusOpacity) {
			UI.focusOpacity = focusOpacity;
		}
		public static int getFocusArcwidth() {
			return focusArcwidth;
		}
		public static void setFocusArcwidth(int focusArcwidth) {
			UI.focusArcwidth = focusArcwidth;
		}
		public static int getFocusArcheight() {
			return focusArcheight;
		}
		public static void setFocusArcheight(int focusArcheight) {
			UI.focusArcheight = focusArcheight;
		}
		public static double getChatBubbleOpacity() {
			return chatBubbleOpacity;
		}
		public static void setChatBubbleOpacity(double chatBubbleOpacity) {
			UI.chatBubbleOpacity = chatBubbleOpacity;
		}
		public static int getChatBubbleArchWidth() {
			return chatBubbleArchWidth;
		}
		public static void setChatBubbleArchWidth(int chatBubbleArchWidth) {
			UI.chatBubbleArchWidth = chatBubbleArchWidth;
		}
		public static int getChatBubbleArchHeight() {
			return chatBubbleArchHeight;
		}
		public static void setChatBubbleArchHeight(int chatBubbleArchHeight) {
			UI.chatBubbleArchHeight = chatBubbleArchHeight;
		}
	}
	
	
	///////////////
	// F O N T S //
	///////////////
	public static class Fonts {
		
		private static Font userNameFont = new Font("System", 22);
		private static Font focusNameFont = new Font("Sytem", 24);
		private static Font chatInputFont = new Font("System", 14);
		private static Font labelFont = new Font("System", 14);
		private static Font timeFont = new Font("System", 11);
		private static Font contactSlotFont = new Font("System", 20);
		private static Font chatFont = new Font("System", 16);
		
		public static Font getUserNameFont() {
			return userNameFont;
		}
		public static void setUserNameFont(Font userNameFont) {
			Fonts.userNameFont = userNameFont;
		}
		public static Font getFocusNameFont() {
			return focusNameFont;
		}
		public static void setFocusNameFont(Font focusNameFont) {
			Fonts.focusNameFont = focusNameFont;
		}
		public static Font getChatInputFont() {
			return chatInputFont;
		}
		public static void setChatInputFont(Font chatInputFont) {
			Fonts.chatInputFont = chatInputFont;
		}
		public static Font getLabelFont() {
			return labelFont;
		}
		public static void setLabelFont(Font labelFont) {
			Fonts.labelFont = labelFont;
		}
		public static Font getTimeFont() {
			return timeFont;
		}
		public static void setTimeFont(Font timeFont) {
			Fonts.timeFont = timeFont;
		}
		public static Font getContactSlotFont() {
			return contactSlotFont;
		}
		public static void setContactSlotFont(Font contactSlotFont) {
			Fonts.contactSlotFont = contactSlotFont;
		}
		public static Font getChatFont() {
			return chatFont;
		}
		public static void setChatFont(Font chatFont) {
			Fonts.chatFont = chatFont;
		}
	}
	
	/////////////////
	// C O L O R S //
	/////////////////
	public static class Colors {
		
		private static Color highlightColor = Color.BLUEVIOLET;
		private static Color focusColor = Color.PLUM;
		private static Color chatBubbleColor = Color.PLUM;
		
		public static Color getFocusColor() {
			return focusColor;
		}
		public static void setFocusColor(Color focusColor) {
			Colors.focusColor = focusColor;
		}
		public static Color getHighlightColor() {
			return highlightColor;
		}
		public static void setHighlightColor(Color highlightColor) {
			Colors.highlightColor = highlightColor;
		}
		public static Color getChatBubbleColor() {
			return chatBubbleColor;
		}
		public static void setChatBubbleColor(Color chatBubbleColor) {
			Colors.chatBubbleColor = chatBubbleColor;
		}
	}
	
	///////////////
	// D E B U G //
	///////////////
	public static class Debug {
		
	}
}
