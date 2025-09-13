-- Seed ról, grup i uprawnień dla realmu 'admin' bez użycia zmiennych sesyjnych.
-- Wymaga: istnieje realm 'admin' (V2) oraz użytkownik admina (V2) o username = ${admin-user-username}.
-- Zakłada dostępność funkcji gen_random_uuid() (rozszerzenie pgcrypto).

WITH
    -- Pobierz id realmu 'admin'
    realm_cte AS (
        SELECT r.id AS realm_id
        FROM realm r
        WHERE r.name = 'admin'
    ),

    -- Pobierz id użytkownika admina po username i realmie
    admin_user AS (
        SELECT u.id AS user_id
        FROM users u
                 JOIN realm_cte rc ON rc.realm_id = u.realm_id
        WHERE u.username = '${admin-user-username}'
    LIMIT 1
    ),

    -- Utwórz grupę administrators
    new_group AS (
INSERT INTO groups (id, realm_id, name)
SELECT gen_random_uuid(), rc.realm_id, 'administrators'
FROM realm_cte rc
    RETURNING id AS group_id, realm_id
    ),

    -- Podepnij admina do grupy
    group_admin_rel AS (
INSERT INTO group_user (group_id, user_id)
SELECT ng.group_id, au.user_id
FROM new_group ng
    JOIN admin_user au ON TRUE
    RETURNING group_id
    ),

    -- Utwórz rolę administrator
    new_role AS (
INSERT INTO role (id, realm_id, name)
SELECT gen_random_uuid(), rc.realm_id, 'administrator'
FROM realm_cte rc
    RETURNING id AS role_id, realm_id
    ),

    -- Podepnij rolę do grupy administrators
    group_role_rel AS (
INSERT INTO group_role (group_id, role_id)
SELECT ng.group_id, nr.role_id
FROM new_group ng
    CROSS JOIN new_role nr
    RETURNING role_id
    ),

    -- Wstaw zestaw uprawnień w tym samym realmie
    inserted_permissions AS (
INSERT INTO permission (id, realm_id, name)
SELECT gen_random_uuid(), rc.realm_id, v.perm_name
FROM realm_cte rc
    CROSS JOIN (VALUES
    ('add_user'),
    ('edit_user'),
    ('delete_user'),
    ('add_realm'),
    ('edit_realm'),
    ('delete_realm'),
    ('add_group'),
    ('edit_group'),
    ('delete_group'),
    ('add_role'),
    ('edit_role'),
    ('delete_role'),
    ('assign_permission_to_role'),
    ('assign_role_to_user'),
    ('assign_role_to_group'),
    ('revoke_permission_from_role'),
    ('revoke_role_from_user'),
    ('revoke_role_from_group'),
    ('configure')
    ) AS v(perm_name)
    RETURNING id
    )

-- Podepnij wszystkie uprawnienia do roli administrator
INSERT INTO role_permission (permission_id, role_id)
SELECT p.id, nr.role_id
FROM inserted_permissions p
         CROSS JOIN new_role nr;