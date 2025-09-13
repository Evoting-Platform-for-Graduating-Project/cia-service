package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;

public class V2__init_programmatic extends BaseJavaMigration {
//
//    @Override
//    public void migrate(Context context) throws Exception {
//        Connection conn = context.getConnection();
//
//        String schema = resolveSchema(conn);
//        String appUser = resolveAppUser(conn);
//
//        try (Statement st = conn.createStatement()) {
//            // Ensure schema exists
//            st.execute("create schema if not exists " + quoteIdent(schema));
//
//            // Example extension (PostgreSQL). Use DO block to avoid error on lack of superuser perms.
//            // If extension already exists or perms are insufficient, ignore error gracefully.
//            try {
//                st.execute("create extension if not exists \"uuid-ossp\"");
//            } catch (SQLException e) {
//                // Logically ignore if insufficient privilege or already exists (depends on DB perms)
//                // You can integrate with a logger if needed
//            }
//
//            // Tables
//            st.execute("""
//                create table if not exists %s.realm (
//                    id uuid primary key default uuid_generate_v4(),
//                    system_name varchar(100) unique not null,
//                    display_name varchar(150) not null,
//                    created_at timestamp with time zone default now()
//                )
//                """.formatted(qualified(schema, "realm")));
//
//            st.execute("""
//                create table if not exists %s.config (
//                    id uuid primary key default uuid_generate_v4(),
//                    key varchar(120) not null,
//                    value text,
//                    type varchar(50),
//                    created_at timestamp with time zone default now()
//                )
//                """.formatted(qualified(schema, "config")));
//
//            // Grants (optional, jeśli user istnieje)
//            if (appUser != null && !appUser.isBlank()) {
//                String qUser = quoteIdent(appUser);
//                st.execute("grant usage on schema " + quoteIdent(schema) + " to " + qUser);
//                st.execute("grant select, insert, update, delete on all tables in schema " + quoteIdent(schema) + " to " + qUser);
//                st.execute("""
//                    alter default privileges in schema %s
//                        grant select, insert, update, delete on tables to %s
//                    """.formatted(quoteIdent(schema), qUser));
//            }
//        }
//    }
//
//    private String resolveSchema(Connection conn) {
//        // 1) env DB_SCHEMA, 2) current_schema(), 3) "public"
//        String fromEnv = System.getenv("DB_SCHEMA");
//        if (fromEnv != null && !fromEnv.isBlank()) {
//            return fromEnv;
//        }
//        try (Statement st = conn.createStatement();
//             ResultSet rs = st.executeQuery("select current_schema()")) {
//            if (rs.next()) {
//                String cs = rs.getString(1);
//                if (cs != null && !cs.isBlank()) {
//                    return cs;
//                }
//            }
//        } catch (SQLException ignored) { }
//        return "public";
//        }
//
//    private String resolveAppUser(Connection conn) {
//        // 1) env APP_USER, 2) JDBC user from metadata (may include quoting), 3) null (skip grants)
//        String fromEnv = System.getenv("APP_USER");
//        if (fromEnv != null && !fromEnv.isBlank()) {
//            return fromEnv;
//        }
//        try {
//            String mdUser = conn.getMetaData().getUserName();
//            if (mdUser != null && !mdUser.isBlank()) {
//                return mdUser;
//            }
//        } catch (SQLException ignored) { }
//        return null; // brak — pomijamy GRANTy
//    }
//
//    private static String quoteIdent(String identifier) {
//        // Proste cytowanie identyfikatora SQL (PostgreSQL). Uwaga: bezpiecznie cytujemy podwójny cudzysłów.
//        return "\"" + identifier.replace("\"", "\"\"") + "\"";
//    }
//
//    private static String qualified(String schema, String table) {
//        return quoteIdent(schema) + "." + quoteIdent(table);
//    }
}
