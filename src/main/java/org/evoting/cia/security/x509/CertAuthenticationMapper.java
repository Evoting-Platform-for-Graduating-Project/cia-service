package org.evoting.cia.security.x509;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.reactivestreams.Publisher;

@Singleton
@Requires(property = "micronaut.security.x509.enabled", value = "true")
public class CertAuthenticationMapper {

    private final MtlsConfigurationProperties mtlsConfig;

    @Inject
    public CertAuthenticationMapper(MtlsConfigurationProperties mtlsConfig) {
        this.mtlsConfig = mtlsConfig;
    }

    public Publisher<AuthenticationResponse> map(X509Certificate[] chain) {
        if (chain == null || chain.length == 0 || chain[0] == null) {
            return Publishers.just(AuthenticationResponse.failure("No client certificate"));
        }
        X509Certificate cert = chain[0];
        String dn = cert.getSubjectX500Principal().getName();
        String cn = extractCn(dn);
        if (cn == null || cn.isBlank()) {
            return Publishers.just(AuthenticationResponse.failure("Certificate subject CN not found"));
        }
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("dn", dn);
        attributes.put("serialNumber", cert.getSerialNumber().toString());
        List<String> roles = mtlsConfig.getRoles();
        return Publishers.just(AuthenticationResponse.success(cn, roles, attributes));
    }

    private String extractCn(String dn) {
        if (dn == null) {
            return null;
        }
        String[] parts = dn.split(",");
        for (String raw : parts) {
            String p = raw.trim();
            if (p.regionMatches(true, 0, "CN=", 0, 3)) {
                return p.substring(3).trim();
            }
        }
        return null;
    }
}
