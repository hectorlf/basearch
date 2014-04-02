-- Table: languages
create table languages
(
  id integer not null primary key,
  language character varying(3) not null,
  region character varying(3),
  variant character varying(8)
);

-- Table: users
create table users
(
  id integer not null primary key,
  username character varying(50) not null,
  password character varying(50) not null,
  enabled boolean not null,
  language_id integer not null,
  constraint fk_users_languages foreign key(language_id) references languages(id)
);
create unique index ix_users_username on users (username);

-- Table: authorities
create table authorities
(
  username character varying(50) not null,
  authority character varying(50) not null,
  constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);
