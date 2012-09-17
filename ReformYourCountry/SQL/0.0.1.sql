
-- 17/09/12 maxime create initial state


create table article_action (
        article_id int8 not null,
        action_id int8 not null
    );

    create table action (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        content text,
        longdescription varchar(255),
        shortdescription varchar(100),
        title varchar(100),
        url varchar(255),
        createdby_id int8,
        updatedby_id int8,
        primary key (id)
    );

    create table argument (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        content text,
        title varchar(100),
        votecountagainst int4 not null,
        votecountpro int4 not null,
        createdby_id int8,
        updatedby_id int8,
        action_id int8 not null,
        user_id int8 not null,
        primary key (id)
    );

    create table article (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        content text,
        publicview boolean not null,
        publishdate timestamp,
        releasedate timestamp,
        summary text,
        title varchar(100),
        url varchar(255),
        createdby_id int8,
        updatedby_id int8,
        parent_id int8,
        primary key (id)
    );

    create table book (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        abrev varchar(255) not null unique,
        author varchar(255),
        description text,
        externalurl varchar(255),
        hasimage boolean default '0' not null,
        pubyear varchar(255),
        title varchar(255),
        top boolean not null,
        createdby_id int8,
        updatedby_id int8,
        primary key (id)
    );

    create table comment (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        content varchar(255),
        title varchar(255),
        createdby_id int8,
        updatedby_id int8,
        action_id int8 not null,
        user_id int8 not null,
        primary key (id)
    );

    create table groupreg (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        confirmed boolean not null,
        owner boolean not null,
        createdby_id int8,
        updatedby_id int8,
        confirmedby_id int8,
        groups int8,
        user_id int8,
        primary key (id)
    );

    create table groups (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        description varchar(255),
        hasimage boolean default '0' not null,
        name varchar(100) not null unique,
        url varchar(255) unique,
        createdby_id int8,
        updatedby_id int8,
        parent_id int8,
        primary key (id)
    );

    create table mail (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        content text not null,
        emailtarget varchar(100),
        mailcategory varchar(255),
        mailtype varchar(255),
        subject varchar(255) not null,
        usetemplate boolean not null,
        createdby_id int8,
        updatedby_id int8,
        replyto_id int8,
        user_id int8,
        primary key (id)
    );

    create table voteaction (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        value int4 not null,
        createdby_id int8,
        updatedby_id int8,
        action_id int8 not null,
        group_id int8,
        user_id int8 not null,
        primary key (id)
    );

    create table voteargument (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        pro boolean not null,
        value int4 not null,
        createdby_id int8,
        updatedby_id int8,
        argument_id int8 not null,
        user_id int8 not null,
        primary key (id)
    );

    create table users (
        id int8 not null,
        createdon timestamp,
        updatedon timestamp,
        accountstatus varchar(20) not null,
        birthdate timestamp,
        consecutivefailedlogins int4 not null,
        firstname varchar(50),
        gender varchar(255),
        lastaccess timestamp,
        lastfailedlogindate timestamp,
        lastloginip varchar(40),
        lastmailsentdate timestamp,
        lastname varchar(50),
        lockreason varchar(255),
        mail varchar(100),
        mailingdelaytype varchar(255),
        namechangelog text,
        nlsubscriber boolean not null,
        password varchar(100),
        picture boolean not null,
        registrationdate timestamp,
        role int4,
        spammer boolean not null,
        username varchar(255) not null unique,
        validationcode varchar(255),
        createdby_id int8,
        updatedby_id int8,
        spamreporter_id int8,
        primary key (id)
    );

    create table users_privileges (
        user_id int8 not null,
        privilege varchar(50) not null,
        primary key (user_id, privilege)
    );

    alter table article_action 
        add constraint fkc23699bfcd43bd3 
        foreign key (article_id) 
        references article;

    alter table article_action 
        add constraint fkc23699bf8f382901 
        foreign key (action_id) 
        references action;

    alter table action 
        add constraint fk74946a56ccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table action 
        add constraint fk74946a563967baba 
        foreign key (updatedby_id) 
        references users;

    alter table argument 
        add constraint fka519c2ddccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table argument 
        add constraint fka519c2dd8f382901 
        foreign key (action_id) 
        references action;

    alter table argument 
        add constraint fka519c2dd4ca5daa1 
        foreign key (user_id) 
        references users;

    alter table argument 
        add constraint fka519c2dd3967baba 
        foreign key (updatedby_id) 
        references users;

    alter table article 
        add constraint fk379164d6ccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table article 
        add constraint fk379164d67e65f39f 
        foreign key (parent_id) 
        references article;

    alter table article 
        add constraint fk379164d63967baba 
        foreign key (updatedby_id) 
        references users;

    alter table book 
        add constraint fk1faf09ccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table book 
        add constraint fk1faf093967baba 
        foreign key (updatedby_id) 
        references users;

    alter table comment 
        add constraint fk9bde863fccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table comment 
        add constraint fk9bde863f8f382901 
        foreign key (action_id) 
        references action;

    alter table comment 
        add constraint fk9bde863f4ca5daa1 
        foreign key (user_id) 
        references users;

    alter table comment 
        add constraint fk9bde863f3967baba 
        foreign key (updatedby_id) 
        references users;

    alter table groupreg 
        add constraint fk2208a9b5ccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table groupreg 
        add constraint fk2208a9b54ca5daa1 
        foreign key (user_id) 
        references users;

    alter table groupreg 
        add constraint fk2208a9b5d6ae86c 
        foreign key (groups) 
        references groups;

    alter table groupreg 
        add constraint fk2208a9b5bd045e96 
        foreign key (confirmedby_id) 
        references users;

    alter table groupreg 
        add constraint fk2208a9b53967baba 
        foreign key (updatedby_id) 
        references users;

    alter table groups 
        add constraint fk7fa2c5f4ccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table groups 
        add constraint fk7fa2c5f4d293bf68 
        foreign key (parent_id) 
        references groups;

    alter table groups 
        add constraint fk7fa2c5f43967baba 
        foreign key (updatedby_id) 
        references users;

    alter table mail 
        add constraint fk2479d7ccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table mail 
        add constraint fk2479d735d4a947 
        foreign key (replyto_id) 
        references users;

    alter table mail 
        add constraint fk2479d74ca5daa1 
        foreign key (user_id) 
        references users;

    alter table mail 
        add constraint fk2479d73967baba 
        foreign key (updatedby_id) 
        references users;

    alter table voteaction 
        add constraint fkbfca41e0ccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table voteaction 
        add constraint fkbfca41e08f382901 
        foreign key (action_id) 
        references action;

    alter table voteaction 
        add constraint fkbfca41e04ca5daa1 
        foreign key (user_id) 
        references users;

    alter table voteaction 
        add constraint fkbfca41e0755b8573 
        foreign key (group_id) 
        references groups;

    alter table voteaction 
        add constraint fkbfca41e03967baba 
        foreign key (updatedby_id) 
        references users;

    alter table voteargument 
        add constraint fkfa37dfe7ccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table voteargument 
        add constraint fkfa37dfe74ca5daa1 
        foreign key (user_id) 
        references users;

    alter table voteargument 
        add constraint fkfa37dfe7d4dae1e1 
        foreign key (argument_id) 
        references argument;

    alter table voteargument 
        add constraint fkfa37dfe73967baba 
        foreign key (updatedby_id) 
        references users;

    alter table users 
        add constraint fk6a68e08ccfcf0ad 
        foreign key (createdby_id) 
        references users;

    alter table users 
        add constraint fk6a68e081bee12e2 
        foreign key (spamreporter_id) 
        references users;

    alter table users 
        add constraint fk6a68e083967baba 
        foreign key (updatedby_id) 
        references users;

    alter table users_privileges 
        add constraint fk1b2412794ca5daa1 
        foreign key (user_id) 
        references users;

    create sequence hibernate_sequence;