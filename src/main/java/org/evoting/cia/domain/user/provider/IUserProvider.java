package org.evoting.cia.domain.user.provider;

import java.util.Optional;
import org.evoting.cia.domain.user.model.IUser;

public interface IUserProvider {
    IUser getUser(String id);

    Optional<IUser> findUserByUsername(String username);
}
