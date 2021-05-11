insert into users (is_moderator, reg_time, name, email, password)
    values ( 0, NOW(), 'kirill', 'kirillemail@yandex.ru', "$2y$12$9kYbkGnPHt.Ro0W.DuFbT.Hq2/Jp.8ZYj0ji/41w8828RVqvo5.Ni"),
    ( 1, NOW(), 'goga', 'gogaemail@yandex.ru', "$2y$12$9kYbkGnPHt.Ro0W.DuFbT.Hq2/Jp.8ZYj0ji/41w8828RVqvo5.Ni");

insert into posts (is_active, moderation_status, moderator_id, user_id, publication_time, title, text, view_count)
    values ( 1, 'ACCEPTED', 2, 1, ADDDATE(NOW(), INTERVAL -10 DAY), 'Hello world!', 'My first app in java IDE.', 5),
           ( 1, 'ACCEPTED', 2, 1, ADDDATE(NOW(), INTERVAL -9 DAY), 'This is JAVA', 'Lets talk about java lang, you first', 2),
            ( 1, 'ACCEPTED', 2, 1, ADDDATE(NOW(), INTERVAL -8 DAY), 'SkillBox', 'Our new sale for you.', 10),
            ( 1, 'ACCEPTED', 2, 1, ADDDATE(NOW(), INTERVAL -8 DAY), 'Hibernate', 'You do not need care about DB anymore', 101);

insert into post_comments (post_id, user_id, time, text, parent_id)
    values ( (SELECT id FROM posts WHERE title like 'Hello world!'),
             (SELECT id FROM users WHERE name like 'ivan'),
              NOW(), 'That cool', 3),

           ( (SELECT id FROM posts WHERE title like 'Hello world!'),
             (SELECT id FROM users WHERE name like 'ivan'),
              NOW(), 'But too boring', 3),

           ( (SELECT id FROM posts WHERE title like 'This is JAVA'),
            (SELECT id FROM users WHERE name like 'eugene'),
            NOW(), 'First bitches!', 5),

           ( (SELECT id FROM posts WHERE title like 'This is JAVA'),
            (SELECT id FROM users WHERE name like 'eugene'),
            NOW(), 'Second!', 5),

           ( (SELECT id FROM posts WHERE title like 'This is JAVA'),
            (SELECT id FROM users WHERE name like 'ivan'),
            NOW(), 'Not that again!', 5);

--insert into post_votes (user_id, post_id, time, value)
--    values (1, 3, NOW(), 1),
--            (2, 3, NOW(), 1),
--            (3, 3, NOW(), 1),
--            (4, 3, NOW(), 1),
--            (1, 4, NOW(), -1),
--            (2, 5, NOW(), 1),
--            (3, 5, NOW(), 1);
