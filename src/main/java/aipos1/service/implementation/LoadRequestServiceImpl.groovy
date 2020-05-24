package aipos1.service.implementation

import aipos1.model.Request
import aipos1.service.LoadRequestService
import aipos1.service.RequestBuilder
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LoadRequestServiceImpl implements LoadRequestService {

    @Autowired
    RequestBuilder requestBuilder

    @Autowired
    JsonSlurper jsonSlurper

    @Override
    Request load(String path) {
        def args = jsonSlurper.parse(new File(path))
        requestBuilder.build args
    }
}
