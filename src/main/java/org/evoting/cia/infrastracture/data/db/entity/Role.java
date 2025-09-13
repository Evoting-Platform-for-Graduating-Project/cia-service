package org.evoting.cia.infrastracture.data.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
public class Role {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private java.util.UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(
        name = "realm_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_role_realm")
    )
    private Realm realm;

    @ManyToMany
    @JoinTable(name = "role_permission",
            joinColumns = @JoinColumn(
                name = "role_id",
                foreignKey = @ForeignKey(name = "fk_role_role_permission")),
            inverseJoinColumns = @JoinColumn(
                name = "permission_id",
                foreignKey = @ForeignKey(name = "fk_permission_role_permission")))
    private List<Permission> permissions;
}
