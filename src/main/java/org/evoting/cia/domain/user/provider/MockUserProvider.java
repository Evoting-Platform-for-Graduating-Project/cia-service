package org.evoting.cia.domain.user.provider;

import jakarta.inject.Singleton;
import java.util.Optional;
import org.evoting.cia.domain.user.model.IUser;

@Singleton
public class MockUserProvider implements IUserProvider {
    @Override
    public IUser getUser(String id) {
        return null;
    }

    @Override
    public Optional<IUser> findUserByUsername(String username) {
        return Optional.empty();
    }
}
