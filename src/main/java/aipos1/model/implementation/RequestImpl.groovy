package aipos1.model.implementation

import aipos1.enumeration.HttpMethod
import aipos1.model.Request

class RequestImpl implements Request {

    HttpMethod httpMethod

    URI uri

    Map<String, String> headers

    byte[] body
}

