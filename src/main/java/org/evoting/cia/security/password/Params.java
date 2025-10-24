package org.evoting.cia.security.password;

import lombok.Getter;

record Params(int memory, int iterations, int parallelism) {
}
