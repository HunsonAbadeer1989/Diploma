insert into users (is_moderator, reg_time, name, email)
    values ( 0, NOW(), 'ivan', 'ivanemail@yandex.ru'),
    ( 1, NOW(), 'eugene', 'eugeneemail@yandex.ru');

insert into posts (is_active, moderation_status, moderator_id, user_id, publication_time, title, text, view_count)
    values ( 1, 'ACCEPTED', (SELECT id FROM users WHERE is_moderator like 1),
            (SELECT id FROM users WHERE is_moderator like 0), ADDDATE(NOW(), INTERVAL -780 DAY), 'MyFirstPost', 'Test post text', 1),
            ( 0, 'ACCEPTED', (SELECT id FROM users WHERE is_moderator like 1),
                (SELECT id FROM users WHERE name like 'ivan'), ADDDATE(NOW(), INTERVAL -360 DAY), 'MySecondPost', 'Test post text', 1),
            ( 1, 'ACCEPTED', (SELECT id FROM users WHERE is_moderator like 1),
                (SELECT id FROM users WHERE name like 'eugene'), ADDDATE(NOW(), INTERVAL -200 DAY), 'SimplePost', 'Simple post text', 2),
            ( 1, 'ACCEPTED', (SELECT id FROM users WHERE is_moderator like 1),
                (SELECT id FROM users WHERE name like 'eugene'), ADDDATE(NOW(), INTERVAL -100 DAY), 'ThirdPost', 'Third post text', 3);

insert into post_comments (post_id, user_id, time, text, parent_id)
    values ( (SELECT id FROM posts WHERE title like 'MyFirstPost'),
             (SELECT id FROM users WHERE name like 'ivan'),
              NOW(), 'You totally wright!', 3),
           ( (SELECT id FROM posts WHERE title like 'MySecondPost'),
             (SELECT id FROM users WHERE name like 'ivan'),
              NOW(), 'You totally wrong!', 4);

insert into tags (name) values ('lookAtMe'), ('Java'), ('skillbox');

insert into tag2post (post_id, tag_id) values (1, 1), (2, 3), (4, 2);


