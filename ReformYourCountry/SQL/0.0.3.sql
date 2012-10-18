-- 2012-10-16 Thomas VR

    alter table article 
        add column content text;

    alter table article 
        add column summary text;

    alter table article 
        add column toclassify text;
-- 2012-10-12 Maxime
--    ALTER TABLE article DROP COLUMN content;
--    ALTER TABLE article DROP COLUMN toclassify ;
--    ALTER TABLE article DROP COLUMN summary ;
-- 2012-10-18 Thomas

    alter table book 
        add column subtitle varchar(255);