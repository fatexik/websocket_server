package example;

import javax.websocket.Session;
import java.util.LinkedList;

public class AgentTable {
    private static LinkedList<Session> agentMap = new LinkedList<Session>();

    public static Session getSession() {
        if (!agentMap.isEmpty()) {
            Session session = agentMap.getLast();
            agentMap.removeLast();
            return session;
        } else {
            System.out.println("Not available agent");
            return null;
        }
    }

    public static void addSession(Session session) {
        if (agentMap.contains(session)) {
            System.out.println("Connection used");
        } else {
            agentMap.addFirst(session);
            System.out.println("Connection created");
        }
    }

    public static boolean notAvailableAgent(){

        return agentMap.isEmpty();
    }
}
