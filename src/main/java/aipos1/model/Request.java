package aipos1.model;

import aipos1.enumeration.HttpMethod;

import java.net.URI;
import java.util.Map;

public interface Request {

    HttpMethod getHttpMethod();

    URI getUri();

    Map<String, String> getHeaders();

    byte[] getBody();
}
