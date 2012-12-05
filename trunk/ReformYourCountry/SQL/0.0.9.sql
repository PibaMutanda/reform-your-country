-- 2012-12-04 Johnny

delete from users where role=5;
-- 2012-12-04 Julien
alter table users 
        add column badgepoints int4;
-- 2012-12-05 maxime

ALTER TABLE goodexample RENAME description TO content;
ALTER TABLE argument DROP COLUMN user_id ;
ALTER TABLE comment DROP COLUMN user_id  ;