
-- ****************************************
-- This SQL script creates the initial users.
-- ****************************************

INSERT INTO "security".roles (id,name) VALUES
     (1,'ADMIN'),
     (2,'READ');

INSERT INTO "security".users (id,username,"password",name,email,password_not_expired,enabled,not_expired,not_locked,login_attempts) VALUES
     (1,'root','$2a$10$DF5ljpM.scb4kRn2rqGWye94/jqwX3K6XgVbE3fbv5pSPTmfO6zqe','root','email1@nowhere.com',true,true,true,true,0),
     (2,'read','$2a$10$DF5ljpM.scb4kRn2rqGWye94/jqwX3K6XgVbE3fbv5pSPTmfO6zqe','read','email2@nowhere.com',true,true,true,true,0);

INSERT INTO "security".user_roles (user_id,role_id) VALUES
     (1,1),
     (2,2);

-- Assign every existing permission to the ADMIN role.
INSERT INTO "security".role_permissions (role_id, permission_id)
     SELECT
         1,
         p.id
     FROM "security".permissions AS p;

-- Assign every read permission to the READ role.
INSERT INTO "security".role_permissions (role_id, permission_id)
     SELECT
         r.id,
         p.id
     FROM "security".roles AS r
     JOIN "security".permissions AS p
         ON p."action" IN ('READ', 'VIEW')
     WHERE r.name = 'READ'
     ON CONFLICT (role_id, permission_id) DO NOTHING;



-- Add the LIBRARY role and user.
INSERT INTO "security".roles (id,name) VALUES
     (3,'LIBRARY');

INSERT INTO "security".users (id,username,"password",name,email,password_not_expired,enabled,not_expired,not_locked,login_attempts) VALUES
     (3,'library','$2a$10$DF5ljpM.scb4kRn2rqGWye94/jqwX3K6XgVbE3fbv5pSPTmfO6zqe','library','email3@nowhere.com',true,true,true,true,0);

INSERT INTO "security".user_roles (user_id,role_id) VALUES
     (3,3);

-- Assign library permissions.
INSERT INTO "security".role_permissions (role_id, permission_id)
     SELECT
         r.id,
         p.id
     FROM "security".roles AS r
     JOIN "security".permissions AS p
         ON p."resource" IN (
             'INVENTORY_DONOR',
             'LIBRARY_AUTHOR',
             'LIBRARY_PUBLISHER',
             'LIBRARY_BOOK',
             'LIBRARY_BOOK_TYPE',
             'LIBRARY_GAME_SYSTEM',
             'LIBRARY_LENDING'
         )
     WHERE r.name = 'LIBRARY'
     ON CONFLICT (role_id, permission_id) DO NOTHING;

-- Add the MEMBER role and user.
INSERT INTO "security".roles (id, name) VALUES
    (4, 'MEMBER');

INSERT INTO "security".users (id, username, "password", name, email, password_not_expired, enabled, not_expired, not_locked, login_attempts) VALUES
     (4, 'member','$2a$10$DF5ljpM.scb4kRn2rqGWye94/jqwX3K6XgVbE3fbv5pSPTmfO6zqe', 'member', 'email4@nowhere.com', true, true, true, true, 0);

INSERT INTO "security".user_roles (user_id, role_id) VALUES
    (4, 4);

-- Assign read permissions to the MEMBER role.
INSERT INTO "security".role_permissions (role_id, permission_id)
     SELECT
         r.id,
         p.id
     FROM "security".roles AS r
     CROSS JOIN "security".permissions AS p
     WHERE r.name = 'MEMBER'
         AND p."action" IN ('READ', 'VIEW')
         AND p."resource" IN (
             'MY_FEES',
             'LIBRARY_BOOK',
             'LIBRARY_LENDING',
             'MEMBER'
         )
     ON CONFLICT (role_id, permission_id) DO NOTHING;