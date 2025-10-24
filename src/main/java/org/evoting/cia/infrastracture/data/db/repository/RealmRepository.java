package org.evoting.cia.infrastracture.data.db.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.Optional;
import java.util.UUID;
import org.evoting.cia.infrastracture.data.db.entity.Client;
import org.evoting.cia.infrastracture.data.db.entity.Realm;

@Repository
public interface RealmRepository extends CrudRepository<Realm, UUID> {

    Optional<Realm> findByName(String name);
}
