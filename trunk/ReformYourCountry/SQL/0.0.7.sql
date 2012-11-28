-- maxime 20/11/12
ALTER TABLE goodexample DROP COLUMN  url ;

-- 2012-11-27 Delphine

    alter table goodexample 
        add column publishdate timestamp; 
        
-- 2012-11-27 Delphine

    alter table goodexample 
        drop column publishdate ; 
        
        