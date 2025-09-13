package org.evoting.cia.infrastracture.data.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Config {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private java.util.UUID id;

    @ManyToOne
    @JoinColumn(
        name = "realm_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_config_realm")
    )
    private Realm realm;

    @ManyToOne
    @JoinColumn(
        name = "client_id",
        foreignKey = @ForeignKey(name = "fk_config_client")

    )
    private Client client;

    @Column(nullable = false)
    private String key;

    @Enumerated(EnumType.STRING)
    private ConfigType configType;

    private String stringValue;

    private Boolean boolValue;

    private Integer intValue;


}
