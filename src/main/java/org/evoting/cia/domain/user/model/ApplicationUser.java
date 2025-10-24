package org.evoting.cia.domain.user.model;

import java.util.Set;
import lombok.Builder;
import lombok.Value;
import org.evoting.cia.domain.group.model.Group;
import org.evoting.cia.domain.role.model.Role;

@Builder(toBuilder = true)
@Value
public class ApplicationUser implements IUser {
    String id;
    String username;
    Set<Role> roles;
    Set<Group> groups;
    Type type = Type.APPLICATION;
}
