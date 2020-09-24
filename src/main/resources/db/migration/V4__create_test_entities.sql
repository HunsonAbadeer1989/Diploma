insert into users (is_moderator, reg_time, name, email)
    values ( 0, NOW(), 'ivan', 'ivanemail@yandex.ru'),
    ( 1, NOW(), 'eugene', 'eugeneemail@yandex.ru');

insert into posts (is_active, moderation_status, moderator_id, user_id, publication_time, title, text)
    values ( 1, 'ACCEPTED', (SELECT id FROM users WHERE is_moderator like 1),
            (SELECT id FROM users WHERE is_moderator like 0), NOW(), 'MyFirstPost', 'Test post text'),
            ( 0, 'ACCEPTED', (SELECT id FROM users WHERE is_moderator like 1),
                (SELECT id FROM users WHERE name like 'ivan'), NOW(), 'MySecondPost', 'Test post text'),
            ( 1, 'ACCEPTED', (SELECT id FROM users WHERE is_moderator like 1),
                (SELECT id FROM users WHERE name like 'eugene'), NOW(), 'SimplePost', 'Simple post text'),
            ( 1, 'ACCEPTED', (SELECT id FROM users WHERE is_moderator like 1),
                (SELECT id FROM users WHERE name like 'eugene'), NOW(), 'ThirdPost', 'Third post text');

insert into post_comments (post_id, user_id, time, text )
    values ( (SELECT id FROM posts WHERE title like 'MyFirstPost'),
             (SELECT id FROM users WHERE name like 'ivan'),
              NOW(), 'You totally wright!'),
           ( (SELECT id FROM posts WHERE title like 'MySecondPost'),
             (SELECT id FROM users WHERE name like 'ivan'),
              NOW(), 'You totally wrong!');

insert into tags (name) values ('#lookAtme'), ('#Java'), ('#skillbox');


