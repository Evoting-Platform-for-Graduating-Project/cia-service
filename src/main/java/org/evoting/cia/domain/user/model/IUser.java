package org.evoting.cia.domain.user.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.evoting.cia.domain.group.model.Group;
import org.evoting.cia.domain.role.model.Role;

public interface IUser {
    String getId();

    String getUsername();

    Type getType();

    Set<Group> getGroups();

    Set<Role> getRoles();

    default Set<Role> getAllRoles() {
        return Stream.concat(
            getRoles().stream(),
            getGroups().stream()
                .flatMap(group -> group.getRoles().stream())
        ).collect(Collectors.toSet());
    }
}
