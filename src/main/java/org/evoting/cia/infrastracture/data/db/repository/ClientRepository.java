package org.evoting.cia.infrastracture.data.db.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.Optional;
import java.util.UUID;
import org.evoting.cia.infrastracture.data.db.entity.Client;

@Repository
@SuppressWarnings("MethodName")
public interface ClientRepository extends CrudRepository<Client, UUID> {

    Optional<Client> findByHostsContainsIgnoreCase(String hosts);


    Optional<Client> findByHostsContainsIgnoreCaseAndRealm_Name(String hosts, String realmName);
}
