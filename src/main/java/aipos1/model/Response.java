package aipos1.model;

import java.util.Map;

public interface Response {

    Integer getStatusCode();

    Map<String, String> getHeaders();

    byte[] getBody();
}
