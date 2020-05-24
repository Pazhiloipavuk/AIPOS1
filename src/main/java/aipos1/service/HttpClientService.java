package aipos1.service;

import aipos1.model.Request;
import aipos1.model.Response;

public interface HttpClientService {

    Response execute(Request request);
}
