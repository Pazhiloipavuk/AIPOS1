package aipos1.service;

import aipos1.model.Request;

import java.util.Map;

public interface RequestBuilder {

    Request build(Map<String, String> args);
}
