package example;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;

public class ConnectionTable {
    private static HashMap<String, Session> connectionMapAgent = new HashMap<String, Session>();

    private static HashMap<String, Session> connectionMapClient = new HashMap<String, Session>();

    public static final int AGENTROLE = 1;

    public static final int CLIENTROLE = 0;

    public static Session getConnection(String keyString, int role) {
        if (role == AGENTROLE) {
            if (connectionMapAgent.containsKey(keyString)) {
                return connectionMapAgent.get(keyString);
            } else {
                System.out.println("Session not found");
                return null;
            }
        } else {
            if (connectionMapClient.containsKey(keyString)) {
                return connectionMapClient.get(keyString);
            } else {
                System.out.println("Session not found");
                return null;
            }
        }
    }

    public static boolean addConnection(String sessionClientId) {
        if (connectionMapAgent.containsKey(sessionClientId)) {
            System.out.printf("Connection %s created", sessionClientId);
            return true;
        } else {
            Session clientSession = ClientTable.getSession(sessionClientId);
            if (AgentTable.notAvailableAgent()) {
                try {
                    clientSession.getBasicRemote().sendText("not available agent");
                    return false;
                } catch (IOException e) {
                    System.out.println(e);
                }finally {
                    return false;
                }
            } else {
                Session agentSession = AgentTable.getSession();
                connectionMapAgent.put(agentSession.getId(), clientSession);
                connectionMapClient.put(sessionClientId, agentSession);
                return true;
            }
        }
    }


    public static boolean hasConnection(String keyString,int role) {
        if(role==CLIENTROLE){
            return connectionMapClient.containsKey(keyString);
        }
        else {
            return connectionMapAgent.containsKey(keyString);
        }
    }
}
