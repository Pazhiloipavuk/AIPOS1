package aipos1.configuration

import groovy.json.JsonSlurper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HttpClientConfiguration {

    @Bean
    JsonSlurper JsonSlurper() {
        new JsonSlurper()
    }
}
