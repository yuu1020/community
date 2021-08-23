create table question
(
    id BIGINT auto_increment,
    title varchar(50),
    description TEXT,
    gmt_create BIGINT,
    gmt_modified BIGINT,
    creator BIGINT,
    comment_count int default 0,
    view_count int default 0,
    like_count int default 0,
    tag varchar(256),
    constraint QUESTION_PK
        primary key (id)
);

