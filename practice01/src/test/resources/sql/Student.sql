create table student (
    id INT NOT NULL auto_increment,
    first_name VARCHAR(20) default NULL,
    last_name  VARCHAR(20) default NULL,
    grade     INT  default NULL,
    PRIMARY KEY (id)
);