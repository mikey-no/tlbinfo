# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table tlb (
  id                            bigint auto_increment not null,
  created                       timestamp,
  modified                      timestamp,
  tlbid                         varchar(255),
  name                          varchar(255),
  introduced                    timestamp,
  discontinued                  timestamp,
  note                          varchar(2048),
  constraint pk_tlb primary key (id)
);


# --- !Downs

drop table if exists tlb;

