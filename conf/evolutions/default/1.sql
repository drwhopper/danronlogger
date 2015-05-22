# --- !Ups
create table Villages (
  no int primary key not null, 
  name varchar(50) not null,
  capacity int not null,
  vcast char(1) not null, 
  rule_cat bool not null,
  rule_sp bool not null,
  rule_lunatic bool not null,
  special varchar(16),
  createTime varchar(16),
  winner varchar(5) not null,
  comment text
);
# --- !Downs
drop table Villages;