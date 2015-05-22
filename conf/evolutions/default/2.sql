# --- !Ups
create index castset on Villages (
  capacity,
  vcast, 
  rule_cat,
  rule_sp,
  rule_lunatic
);
# --- !Downs
drop index castset;