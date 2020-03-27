create table users (
  id            bigserial primary key,
  version       bigint       not null,
  name          varchar(100) not null,
  email         varchar(100) not null,
  password_hash varchar(100) not null
);

alter table users
  add constraint cc_users_name check (length(name) >= 1);

alter table users
  add constraint cc_users_email check (length(email) >= 1);

alter table users
  add constraint uc_users_email unique (email);
