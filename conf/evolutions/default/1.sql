# --- !Ups

create table TASKS
(
    id            int auto_increment,
    title         VARCHAR(100)              null,
    description   VARCHAR(255) default ''   null,
    dateCompleted TIMESTAMP    default NULL null,
    constraint TASKS_pk
        primary key (id)
);

# --- !Downs

DROP TABLE TASKS;
