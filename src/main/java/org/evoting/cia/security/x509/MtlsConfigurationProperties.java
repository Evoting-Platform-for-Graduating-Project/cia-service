package org.evoting.cia.security.x509;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.Introspected;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Configuration for mutual TLS (X.509) authentication mapping.
 *
 * Library-friendly: depends only on Micronaut config annotations.
 */
@Introspected
@ConfigurationProperties("security.mtls")
public class MtlsConfigurationProperties {

    /**
     * Default roles to assign to principals authenticated via client certificate.
     */
    private List<String> roles = new ArrayList<>(List.of("ROLE_SERVICE"));

    public List<String> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    public void setRoles(List<String> roles) {
        this.roles = roles == null ? new ArrayList<>() : new ArrayList<>(roles);
    }
}
