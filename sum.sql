CREATE TABLE userTable (
	userId varchar(20),
	password varchar(50),
	registrationTime datetime, 
	status char(1) -- u/s: u-user;s-admin
)

CREATE TABLE userId_history (
	Time datetime,
	Type char(1), 
	content varchar(50)
)

/*
userId_history format:
time --|--- type ------|----- content
		  (t)ake ------|----- quizId
		 (c)reate -----|---- quizName
		(a)chievement -|-- achievename
*/

CREATE TABLE userId_network (
	userId varchar(20),
	status char(1) -- r(request)/u(unconfirmed)/c(confirmed)/i(ignore)
)

CREATE TABLE userId_inbox (
	Time datetime,
	fromUser varchar(20),
	Type char(1), -- c(challenge)/r(friend request)/c(friend conform)/n(normal text)
	title varchar(50),
	content text,
	ifRead char(1) -- 1(read)/0(unread)
)

CREATE TABLE userId_sent (
	Time datetime,
	toUser varchar(20),
	Type char(1), -- c(challenge)/r(friend request)/c(friend confirm)/n(normal text)
	title varchar(50),
	content text,
)
