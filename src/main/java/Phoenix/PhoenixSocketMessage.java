package Phoenix;

import java.util.HashMap;
import org.json.simple.JSONObject;

public class PhoenixSocketMessage {

    private final HashMap<String, Object> attrs;

    public PhoenixSocketMessage(Builder builder) {
        this.attrs = builder.attrs;
    }

    public String toJSONString() {
        JSONObject obj = new JSONObject(this.attrs);
        return obj.toJSONString();
    }

    public static class Builder {
        private final HashMap<String, Object> attrs = new HashMap<>();

        public Builder set(String key, Object value) {
            attrs.put(key, value);
            return this;
        }

        public PhoenixSocketMessage build() {
            return new PhoenixSocketMessage(this);
        }
    }
}
