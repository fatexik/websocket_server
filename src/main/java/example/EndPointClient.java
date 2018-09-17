package example;


import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/client")
public class EndPointClient {

    public EndPointClient() {
        System.out.println("class loaded " + this.getClass());
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.printf("Session opened, id: %s%n", session.getId());
        try {
            session.getBasicRemote().sendText("Hi there, we are successfully connected.");
            ClientTable.addSession(session.getId(), session);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.printf("Message received. Session id: %s Message: %s%n",
                session.getId(), message);

        try {
            session.getBasicRemote().sendText(String.format("We received your message: %s%n", message));
            if (ConnectionTable.hasConnection(session.getId(), ConnectionTable.CLIENTROLE)) {
                ConnectionTable.getConnection(session.getId(), ConnectionTable.CLIENTROLE).getBasicRemote().sendText(message);
            } else {
                if (ConnectionTable.addConnection(session.getId())) {
                    ConnectionTable.getConnection(session.getId(), ConnectionTable.CLIENTROLE).getBasicRemote().sendText(message);
                }
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