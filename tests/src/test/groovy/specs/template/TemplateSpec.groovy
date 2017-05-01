package specs.template

class TemplateSpec extends ApiSpec {

    def "Template test" () {
        when: "I have some initial state"

        then: "I can do something"
        print restClient
    }
}
