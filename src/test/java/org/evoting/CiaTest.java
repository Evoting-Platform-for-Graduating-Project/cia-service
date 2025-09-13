package org.evoting;

import io.micronaut.context.annotation.Replaces;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.evoting.cia.infrastracture.data.db.repository.ClientRepository;
import org.evoting.cia.infrastracture.data.db.repository.RealmRepository;
import org.evoting.cia.infrastracture.data.db.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest(environments = "test")
class CiaTest {

    @Inject
    EmbeddedApplication<?> application;

    @MockBean
    @Replaces(ClientRepository.class)
    ClientRepository clientRepository;

    @MockBean
    @Replaces(UserRepository.class)
    UserRepository userRepository;

    @MockBean
    @Replaces(RealmRepository.class)
    RealmRepository realmRepository;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

}
