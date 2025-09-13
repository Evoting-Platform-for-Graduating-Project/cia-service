package org.evoting.cia.domain.group.model;

import java.util.Set;
import lombok.Value;
import org.evoting.cia.domain.role.model.Role;

@Value
public class Group {
    String id;
    String name;
    Set<Role> roles;
}
