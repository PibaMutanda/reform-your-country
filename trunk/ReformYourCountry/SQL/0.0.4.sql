-- 2012-10-10 Lionel

    alter table users 
        add column accountconnectedtype varchar(20);
    alter table mail add column emailreplyto varchar(100);   
-- 2012-10-19 Jamal
    alter table articleversion 
        add column versionnumber int4;
        
        
-- 2012-10-23  Piba

    create table video (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        idonhost varchar(255),
        createdby_id int8,
        updatedby_id int8,
        article_id int8,
        primary key (id)
    );

    alter table video 
        add constraint fk4ed245bcd43bd3 
        foreign key (article_id) 
        references article;

    alter table video 
        add constraint fk4ed245bccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table video 
        add constraint fk4ed245b3967baba 
        foreign key (updatedby_id) 
        references users;
 -- 2012-10-24 Lionel

    alter table users 
        add column ispasswordknownbytheuser boolean not null default true;   
        
-- 2012-11-05 Cédric

    alter table argument 
        add column positivearg boolean not null  default false;