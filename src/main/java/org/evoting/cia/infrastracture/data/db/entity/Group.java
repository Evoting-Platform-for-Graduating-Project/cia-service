package org.evoting.cia.infrastracture.data.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "groups")
public class Group {

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
        foreignKey = @ForeignKey(name = "fk_group_realm"))
    private Realm realm;

    @ManyToMany
    @JoinTable(name = "group_user",
            joinColumns = @JoinColumn(
                name = "group_id",
                foreignKey = @ForeignKey(name = "fk_group_group_user")),
            inverseJoinColumns = @JoinColumn(
                name = "user_id",
                foreignKey = @ForeignKey(name = "fk_user_group_user")))
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "group_role",
            joinColumns = @JoinColumn(
                name = "group_id",
                foreignKey = @ForeignKey(name = "fk_group_group_role")),
            inverseJoinColumns = @JoinColumn(
                name = "role_id",
                foreignKey = @ForeignKey(name = "fk_role_group_role")))
    private List<Role> roles;
}
