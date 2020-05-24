package aipos1.service.implementation

import aipos1.json.Deserializer
import aipos1.json.ResponseCacheHandler
import aipos1.json.Serializer
import aipos1.model.Request
import aipos1.model.Response
import aipos1.service.HttpClientService
import org.springframework.stereotype.Service

import java.nio.channels.SocketChannel

@Service
class HttpClientServiceImpl implements HttpClientService, Serializer, Deserializer, ResponseCacheHandler {

    static final PORT = 80

    @Override
    Response execute(Request request) {
        try (def socketChannel = SocketChannel.open(new InetSocketAddress(request.uri.getHost(), PORT))) {
            send socketChannel, request
            def cache = cache socketChannel
            deserialize cache
        }
    }

    private def send(socketChannel, request) {
        def serializedRequest = serialize request
        while (serializedRequest.hasRemaining()) {
            socketChannel.write serializedRequest
        }
    }
}

