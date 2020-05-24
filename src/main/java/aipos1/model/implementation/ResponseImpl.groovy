package aipos1.model.implementation

import aipos1.model.Response

class ResponseImpl implements Response {
    Integer statusCode

    Map<String, String> headers

    byte[] body
}
