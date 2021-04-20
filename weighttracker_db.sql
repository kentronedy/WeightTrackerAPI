drop database weighttrackerdb;
drop user weighttracker;
create user weighttracker with password 'password';
create database weighttrackerdb with template=template0 owner=weighttracker;
\connect weighttrackerdb;
alter default privileges grant all on tables to weighttracker;
alter default privileges grant all on sequences to weighttracker;

create table wt_users(
username varchar(30) primary key not null,
password text not null,
goal double precision,
permission integer default 0 not null
);

create table wt_daily_entries(
entry_id integer primary key not null,
date text not null,
weight double precision,
sleep double precision,
date_int bigint not null,
username varchar(30) not null
);
alter table wt_daily_entries add constraint entry_users_fk
foreign key (username) references wt_users(username);

create sequence wt_date_entries_seq increment 1 start 1;

