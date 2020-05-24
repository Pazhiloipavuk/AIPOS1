package aipos1.json

import aipos1.model.Request
import org.apache.logging.log4j.util.Strings

import java.nio.ByteBuffer
import java.util.stream.Collectors

trait Serializer {

    static final PROTOCOL = "HTTP/1.1"
    static final HEADERS_DELIMITER = "\r\n"
    static final DEFAULT_PATH = "/"

    ByteBuffer serialize(Request request) {
        def headers = new StringBuilder()
                .append("${request.httpMethod.toString()} ${getPath(request)} ${PROTOCOL}${HEADERS_DELIMITER}")
                .append(getHeaders(request))
                .append(HEADERS_DELIMITER)
                .append(HEADERS_DELIMITER)
                .toString()
                .bytes
        def capacity = headers.length + request.body.length
        ByteBuffer.allocate(capacity).put(headers).put(request.body).flip()
    }

    private def getPath(request) {
        def path = request.uri.getPath()
        path == Strings.EMPTY ? DEFAULT_PATH : path
    }

    private def getHeaders(request) {
        request.headers.entrySet().stream()
                .map({ entry -> "${entry.key}: ${entry.value}" })
                .collect(Collectors.joining(HEADERS_DELIMITER))
    }
}
