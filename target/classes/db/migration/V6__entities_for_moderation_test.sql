insert into posts (is_active, moderation_status, moderator_id, user_id, publication_time, title, text, view_count)
    values (1, 'NEW', 2, 1, NOW(), 'Moderation posts', 'First NEW post', 0),
            (1, 'NEW', 2, 1, NOW(), 'Moderation posts',  'Second NEW post', 0),
            (1, 'NEW', 2, 1, NOW(), 'Moderation posts',  'Third NEW post', 0),
           (1, 'ACCEPTED', 2, 1, NOW(), 'Moderation posts',  'First ACCEPTED post', 0),
           (1, 'ACCEPTED', 2, 1, NOW(), 'Moderation posts',  'Second ACCEPTED post', 0),
           (1, 'ACCEPTED', 2, 1, NOW(), 'Moderation posts',  'Third ACCEPTED post', 0),
            (1, 'DECLINED', 2, 1, NOW(), 'Moderation posts',  'First DECLINE post', 0),
            (1, 'DECLINED', 2, 1, NOW(), 'Moderation posts',  'Second DECLINE post', 0),
            (1, 'DECLINED', 2, 1, NOW(), 'Moderation posts',  'Third DECLINE post', 0),
            (0, null, 2, 1, NOW(), 'Moderation posts',  'First INACTIVE post', 0),
            (0, null, 2, 1, NOW(), 'Moderation posts',  'Second INACTIVE post', 0);
