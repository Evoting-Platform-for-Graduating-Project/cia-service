package org.evoting;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;


@MicronautTest(environments = "test")
class SecurityJwtFlowTest {

    @Inject
    @Client("/")
    HttpClient client;
}