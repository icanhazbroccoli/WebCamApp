package Phoenix;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;
import java.net.URI;

public class PhoenixWebSocket {
    private final WebSocket ws;

    public PhoenixWebSocket(WebSocketFactory factory, URI uri) throws IOException {
        this.ws = factory.createSocket(uri);
    }

    PhoenixWebSocket addListener(WebSocketAdapter listener) {
        this.ws.addListener(listener);
        return this;
    }

    PhoenixWebSocket connect() throws WebSocketException {
        this.ws.connect();
        return this;
    }

    PhoenixWebSocket sendMessage(PhoenixSocketMessage message) {
        this.ws.sendText(message.toJSONString());
        return this;
    }
}
