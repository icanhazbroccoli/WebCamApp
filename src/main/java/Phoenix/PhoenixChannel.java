package Phoenix;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.*;

public class PhoenixChannel {

    private final String topic;
    private String ref;
    private final Map<String, Object> params;
    private final PhoenixWebSocket websocket;
    private final Map<CHANNEL_EVENT, List<PhoenixSocketMessageHandler>> handlers;
    private CHANNEL_STATE state;

    public enum CHANNEL_STATE {
        CLOSED,
        ERRORRED,
        JOINED,
        JOINING,
        LEAVING
    }

    public enum CHANNEL_EVENT {
        CLOSE,
        ERROR,
        JOIN,
        REPLY,
        LEAVE
    }

    public PhoenixChannel(String topic, Map<String, Object> params, PhoenixWebSocket websocket) {
        this.topic = topic;
        this.params = params;
        this.websocket = websocket;
        this.handlers = new HashMap<>();
        PhoenixChannel self = this;

        this.websocket.addListener(new WebSocketAdapter() {
            public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
                self.state = CHANNEL_STATE.ERRORRED;
            }

            public void onTextMessage(WebSocket websocket, String message) throws Exception {
                self.dispatchMessage(websocket, message);
            }
        });
    }

    synchronized public PhoenixChannel on(CHANNEL_EVENT event, PhoenixSocketMessageHandler handler) {
        this.handlers
                .putIfAbsent(event, new ArrayList<>())
                .add(handler);
        return this;
    }

    synchronized public PhoenixChannel join() {
        System.out.println("Sending join message");
        websocket.sendMessage(
                new PhoenixSocketMessage.Builder()
                        .set("event", "phx_join")
                        .set("topic", this.topic)
                        .set("payload", new JSONObject())
                        .set("ref", this.ref)
                        .build()
        );
        return this;
    }

    synchronized private void dispatchMessage(WebSocket websocket, String jsonMessage) throws Exception {
        PhoenixSocketMessage message = PhoenixSocketMessage.fromJSON(jsonMessage);
        // TODO
    }
}
