-- Example initial Flyway migration
-- Uses placeholders defined in application.yml or Gradle flyway {} block
-- Placeholders: ${schema}, ${appuser}



insert into realm (id, name)
values ('65048522-abe8-49d9-97b7-238adb139a42', 'admin');

insert into users (id, realm_id, type, password, username, email)
values
    (gen_random_uuid(), (select id from realm where name = 'admin'), 'HUMAN', '${admin-user-password}', '${admin-user-username}', 'oskar.sek94@gmail.com');

insert into client (id, realm_id, callback_url, hosts)
values
(gen_random_uuid(), (select id from realm where name = 'admin'), '${self-client-callback}', '${self-client-host}');