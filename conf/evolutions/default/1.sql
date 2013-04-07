# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comment (
  id                        integer not null,
  post_id                   integer,
  commenter                 varchar(255),
  body                      varchar(255),
  constraint pk_comment primary key (id))
;

create table post (
  id                        integer not null,
  caption                   varchar(255),
  body                      varchar(255),
  constraint pk_post primary key (id))
;

create table tag (
  name                      varchar(255) not null,
  constraint pk_tag primary key (name))
;


create table post_tag (
  post_id                        integer not null,
  tag_name                       varchar(255) not null,
  constraint pk_post_tag primary key (post_id, tag_name))
;

create table tag_post (
  tag_name                       varchar(255) not null,
  post_id                        integer not null,
  constraint pk_tag_post primary key (tag_name, post_id))
;
create sequence comment_seq;

create sequence post_seq;

create sequence tag_seq;

alter table comment add constraint fk_comment_post_1 foreign key (post_id) references post (id) on delete restrict on update restrict;
create index ix_comment_post_1 on comment (post_id);



alter table post_tag add constraint fk_post_tag_post_01 foreign key (post_id) references post (id) on delete restrict on update restrict;

alter table post_tag add constraint fk_post_tag_tag_02 foreign key (tag_name) references tag (name) on delete restrict on update restrict;

alter table tag_post add constraint fk_tag_post_tag_01 foreign key (tag_name) references tag (name) on delete restrict on update restrict;

alter table tag_post add constraint fk_tag_post_post_02 foreign key (post_id) references post (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists comment;

drop table if exists post;

drop table if exists post_tag;

drop table if exists tag;

drop table if exists tag_post;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists comment_seq;

drop sequence if exists post_seq;

drop sequence if exists tag_seq;

