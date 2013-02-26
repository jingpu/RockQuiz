USE c_cs108_yzhao3;

DROP TABLE IF EXISTS Global_Quiz_Info_Table;
 -- remove table if it already exists and start from scratch

CREATE TABLE Global_Quiz_Info_Table (  
	quizName CHAR(32),
    creatorId CHAR(32),
    quizDescription BLOB,
    tagString CHAR(255),
    canPractice BOOLEAN,
    isRandom BOOLEAN,
    isOnePage BOOLEAN,
    isImmediateCorrection BOOLEAN,
    createTime TIMESTAMP
);

INSERT INTO Global_Quiz_Info_Table VALUES
	("quizExample0","Patrick","This is an exmaple quiz description. 
							   User should follow the instruction to complete the quiz",
							   "#Geo#His#", false, true, false, false,"2012-01-19 03:14:07"),
    ("quizExample1","Molly","This is an exmaple quiz description. 
							   User should follow the instruction to complete the quiz",
							   "#Geo", false, true, false, false,"2013-01-01 03:14:07"),
							   
    ("quizExample2","More","This is an exmaple quiz description. 
							   User should follow the instruction to complete the quiz",
							   "#His", false, true, false, false,"2012-01-19 05:14:07");

							   
DROP TABLE IF EXISTS quizExample0_Content_Table;
 -- remove table if it already exists and start from scratch

CREATE TABLE quizExample0_Content_Table (  
	questionNum CHAR(32),
    questionType CHAR(32),
    questionId CHAR(32)
);

INSERT INTO quizExample0_Content_Table VALUES
	("0","QandR","1"),
    ("1","QandR","0"),
    ("2","FillInBlank","3");	
							   
DROP TABLE IF EXISTS quizExample0_Event_Table;
 -- remove table if it already exists and start from scratch

CREATE TABLE quizExample0_Event_Table (  
	quizId CHAR(32),
    userName CHAR(32),
    submitTime TIMESTAMP,
    timeElapsed BIGINT,
    score INT
);

INSERT INTO quizExample0_Event_Table VALUES
	("0","Patrick","2013-01-19 03:14:07", 60000, 8),
    ("1","Molly","2013-01-19 03:14:07", 600000, 9),
    ("2","Jim","2013-03-19 02:14:07", 50000, 10),
    ("3","Kate","2013-03-19 05:14:07", 30000, 7),
    ("4","Molly","2013-01-19 01:14:07", 10000, 6),
    ("5","Molly","2013-03-19 05:14:07", 3000, 8),
    ("6","Patrick","2013-02-19 03:14:07", 20000, 7);						   


							   