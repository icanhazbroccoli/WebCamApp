package Phoenix;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PhoenixSocketMessage {

    private final HashMap<String, Object> attrs;

    public PhoenixSocketMessage(Builder builder) {
        this.attrs = builder.attrs;
    }

    public String toJSONString() {
        JSONObject obj = new JSONObject(this.attrs);
        return obj.toJSONString();
    }

    public Object getDeep(String keyStr, Object obj) {
        String[] keys = keyStr.split(".");
        return recGetInObj((JSONObject) obj, Arrays.asList(keys).listIterator());
    }

    private Object recGetInObj(JSONObject obj, ListIterator<String> keysIt) {
        if (keysIt.hasNext()) {
            Object subObj = obj.get(keysIt.next());
            if (subObj == null) {
                return null;
            } else {
                return recGetInObj((JSONObject)subObj, keysIt);
            }
        } else {
            return obj;
        }
    }

    public static PhoenixSocketMessage fromJSON(String jsonMessage) {
        Builder builder = new Builder();
        JSONObject jsonObject = (JSONObject)JSONValue.parse(jsonMessage);
        for (Object key : jsonObject.keySet()) {
            builder.set((String)key, jsonObject.get(key));
        }
        return builder.build();
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
