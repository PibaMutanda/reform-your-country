-- 2012-09-17 Lionel (pour Jamal)
    alter table groups 
        add column hasimage boolean default '0' not null; 
-- 2012-09-18 Jamal
    alter table users 
        add column url varchar(255);