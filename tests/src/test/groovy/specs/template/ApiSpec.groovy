package specs.template

import com.template.context.ApiTestContext
import groovyx.net.http.RESTClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = ApiTestContext.class)
abstract class ApiSpec extends Specification {

    @Autowired
    RESTClient restClient

    RESTClient restClient() {
        restClient
    }

}
