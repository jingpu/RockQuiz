USE c_cs108_jingpu;

DROP TABLE IF EXISTS Global_Quiz_Info_Table;

CREATE TABLE Global_Quiz_Info_Table (  
	quizName CHAR(32),
    creatorId CHAR(32),
    quizDescription BLOB,
    tagString CHAR(255),
    canPractice BOOLEAN,
    isRandom BOOLEAN,
    isOnePage BOOLEAN,
    isImmediateCorrection BOOLEAN,
    createTime TIMESTAMP,
    category CHAR(32)
);

INSERT INTO Global_Quiz_Info_Table VALUES
	("quizExample0","Patrick","This is an exmaple quiz description. 
							   User should follow the instruction to complete the quiz",
							   "#Geo#His#", false, true, true, false,"2012-01-19 03:14:07", "Geology"),
    ("quizExample1","Molly","This is an exmaple quiz description. 
							   User should follow the instruction to complete the quiz",
							   "#Geo", false, true, false, false,"2013-01-01 03:14:07", "Art"),						   
    ("quizExample2","More","This is an exmaple quiz description. 
							   User should follow the instruction to complete the quiz",
							   "#His", false, true, false, false,"2012-01-19 05:14:07", "Geology"),
    ("NoOneTaken","More","This is an exmaple quiz description. 
							   User should follow the instruction to complete the quiz",
							   "#His", false, true, false, false,"2012-01-19 05:14:07", "Geology");

							   
DROP TABLE IF EXISTS quizExample0_Content_Table;

CREATE TABLE quizExample0_Content_Table (  
	questionNum CHAR(32),
    questionType CHAR(32),
    questionId CHAR(32)
);

INSERT INTO quizExample0_Content_Table VALUES
	("0","Question_Response","0001"),
    ("1","Fill_In_Blank","0001"),
    ("2","Multi_Choice","0001");	
							   
DROP TABLE IF EXISTS quizExample0_Event_Table;

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
    
DROP TABLE IF EXISTS quizExample1_Content_Table;

CREATE TABLE quizExample1_Content_Table (  
	questionNum CHAR(32),
    questionType CHAR(32),
    questionId CHAR(32)
);

INSERT INTO quizExample1_Content_Table VALUES
	("0","Multi_Choice","0001"),
    ("1","Fill_In_Blank","0001"),
    ("2","Question_Response","0001");	
							   
DROP TABLE IF EXISTS quizExample1_Event_Table;
 -- remove table if it already exists and start from scratch

CREATE TABLE quizExample1_Event_Table (  
	quizId CHAR(32),
    userName CHAR(32),
    submitTime TIMESTAMP,
    timeElapsed BIGINT,
    score INT
);

INSERT INTO quizExample1_Event_Table VALUES
	("0","Patrick","2013-01-19 03:14:07", 60000, 8),
    ("1","Molly","2013-01-19 03:14:07", 600000, 9),
    ("2","Jim","2013-03-19 02:14:07", 50000, 10),
    ("3","Kate","2013-03-19 05:14:07", 30000, 7);
    
    
    
DROP TABLE IF EXISTS quizExample2_Content_Table;

CREATE TABLE quizExample2_Content_Table (  
	questionNum CHAR(32),
    questionType CHAR(32),
    questionId CHAR(32)
);

INSERT INTO quizExample2_Content_Table VALUES
	("0","Fill_In_Blank","0001"),
    ("1","Fill_In_Blank","0001"),
    ("2","Multi_Choice","0001");	
							   
DROP TABLE IF EXISTS quizExample2_Event_Table;

CREATE TABLE quizExample2_Event_Table (  
	quizId CHAR(32),
    userName CHAR(32),
    submitTime TIMESTAMP,
    timeElapsed BIGINT,
    score INT
);

INSERT INTO quizExample2_Event_Table VALUES
	("0","Patrick","2013-01-19 03:14:07", 60000, 8),
    ("1","Molly","2013-01-19 03:14:07", 600000, 9),
    ("2","Jim","2013-03-19 02:14:07", 50000, 10),
    ("3","Kate","2013-03-19 05:14:07", 30000, 7),
    ("5","Molly","2013-03-19 05:14:07", 3000, 8),
    ("6","Patrick","2013-02-19 03:14:07", 20000, 7);	
    
    
DROP TABLE IF EXISTS NoOneTaken_Content_Table;

CREATE TABLE NoOneTaken_Content_Table (  
	questionNum CHAR(32),
    questionType CHAR(32),
    questionId CHAR(32)
);

INSERT INTO NoOneTaken_Content_Table VALUES
	("0","Fill_In_Blank","0001"),
    ("1","Fill_In_Blank","0001"),
    ("2","Multi_Choice","0001");	
							   
DROP TABLE IF EXISTS NoOneTaken_Event_Table;

CREATE TABLE NoOneTaken_Event_Table (  
	quizId CHAR(32),
    userName CHAR(32),
    submitTime TIMESTAMP,
    timeElapsed BIGINT,
    score INT
);