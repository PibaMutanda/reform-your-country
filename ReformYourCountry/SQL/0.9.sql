ALTER TABLE article RENAME lastversionrenderdsummary  TO lastversionrenderedsummary;

alter table article 
        add column lastversionrenderedtoclassify text;

    alter table video 
        add column caption varchar(255);

    alter table video 
        add column videotype varchar(255);

    update video set videotype = 'YOUTUBE';
    
-----  executed on server so far. John 2013-01-16  