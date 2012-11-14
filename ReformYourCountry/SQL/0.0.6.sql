-- 2012-11-13 maxime
    DROP TABLE article_goodexample ;
    create table article_goodexample (
        articles_id int8 not null,
        goodexamples_id int8 not null
    );

    alter table article_goodexample 
        add constraint fkfa5423e433d25d2c 
        foreign key (articles_id) 
        references article;

    alter table article_goodexample 
        add constraint fkfa5423e4bd17c73a 
        foreign key (goodexamples_id) 
        references goodexample;
    
    DROP TABLE article_action  ;
    create table article_action (
        articles_id int8 not null,
        actions_id int8 not null
    );

    alter table article_action 
        add constraint fk351605dff1d594da 
        foreign key (actions_id) 
        references action;

    alter table article_action 
        add constraint fk351605df33d25d2c 
        foreign key (articles_id) 
        references article;
 
    
-- 2012-11-14 Delphine

    alter table users 
        add column title varchar(250);

-- 2012-11-14 Delphine

    alter table users 
        add column certificationdate timestamp;
 -- 2012-11-14 CEDRIC

    alter table action 
        add column shortname varchar(20) unique;