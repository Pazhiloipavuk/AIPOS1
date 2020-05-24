package aipos1.service.implementation

import aipos1.model.Request
import aipos1.enumeration.HttpMethod
import aipos1.model.implementation.RequestImpl
import aipos1.service.RequestBuilder
import aipos1.validator.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

@Service
class RequestBuilderImpl implements RequestBuilder {

    static final HEADERS_DELIMITER = ",,,"
    static final KEY_TO_VALUE_DELIMITER = ":"
    static final HEADERS_WHITESPACE = "_"
    static final HEADERS_WHITESPACE_DELIMITER = " "
    static final KEY_INDEX = 0
    static final VALUE_INDEX = 1
    static final DEFAULT_BODY_SIZE = 0
    static final EMPTY_BODY_SIZE = 0
    static final HOST_HEADER_KEY = "Host"
    static final CONNECTION_HEADER_KEY = "Connection"
    static final CONNECTION_HEADER_VALUE = "close"
    static final CONTENT_LENGTH_HEADER_KEY = "Content-Length"

    @Autowired
    Validator validator

    @Override
    Request build(Map<String, String> args) {
        validator.validate args
        def method = resolveMethod args
        def uri = resolveUrl args
        def headers = resolveHeaders args
        def body = resolveBody args
        putHeaders headers, method, uri, body
        new RequestImpl(httpMethod: method, uri: uri, headers: headers, body: body)
    }

    private def resolveMethod(args) {
        HttpMethod.valueOf(args.method.toUpperCase())
    }

    private def resolveUrl(args) {
        URI.create args.url
    }

    private def resolveHeaders(args) {
        def keyMapper = { keyToValue -> keyToValue.split(KEY_TO_VALUE_DELIMITER)[KEY_INDEX].trim() }
        def valueMapper = { keyToValue -> keyToValue.split(KEY_TO_VALUE_DELIMITER)[VALUE_INDEX].trim() }
        def mergeFunction = { arg1, arg2 -> "${arg1},${arg2}" }
        args.headers ? Arrays.asList(args.headers.replaceAll(HEADERS_WHITESPACE, HEADERS_WHITESPACE_DELIMITER)
                .split(HEADERS_DELIMITER)).stream()
                .collect(Collectors.toMap(keyMapper, valueMapper, mergeFunction)) : [:]
    }

    private def putHeaders(headers, method, uri, body) {
        headers << [(HOST_HEADER_KEY) : (uri.host.toString())]
        headers << [(CONNECTION_HEADER_KEY) : CONNECTION_HEADER_VALUE]
        def bodyLength = body.length
        if (bodyLength > EMPTY_BODY_SIZE && method.hasRequestBody()) {
            headers << [(CONTENT_LENGTH_HEADER_KEY) : (Integer.toString(bodyLength))]
        }
    }

    private def resolveBody(args) {
        def bodyFromConsole = args.body?.getBytes()
        def bodyFromFile = args.from ? Files.readAllBytes(Paths.get(args.from)) : new byte[DEFAULT_BODY_SIZE]
        bodyFromConsole ? bodyFromConsole : bodyFromFile
    }
}
