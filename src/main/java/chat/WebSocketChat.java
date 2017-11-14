/*
 * Licencia pendiente, para cambiar la licencia ir a -> Tools | Templates
 */

package chat;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Ilich Arredondo
 */
@ServerEndpoint("/chat")
public class WebSocketChat {
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void onMessage(Session s, String message) throws IOException {
        String userId =  s.getId().substring(0,6);
        for(Session peer : peers) {
            peer.getBasicRemote().sendText(userId + ": " + message);
        }
        //return message;
    }

    @OnOpen
    public void onOpen(Session peer) {
        peers.add(peer);
	System.out.println("Se conectio un nuevo cliente.");
    }

    @OnClose
    public void onClose(Session peer) {
        peers.remove(peer);
    }

    @OnError
    public void onError(Throwable t) {
    }

}
