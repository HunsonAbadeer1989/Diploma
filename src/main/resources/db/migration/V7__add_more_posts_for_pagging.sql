
insert into posts (is_active, moderation_status, moderator_id, user_id, publication_time, title, view_count, text)
    values ( 1, 'ACCEPTED', (SELECT id FROM users WHERE name like 'eugene'), (SELECT id FROM users WHERE name like 'ivan'), NOW(), 'AOP in Spring Framework', 0, 'This is sample of post'),
           ( 1, 'ACCEPTED', (SELECT id FROM users WHERE name like 'eugene'), (SELECT id FROM users WHERE name like 'ivan'), NOW(), 'DI and IoC', 0, 'This is sample of post'),
            ( 1, 'ACCEPTED', (SELECT id FROM users WHERE name like 'eugene'), (SELECT id FROM users WHERE name like 'ivan'), NOW(), 'Bean live cycle', 0, 'This is sample of post'),
            ( 1, 'ACCEPTED', (SELECT id FROM users WHERE name like 'eugene'), (SELECT id FROM users WHERE name like 'ivan'), NOW(), 'JDBC', 0, 'This is sample of post'),
            ( 1, 'ACCEPTED', (SELECT id FROM users WHERE name like 'eugene'), (SELECT id FROM users WHERE name like 'ivan'), NOW(), 'MySQL', 0, 'This is sample of post'),
            ( 1, 'ACCEPTED', (SELECT id FROM users WHERE name like 'eugene'), (SELECT id FROM users WHERE name like 'ivan'), NOW(), 'POSTMAN', 0, 'This is sample of post'),
            ( 1, 'ACCEPTED', (SELECT id FROM users WHERE name like 'eugene'), (SELECT id FROM users WHERE name like 'ivan'), NOW(), 'Postgre', 0, 'This is sample of post'),
            ( 1, 'ACCEPTED', (SELECT id FROM users WHERE name like 'eugene'), (SELECT id FROM users WHERE name like 'ivan'), NOW(), 'Stackoverflow', 0, 'This is sample of post'),
            ( 1, 'ACCEPTED', (SELECT id FROM users WHERE name like 'eugene'), (SELECT id FROM users WHERE name like 'ivan'), NOW(), 'Multithreading', 0, 'This is sample of post'),
            ( 1, 'ACCEPTED', (SELECT id FROM users WHERE name like 'eugene'), (SELECT id FROM users WHERE name like 'ivan'), NOW(), 'SoftSkills', 0, 'This is sample of post'),
            ( 1, 'ACCEPTED', (SELECT id FROM users WHERE name like 'eugene'), (SELECT id FROM users WHERE name like 'ivan'), NOW(), 'Python', 0, 'This is sample of post'),
            ( 1, 'ACCEPTED', (SELECT id FROM users WHERE name like 'eugene'), (SELECT id FROM users WHERE name like 'ivan'), NOW(), 'C++', 0, 'This is sample of post');
