-- 사용자 계정관리 테이블
create table if not exists UserTB
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    userId varchar(127) not null,
    nickName varchar(127),
    profileImgUrl varchar(1000),
    status varchar(31) check(STATUS in ('0', '1', '2')),
    updatedAt timestamp default now(),
    createdAt timestamp not null default now(),
    primary key (Id)
);

create table if not exists FeedTB
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    userId varchar(127) not null,
    status varchar(31) check(STATUS in ('0', '1', '2')),
    updatedAt timestamp default now(),
    createdAt timestamp not null default now(),
    primary key (id)
);


create table if not exists ContentTB
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    userId varchar(127) not null,
    feedId int not null,
    `text` varchar(1023) not null,
    url varchar(1023) not null,
    status varchar(31) check(STATUS in ('0', '1', '2')),
    updatedAt timestamp default now(),
    createdAt timestamp not null default now(),
    primary key (id)
);

create table if not exists ClapTB
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    userId varchar(127) not null,
    feedId int not null,
    createdAt timestamp not null default now(),
    primary key (id)
);


comment on table UserTB is '사용자 계정관리 테이블';
comment on column UserTB.id is '사용자 UNIQUE KEY';
comment on column UserTB.userId is '사용자 ID';
comment on column UserTB.nickName is '사용자 이름';
comment on column UserTB.profileImgUrl is '프로필 이미지 링크';
comment on column UserTB.status is '사용자 상태';
comment on column UserTB.updatedAt is '업데이트 일자';
comment on column UserTB.createdAt is '생성 일자';

insert into UserTB (userId, nickName, profileImgUrl, status) values ('admin1', 'admin1', 'admin1', '0');
insert into UserTB (userId, nickName, profileImgUrl, status) values ('admin2', 'admin2', 'admin2', '0');
insert into UserTB (userId, nickName, profileImgUrl, status) values ('admin3', 'admin3', 'admin3', '0');
insert into UserTB (userId, nickName, profileImgUrl, status) values ('admin4', 'admin4', 'admin4', '0');
insert into UserTB (userId, nickName, profileImgUrl, status) values ('admin5', 'admin5', 'admin5', '0');


-- 친구관리 테이블
create table if not exists FollowTB
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    followerUserId varchar(127) not null,
    followerNickName varchar(127) not null,
    followeeUserId varchar(127) not null,
    followeeNickName varchar(127) not null,
    createdAt timestamp not null default now(),
    primary key (Id)
);

insert into FollowTB(followerUserId, followerNickName, followeeUserId, followeeNickName) values ('admin1', 'admin1', 'admin2', 'admin2');
insert into FollowTB(followerUserId, followerNickName, followeeUserId, followeeNickName) values ('admin1', 'admin1', 'admin3', 'admin3');
insert into FollowTB(followerUserId, followerNickName, followeeUserId, followeeNickName) values ('admin1', 'admin1', 'admin4', 'admin4');