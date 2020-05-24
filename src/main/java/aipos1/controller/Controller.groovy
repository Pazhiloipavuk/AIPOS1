package aipos1.controller

import aipos1.service.HttpClientService
import aipos1.service.LoadRequestService
import aipos1.service.OutputService
import aipos1.service.RequestBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent('PostmanKiller')
class Controller {

    @Autowired
    RequestBuilder requestBuilder

    @Autowired
    LoadRequestService loadRequestService

    @Autowired
    HttpClientService clientService

    @Autowired
    OutputService outputService

    @ShellMethod(value = 'Execute request', key = 'execute')
    def execute(@ShellOption(value = ['-M', '--method']) method,
                @ShellOption(value = ['-U', '--url']) url,
                @ShellOption(value = ['-H', '--headers'], defaultValue = ShellOption.NULL) headers,
                @ShellOption(value = ['-B', '--body'], defaultValue = ShellOption.NULL) body,
                @ShellOption(value = ['-F', '--from'], defaultValue = ShellOption.NULL) from,
                @ShellOption(value = ['-T', '--to'], defaultValue = ShellOption.NULL) to) {
        try {
            def request = requestBuilder.build([method: method, url: url, headers: headers, body: body, from: from])
            executeRequest request, to
        } catch (RuntimeException exception) {
            exception.getMessage()
        }
    }

    @ShellMethod(value = 'Load and execute request from .json file', key = 'load')
    def load(@ShellOption(value = ['-P', '--path']) path,
             @ShellOption(value = ['-T', '--to'], defaultValue = ShellOption.NULL) to) {
        try {
            def request = loadRequestService.load path
            executeRequest request, to
        } catch (RuntimeException exception) {
            exception.getMessage()
        }
    }

    private def executeRequest(request, to) {
        def response = clientService.execute request
        outputService.output response, to
    }
}
