# --- !Ups
create index winnerset on Villages (
  winner
);
# --- !Downs 
drop index winnerset;