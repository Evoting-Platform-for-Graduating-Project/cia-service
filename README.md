# CIA

This project uses Micronaut and Flyway for database migrations.

## Flyway – how to use

- Configure DB in src/main/resources/application.yml under datasources.default.
- Flyway is enabled via flyway.datasources.default.enabled: true.
- Migration files live in src/main/resources/db/migration (e.g., V1__init.sql).

### Example Gradle commands

- ./gradlew flywayInfo — show migration status
- ./gradlew flywayMigrate — apply pending migrations
- ./gradlew flywayValidate — validate checksum/history
- ./gradlew flywayRepair — fix checksums and history issues
- ./gradlew flywayClean — drop objects (use with caution in dev only)

You can point to another environment profile (to resolve application-<env>.yml) with:

- ./gradlew -Penv=dev flywayMigrate

Or override DB credentials inline:

- ./gradlew flywayMigrate -PDB_PASSWORD=secret

### Placeholders

Placeholders can be set in application.yml under flyway.datasources.default.placeholders or in build.gradle in the flyway {} block.
Use them inside SQL like ${schema} or ${app_user}. See V1__init.sql for an example.

---

## Micronaut 4.7.3 Documentation

- [User Guide](https://docs.micronaut.io/4.7.3/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.7.3/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.7.3/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

---

- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)
- [Shadow Gradle Plugin](https://plugins.gradle.org/plugin/com.github.johnrengelman.shadow)

## Feature lombok documentation

- [Micronaut Project Lombok documentation](https://docs.micronaut.io/latest/guide/index.html#lombok)

- [https://projectlombok.org/features/all](https://projectlombok.org/features/all)

## Feature cache-caffeine documentation

- [Micronaut Caffeine Cache documentation](https://micronaut-projects.github.io/micronaut-cache/latest/guide/index.html)

- [https://github.com/ben-manes/caffeine](https://github.com/ben-manes/caffeine)

## Feature junit-platform-suite-engine documentation

- [https://junit.org/junit5/docs/current/humanUser-guide/#junit-platform-suite-engine-setup](https://junit.org/junit5/docs/current/humanUser-guide/#junit-platform-suite-engine-setup)

## Feature security documentation

- [Micronaut Security documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html)

## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)

## Feature security-session documentation

- [Micronaut Security Session documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html#session)

## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)

## Feature security-jwt documentation

- [Micronaut Security JWT documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html)

## Feature buildless documentation

- [https://docs.less.build/](https://docs.less.build/)

## Feature junit-params documentation

- [https://junit.org/junit5/docs/current/humanUser-guide/#writing-tests-parameterized-tests](https://junit.org/junit5/docs/current/humanUser-guide/#writing-tests-parameterized-tests)


