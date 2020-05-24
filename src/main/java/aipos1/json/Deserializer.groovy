package aipos1.json

import aipos1.model.Response
import aipos1.model.implementation.ResponseImpl
import org.apache.logging.log4j.util.Strings

import java.util.stream.Collectors

trait Deserializer {

    static final KEY_TO_VALUE_DELIMITER = ":"
    static final EMPTY_LINE_CONDITION = { line -> line != Strings.EMPTY }
    static final KEY_INDEX = 0
    static final VALUE_INDEX = 1
    static final HTTP_CODE_LINE = 1
    static final HTTP_CODE_DELIMITER = " "
    static final HTTP_CODE_INDEX = 1
    static final DEFAULT_HTTP_CODE = "400"
    static final CONTENT_LENGTH_HEADER_KEY = "Content-Length"
    static final DEFAULT_CONTENT_LENGTH = 0

    Response deserialize(File file) {
        def headers = getHeaders file
        def body = getBody(file, headers.getOrDefault(CONTENT_LENGTH_HEADER_KEY, DEFAULT_CONTENT_LENGTH) as Integer)
        def httpStatusCode = getHttpStatusCode file
        new ResponseImpl(statusCode: httpStatusCode, headers: headers, body: body)
    }

    private def getHeaders(file) {
        def keyMapper = { keyToValue -> keyToValue.split(KEY_TO_VALUE_DELIMITER)[KEY_INDEX].trim() }
        def valueMapper = { keyToValue -> keyToValue.split(KEY_TO_VALUE_DELIMITER)[VALUE_INDEX].trim() }
        file.readLines().stream()
                .skip(HTTP_CODE_LINE)
                .takeWhile(EMPTY_LINE_CONDITION)
                .collect(Collectors.toMap(keyMapper, valueMapper))
    }

    private def getBody(file, contentLength) {
        def binaryRepresentation = file.bytes
        def totalSize = binaryRepresentation.length
        def offset = totalSize - contentLength
        Arrays.copyOfRange binaryRepresentation, offset, totalSize
    }

    private def getHttpStatusCode(file) {
        file.readLines().stream()
                .limit(HTTP_CODE_LINE)
                .map({ line -> line.split(HTTP_CODE_DELIMITER)[HTTP_CODE_INDEX] })
                .findFirst()
                .orElseGet({ () -> DEFAULT_HTTP_CODE }) as Integer
    }
}
