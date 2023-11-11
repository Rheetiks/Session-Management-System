create database BE_Assignment;

use BE_Assignment;

create table user(
	userId int primary key auto_increment,
    userName varchar(100),
    userUID varchar(100),
    userPassword varchar(200),
    userRole varchar(200)
);

select * from user;

select * from Session;

truncate Session;


insert into user(userName,userUID,userPassword,userRole) values('surekha','9029','surekha123','Student');


  
truncate Session;

delete from Session where studentId=(select userId from user where userUID=123);



SELECT DATE_ADD(CURDATE(), INTERVAL n DAY) AS DATE, TIME('10:00:00') as TIME
FROM (
    SELECT a.N + b.N * 10 AS n
    FROM (SELECT 0 AS N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) a,
		 (SELECT 0 AS N UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) b
    WHERE DATE_ADD(CURDATE(), INTERVAL a.N + b.N * 10 DAY) <= LAST_DAY(CURDATE())
) numbers
WHERE MONTH(DATE_ADD(CURDATE(), INTERVAL n DAY)) = MONTH(CURDATE()) AND DAYOFWEEK(DATE_ADD(CURDATE(), INTERVAL n DAY)) IN (5, 6);









create table Session(
	sessionId int primary key auto_increment,
    studentId int,
    deanid int,
    slotDate date,
    slotTime time,
    foreign key(studentId) references user(userId),
	foreign key(deanId) references user(userId)
);


insert into Session(studentId,deanid,slotDate,slotTime) values(1,2,curdate(),TIME('10:00:00'));




select now();