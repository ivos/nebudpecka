create table sessions (
  token    uuid primary key,
  created  timestamp not null,
  duration integer   not null,
  expires  timestamp not null,
  user_id  bigint    not null
);

alter table sessions
  add constraint fk_sessions_user foreign key (user_id) references users on delete cascade;
