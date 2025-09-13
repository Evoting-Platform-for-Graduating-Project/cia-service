package org.evoting.cia.infrastracture.data.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
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
@ToString(exclude = "config")
public class Client {

    @Id
    @UuidGenerator
    @JdbcTypeCode(SqlTypes.UUID)
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private java.util.UUID id;

    @ManyToOne
    @JoinColumn(
        name = "realm_id",
        foreignKey = @ForeignKey(name = "fk_client_realm")
    )
    private Realm realm;

    @OneToMany(mappedBy = "client")
    private List<Config> config;

    @Column(nullable = false)
    private String hosts;

    @Column(nullable = false)
    private String callbackUrl;
}
