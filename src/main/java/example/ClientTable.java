package example;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class ClientTable {
    private static HashMap<String, Session> clientMap = new HashMap<String, Session>();

    public static Session getSession(String keyString) {
        if (clientMap.containsKey(keyString)) {
            Session session = clientMap.get(keyString);
            clientMap.remove(keyString);
            return session;
        } else {
            System.out.println("Session not found");
            return null;
        }
    }

    public static void addSession (String keyString, Session session){
        if(clientMap.containsKey(keyString)){
            System.out.println("Connection used");
        }
        else{
            clientMap.put(keyString,session);
            System.out.println("Client connected");
        }
    }
}
