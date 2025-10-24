package org.evoting.cia.domain.indentity.user;

import org.evoting.cia.domain.user.model.Type;

public interface IUserIdentity {

    String getId();

    Type getType();
}
