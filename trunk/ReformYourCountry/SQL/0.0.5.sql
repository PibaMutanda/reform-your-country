-- 2012-11-05 Jérome       
    alter table voteaction drop column group_id;  
    
    alter table groups drop column parent_id;
    
    -- 2012-11-05 Lionel

    alter table users 
        add column specialtype varchar(255);