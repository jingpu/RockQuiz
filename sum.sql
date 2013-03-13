CREATE TABLE userTable (
	userId varchar(20),
	password char(40),
	registrationTime datetime, 
	status char(1), -- u/s: u-user;s-admin
	gender char(1), -- m/f
	email varchar(50),
)

CREATE TABLE userId_history (
	Time datetime,
	Type varchar(36), 
	content varchar(32)
)

/*
userId_history format:
time --|------ type -------|---- content
	      (t)ake(quizId)---|---- quizName
		    (c)reate ------|---- quizName
   (a)chieve(id#000)(quizId) --|---- quizName
*/

CREATE TABLE userId_network (
	userId varchar(20),
	status char(1) -- r(request)/u(unconfirmed)/f(confirmed)
)

CREATE TABLE userId_inbox (
	code char(40),
	Time datetime,
	fromUser varchar(20),
	Type char(1), -- c(challenge)/r(friend request)/f(friend confirm)/n(normal text)
	title text,
	content text,
	ifRead char(1) -- 1(read)/0(unread)
)

CREATE TABLE userId_sent (
	code char(40)
	Time datetime,
	toUser varchar(20),
	Type char(1), -- c(challenge)/r(friend request)/f(friend confirm)/n(normal text)
	title text,
	content text
)

CREATE TABLE annouce (
	Time datetime,
	content text,
	admin varchar(20)
)