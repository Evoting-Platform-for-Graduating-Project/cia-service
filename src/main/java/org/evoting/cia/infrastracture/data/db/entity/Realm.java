package org.evoting.cia.infrastracture.data.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    indexes = {
        @Index(name = "ix_realm_name", columnList = "name"),
    }
)
public class Realm {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private java.util.UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "realm")
    private List<User> users;

    @OneToMany(mappedBy = "realm")
    private List<Group> groups;

    @OneToMany(mappedBy = "realm")
    private List<Role> role;

    @OneToMany(mappedBy = "realm")
    private List<Permission> permission;

    @OneToMany(mappedBy = "realm")
    private List<Client> clients;

    @OneToMany(mappedBy = "realm")
    private List<Config> config;
}
