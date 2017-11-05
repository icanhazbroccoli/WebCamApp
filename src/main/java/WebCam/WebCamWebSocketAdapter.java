package WebCam;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;

import java.util.List;
import java.util.Map;

public class WebCamWebSocketAdapter extends WebSocketAdapter {
    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        System.out.println("Socket successfully connected");
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        System.err.println(
                String.format("Socket disconnected, closed by server: %b", closedByServer));
    }

    @Override
    public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
        System.err.println("Connect error: " + exception.getMessage());
    }

    @Override
    public void onTextMessage(WebSocket websocket, String message) throws Exception {
        System.out.println("Received a text message: " + message);
    }

    @Override
    public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
        System.out.println("A binary message received");
    }
}
