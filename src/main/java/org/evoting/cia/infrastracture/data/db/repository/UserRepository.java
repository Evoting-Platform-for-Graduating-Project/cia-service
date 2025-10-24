package org.evoting.cia.infrastracture.data.db.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.Optional;
import java.util.UUID;
import org.evoting.cia.infrastracture.data.db.entity.Realm;
import org.evoting.cia.infrastracture.data.db.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByUsernameAndRealm(String username, Realm realm);
}
