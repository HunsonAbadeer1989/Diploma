create table captcha_codes (
    id bigint not null auto_increment,
    code varchar(255),
    secret_code varchar(255),
    time datetime,
    primary key (id));
create table global_settings (
    id bigint not null auto_increment,
    code varchar(255),
    name varchar(255),
    value varchar(255),
    primary key (id));
create table post_comments (
    id bigint not null auto_increment,
    parent_id bigint,
    text varchar(255),
    time datetime, post_id bigint,
    user_id bigint,
    primary key (id));
create table post_votes (
    id bigint not null auto_increment,
    time datetime, value integer,
    post_id bigint, user_id bigint,
    primary key (id));
create table posts (
    id bigint not null auto_increment,
    is_active integer,
    moderation_status varchar(255),
    moderator_id bigint, text varchar(255),
    publication_time datetime,
    title varchar(255),
    user_id bigint,
    view_count integer,
    primary key (id));
create table tag2post (
    id bigint not null auto_increment,
    post_id bigint,
    tag_id bigint,
    primary key (id));
create table tags (
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id));
create table users (
    id bigint not null auto_increment,
    code varchar(255),
    email varchar(255),
    is_moderator integer,
    name varchar(255),
    password varchar(255),
    photo varchar(255),
    reg_time datetime,
    primary key (id));
