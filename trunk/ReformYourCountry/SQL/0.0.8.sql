-- 2012-11-30 maxime
 alter table comment 
        add column hidden boolean not null DEFAULT FALSE;
        
 alter table comment 
        add column goodexample_id int8;

 alter table comment 
        add constraint fk9bde863f14093d3 
        foreign key (goodexample_id) 
        references goodexample;
        
XXXXXXXXXXXXXXXXX->>>>>>>>>>>>>>>>>>> deployed. Use 0.0.9.sql        