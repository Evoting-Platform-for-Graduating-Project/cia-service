package org.evoting.cia.domain.role.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SecurityTier {
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    ULTRA(4),
    FINAL(5);

    private final long level;

    public static SecurityTier fromLevel(int level) {
        for (SecurityTier tier : values()) {
            if (tier.getLevel() == level) {
                return tier;
            }
        }
        return null;
    }
}
