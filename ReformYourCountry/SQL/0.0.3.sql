-- 2012-10-12 maxime
    
    alter table article 
        add column lastversion_id int8;

    create table articleversion (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        content text,
        summary text,
        toclassify text,
        createdby_id int8,
        updatedby_id int8,
        article_id int8,
        primary key (id)
    );

    alter table article 
        add constraint fk379164d662a15001 
        foreign key (lastversion_id) 
        references articleversion;

    alter table articleversion 
        add constraint fk973f5322cd43bd3 
        foreign key (article_id) 
        references article;

    alter table articleversion 
        add constraint fk973f5322ccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table articleversion 
        add constraint fk973f53223967baba 
        foreign key (updatedby_id) 
        references users;

    alter table article 
        add column toclassify text;

   ALTER TABLE article DROP COLUMN content;
   ALTER TABLE article DROP COLUMN toclassify ;
   ALTER TABLE article DROP COLUMN summary ;
     
--2012-10-16
    alter table book add column subtitle varchar(255);
    
