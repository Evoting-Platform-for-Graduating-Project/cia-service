package org.evoting.cia.infrastracture.data.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
import org.evoting.cia.domain.user.model.Type;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "users",
    indexes = {
        @Index(name = "ix_user_realm_id", columnList = "realm_id, id"),
    })
public class User {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private java.util.UUID id;

    @ManyToOne
    @JoinColumn(
        name = "realm_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_user_realm")
    )
    private Realm realm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(unique = true, nullable = false)
    private String username;

    private String email;

    @Column(nullable = false)
    private String password;

    private String givenName;

    private String familyName;

    @ManyToMany
    @JoinTable(name = "user_device",
        joinColumns = @JoinColumn(
            name = "user_id",
            foreignKey = @ForeignKey(name = "fk_user_user_device")),
        inverseJoinColumns = @JoinColumn(
            name = "device_id",
            foreignKey = @ForeignKey(name = "fk_device_user_device")))
    private List<Device> devices;
}