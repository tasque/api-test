package com.template.context

import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import org.apache.http.HttpEntity
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.codehaus.groovy.runtime.MethodClosure
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.ContextConfiguration

import static java.lang.System.getenv

@ContextConfiguration
class ApiTestContext {
    static final String VM_ADDRESS = "VM_ADDRESS"

    @Bean
    PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        def propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer()
        def resources = [
                new ClassPathResource("conf/environments/test-client.properties"),
                new ClassPathResource("spec/spec.properties")
        ]
        propertyPlaceholderConfigurer.locations = resources
        propertyPlaceholderConfigurer.ignoreResourceNotFound = false
        propertyPlaceholderConfigurer
    }

    @Bean
    RESTClient restClient(@Value('${api.username}') String username, @Value('${api.password}') String password) {

        def client = new RESTClient(getenv(VM_ADDRESS) ?: "http://localhost:8181")
        client.ignoreSSLIssues()


        client.setContentType(ContentType.JSON)

        // support single-file multipart uploads
        client.encoder.putAt("multipart/form-data", new MethodClosure(this, 'encodeMultiPart'))

        client
    }


    HttpEntity encodeMultiPart(File file) {
        MultipartEntityBuilder.create()
                .addBinaryBody(
                "file",
                file,
                org.apache.http.entity.ContentType.MULTIPART_FORM_DATA,
                file.name
        ).build()
    }


}
