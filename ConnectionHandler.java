package friendochat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChatManager;

public class ConnectionHandler {

	private static ConnectionHandler connectionHandler = new ConnectionHandler();
	private static ChatManager chatManager;
	private static MultiUserChatManager mucManager;
	
	private AbstractXMPPConnection conn;
	private XMPPTCPConnectionConfiguration config;
	private Roster roster;
	private ArrayList<String> contactJIDs = new ArrayList<String>();
	
	
	public boolean login(String username, String password) {
		
		conn = new XMPPTCPConnection(username, password);
		
		config = XMPPTCPConnectionConfiguration.builder()
				.setHost(Settings.getDomainName())
				.setServiceName(Settings.getHostName())
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setDebuggerEnabled(false)
                .setUsernameAndPassword(username, password)
                .build();
				
        conn = new XMPPTCPConnection(config);
        
        try {
            System.out.println("connecting");
            conn.setPacketReplyTimeout(1000);
            conn.connect();
            System.out.println("connected");
            conn.login();
            if (conn.isAuthenticated())
            	System.out.println("Logged In");
        } catch (SmackException | IOException | XMPPException e) {
            e.printStackTrace();
            return false;
        }
        chatManager = ChatManager.getInstanceFor(conn);
		mucManager = MultiUserChatManager.getInstanceFor(conn);
		return true;
	}
	
	
	public void updateRoster() throws NotLoggedInException, NotConnectedException, InterruptedException {
		
		roster = Roster.getInstanceFor(conn);
		Thread.sleep(1000);
        
        if (!roster.isLoaded()) 
            roster.reloadAndWait();

        Collection <RosterEntry> entries = roster.getEntries();
        System.out.println(entries.size());
        
        for (RosterEntry entry : entries) {
        	
        	contactJIDs.add(entry.getUser());
        }
        
        for (int i = 0; i < contactJIDs.size(); i++){
        	
        	System.out.println(contactJIDs.get(i));
        }
   
        for (RosterEntry entry : entries) {
        	Presence presence = roster.getPresence(entry.getUser());
        	System.out.println(entry);
        	System.out.println(entry.getName());
        	System.out.println("is avaialable: " + presence.isAvailable());
        	System.out.println("mode: " + presence.getMode());
        	System.out.println("status: " + presence.getStatus());
        	System.out.println("presence: " + roster.getPresence(entry.getUser()));
        }
	}
	
	
	public AbstractXMPPConnection getConnection() {	
		return conn;
	}
	public ArrayList<String> getContactJIDs() {
		return contactJIDs;
	}
	public static ConnectionHandler getConnectionHandler() {
		return connectionHandler;
	}
	public static void setConnectionHandler(ConnectionHandler connectionHandler) {
		ConnectionHandler.connectionHandler = connectionHandler;
	}
	public static ChatManager getChatManager() {
		return chatManager;
	}
	public static void setChatManager(ChatManager chatManager) {
		ConnectionHandler.chatManager = chatManager;
	}
	public static MultiUserChatManager getMucManager() {
		return mucManager;
	}
	public static void setMucManager(MultiUserChatManager mucManager) {
		ConnectionHandler.mucManager = mucManager;
	}
}