package Phoenix;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;

import java.util.Map;

public class PhoenixChannel {

    private final String topic;
    private final Map<String, Object> params;
    private final PhoenixWebSocket pws;
    private CHANNEL_STATE state;

    public enum CHANNEL_STATE {
        CLOSED,
        ERRORED,
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

    public PhoenixChannel(String topic, Map<String, Object> params, PhoenixWebSocket pws) {
        this.topic = topic;
        this.params = params;
        this.pws = pws;
        PhoenixChannel self = this;

        this.pws.addListener(new WebSocketAdapter() {
            public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
                self.state = CHANNEL_STATE.ERRORED;
            }
        });
    }
}
