package aipos1.validator.implementation

import aipos1.validator.Validator
import aipos1.enumeration.HttpMethod
import org.springframework.stereotype.Component

@Component
class ValidatorImpl implements Validator {

    @Override
    void validate(Map<String, String> args) {
        validateMethod args.method
        validateUrl args.url
    }

    private def validateMethod(method) {
        Arrays.asList(HttpMethod.values()).stream()
                .filter { httpMethod -> (httpMethod.toString() == method.toUpperCase()) }
                .findAny()
                .orElseThrow { new IllegalArgumentException("Method ${method} is not valid") }
    }

    private def validateUrl(url) {
        try {
            new URL(url).toURI()
        } catch (URISyntaxException | MalformedURLException exception) {
            throw new IllegalArgumentException("Url ${url} is not valid")
        }
    }
}
