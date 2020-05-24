package aipos1.configuration

import org.jline.utils.AttributedString
import org.springframework.beans.factory.annotation.Value
import org.springframework.shell.jline.PromptProvider
import org.springframework.stereotype.Component

@Component
class ControllerConfiguration implements PromptProvider {

    @Value('${console.name}')
    private consoleName;

    @Override
    AttributedString getPrompt() {
        new AttributedString(consoleName)
    }
}