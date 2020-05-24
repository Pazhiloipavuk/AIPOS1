package aipos1.service.implementation

import aipos1.model.Response
import aipos1.service.OutputService
import org.springframework.stereotype.Service

import java.util.stream.Collectors

@Service
class OutputServiceImpl implements OutputService {

    @Override
    String output(Response response, String destination) {
        destination ? writeInFile(response, new File(destination)) : writeInConsole(response)
    }

    private def writeInConsole(response) {
        "Status code: ${response.statusCode}\nHeaders:\n${translate(response)}\nBody:\n${new String(response.body)}"
    }

    private def writeInFile(response, file) {
        file << response.body
        "See file ${file}"
    }

    private def translate(response) {
        response.headers.entrySet().stream().map({ arg -> "${arg.key}:${arg.value}\n" })
                .collect(Collectors.joining())
    }
}
