-- 2012-09-17 Lionel (pour Jamal)
    alter table groups 
        add column hasimage boolean default '0' not null; 
-- 2012-09-18 Jamal
    alter table users add column url varchar(255);
-- 2012-09-21
    alter table users drop column url;
-- 2012-09-25 maxime
    alter table article add column shortname varchar(20);
-- 2012-09-25 Thomas
    alter table book add column url varchar(255) ;
-- 2012-09-27 Thomas
    alter table article 
        add column description text;
-- 2012-09-28 Lionel
CREATE TABLE userconnection
(
  userid character varying(255) NOT NULL,
  providerid character varying(255) NOT NULL,
  provideruserid character varying(255) NOT NULL,
  rank integer NOT NULL,
  displayname character varying(255),
  profileurl character varying(512),
  imageurl character varying(512),
  accesstoken character varying(255) NOT NULL,
  secret character varying(255),
  refreshtoken character varying(255),
  expiretime bigint,
  CONSTRAINT userconnection_pkey PRIMARY KEY (userid , providerid , provideruserid );
    alter table article add column description text;
    
-- 2012-09-28 Maxime
    ALTER TABLE article ALTER url SET NOT NULL;
    ALTER TABLE article ADD UNIQUE (url);
    
    ALTER TABLE article ALTER title SET NOT NULL;
    ALTER TABLE article ADD UNIQUE (title);
    
    ALTER TABLE article ALTER shortName SET NOT NULL;
    ALTER TABLE article ADD UNIQUE (shortName);