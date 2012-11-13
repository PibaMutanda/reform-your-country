-- 2012-11-05 Jérome       
    alter table voteaction drop column group_id;  
    
    alter table groups drop column parent_id;
    
    -- 2012-11-05 Lionel

    alter table users 
        add column specialtype varchar(255);
        
        
-- 2012-11-05  Jamal

    create table article_goodexample (
        article_id int8 not null,
        goodexample_id int8 not null
    );

    create table goodexample (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        description text,
        title varchar(100) not null unique,
        url varchar(255) not null unique,
        createdby_id int8,
        updatedby_id int8,
        primary key (id)
    );

    alter table article_goodexample 
        add constraint fk92ca6c04cd43bd3 
        foreign key (article_id) 
        references article;

    alter table article_goodexample 
        add constraint fk92ca6c0414093d3 
        foreign key (goodexample_id) 
        references goodexample;

    alter table goodexample 
        add constraint fk2944f1cdccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table goodexample 
        add constraint fk2944f1cd3967baba 
        foreign key (updatedby_id) 
        references users;
        
-- 2012-11-05 maxime

    create table votegoodexample (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        value int4 not null,
        createdby_id int8,
        updatedby_id int8,
        goodexample_id int8 not null,
        user_id int8 not null,
        primary key (id)
    );

    alter table votegoodexample 
        add constraint fk649a4083ccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table votegoodexample 
        add constraint fk649a408314093d3 
        foreign key (goodexample_id) 
        references goodexample;

    alter table votegoodexample 
        add constraint fk649a40834ca5daa1 
        foreign key (user_id) 
        references users;

    alter table votegoodexample 
        add constraint fk649a40833967baba 
        foreign key (updatedby_id) 
        references users;
        


-- 2012-11-06 maxime

    alter table votegoodexample DROP COLUMN value ;   
        
    alter table goodexample 
        add column votecount int4 not null;
        
-- 2010-11-08 John
    alter table users drop column registrationdate;
    
-- 2012-11-08 Jamal, Piba & Johnny (Gang Of Three - to be 3)

    create table badge (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        badgetype varchar(255),
        createdby_id int8,
        updatedby_id int8,
        user_id int8 not null,
        primary key (id)
    );

    alter table badge 
        add constraint fk3cfab83ccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table badge 
        add constraint fk3cfab834ca5daa1 
        foreign key (user_id) 
        references users;

    alter table badge 
        add constraint fk3cfab833967baba 
        foreign key (updatedby_id) 
        references users;

-- 2012-11-05 Cédric

    alter table argument 
        add column positivearg boolean not null  default false;
        
 --2012-11-08 Lionel
    alter table users
        add column askedgroup boolean not null default false;
        
 -- 2012-11-08 Julien

    drop table comment;
    create table comment (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        content text,
        createdby_id int8,
        updatedby_id int8,
        argument_id int8 not null,
        user_id int8 not null,
        primary key (id)
    );

    alter table comment 
        add constraint fk9bde863fccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table comment 
        add constraint fk9bde863f4ca5daa1 
        foreign key (user_id) 
        references users;

    alter table comment 
        add constraint fk9bde863fd4dae1e1 
        foreign key (argument_id) 
        references argument;

    alter table comment 
        add constraint fk9bde863f3967baba 
        foreign key (updatedby_id) 
        references users;
        
-- 2012-11-08 Jamal, Piba & Johnny (Gang Of Three - to be 3)

    create table badge (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        badgetype varchar(255),
        createdby_id int8,
        updatedby_id int8,
        user_id int8 not null,
        primary key (id)
    );

    alter table badge 
        add constraint fk3cfab83ccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table badge 
        add constraint fk3cfab834ca5daa1 
        foreign key (user_id) 
        references users;

    alter table badge 
        add constraint fk3cfab833967baba 
        foreign key (updatedby_id) 
        references users;

-- 2012-11-08 Thomas

    ALTER TABLE action DROP COLUMN longdescription;
        
-- 2012-11-09 Jérome

    alter table article 
        add column lastversionrenderdsummary text;

    alter table article 
        add column lastversionrenderedcontent text;       
