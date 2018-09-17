package example;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/agent")
public class EndPointAgent {
    public EndPointAgent() {
        System.out.println("class loaded " + this.getClass());
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.printf("Session opened, id: %s%n", session.getId());
        AgentTable.addSession(session);
        System.out.printf("Agent %s connected", session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.printf("Message received. Session id: %s Message: %s%n",
                session.getId(), message);
        try {
            if (ConnectionTable.hasConnection(session.getId(), ConnectionTable.AGENTROLE)) {
                ConnectionTable.getConnection(session.getId(), ConnectionTable.AGENTROLE).getBasicRemote().sendText(message);
            } else {
                session.getBasicRemote().sendText("Not client");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

    @OnClose
    public void onClose(Session session) {
        System.out.printf("Session closed with id: %s%n", session.getId());
    }

}
