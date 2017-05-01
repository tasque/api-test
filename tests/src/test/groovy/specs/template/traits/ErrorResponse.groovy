package specs.template.traits

import groovyx.net.http.RESTClient

trait ErrorResponse {
    abstract RESTClient restClient()

    def origFailureHandler

    def setup() {
        // need to install custom failure handler which passes the response through instead of throwing an exception
        origFailureHandler = restClient().handler.failure
        restClient().handler.failure = { resp, data -> resp.setData(data); return resp}
    }

    def cleanup() {
        restClient().handler.failure = origFailureHandler
    }
}