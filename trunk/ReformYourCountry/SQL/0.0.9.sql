-- 2012-12-04 Johnny

delete from users where role=5;
-- 2012-12-04 Julien
alter table users 
        add column badgepoints int4;