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
    isImmediateCorrection BOOLEAN
);

INSERT INTO Global_Quiz_Info_Table VALUES
	("quizExample0","Patrick","This is an exmaple quiz description. 
							   User should follow the instruction to complete the quiz",
							   "#Geo#His#", false, true, false, false),
    ("quizExample1","Molly","This is an exmaple quiz description. 
							   User should follow the instruction to complete the quiz",
							   "#Geo", false, true, false, false),
							   
    ("quizExample2","More","This is an exmaple quiz description. 
							   User should follow the instruction to complete the quiz",
							   "#His", false, true, false, false);

							   
						

DROP TABLE IF EXISTS Question_Response_Pool;
 -- remove table if it already exists and start from scratch

CREATE TABLE Question_Response_Pool (   
	question_id CHAR(32),
	creator_id CHAR(32),
    type_introduction BLOB,
    question_description BLOB,
    answer BLOB,
    max_score CHAR(4),
    tag_string CHAR(32),  
    correct_ratio CHAR(32)
);

INSERT INTO Question_Response_Pool VALUES
	("0001", "Patrick", "Same introduction of Question-Response questions",
	 "Who is the first president of USA?", "George Washington", "5", 
	 "#his#Cul#", "2/3");
							   

	 
	 

	 
DROP TABLE IF EXISTS Fill_In_Blank_Pool;
 -- remove table if it already exists and start from scratch

CREATE TABLE Fill_In_Blank_Pool (   
	question_id CHAR(32),
	creator_id CHAR(32),
    type_introduction BLOB,
    question_description BLOB,
    answer BLOB,
    max_score CHAR(4),
    tag_string CHAR(32),  
    correct_ratio CHAR(32)
);

INSERT INTO Fill_In_Blank_Pool VALUES
	("0001","Patrick", "Same introduction of Fill-In-Blank-Pool questions",
	 "#blank# is the first president of USA?", "George Washington", "5",
	 "#his#Cul#", "2/3");
	 

	 
	 
DROP TABLE IF EXISTS Multi_Choice_Pool;
 -- remove table if it already exists and start from scratch

CREATE TABLE Multi_Choice_Pool (  
	question_id CHAR(32),
	creator_id CHAR(32),
    type_introduction BLOB,
    question_description BLOB,
    answer BLOB,
    max_score CHAR(4),
    tag_string CHAR(32),
    correct_ratio CHAR(32),
    choiceA BLOB,
    choiceB BLOB,
    choiceC BLOB,
    choiceD BLOB
);

INSERT INTO Multi_Choice_Pool VALUES
	("0001","Patrick", "Same introduction of Multi-Choice questions",
	 "Who is the first president of USA?", 
	 "George Washington", "George Washington", "George Washington", "George Washington", "5",
	  "A","#his#Cul#", "2/3");


DROP TABLE IF EXISTS Picture_Response_Pool;
 -- remove table if it already exists and start from scratch

CREATE TABLE Picture_Response_Pool (   
	question_id CHAR(32),
	creator_id CHAR(32),
    type_introduction BLOB,
    question_description BLOB,
    answer BLOB,
    max_score CHAR(4),
    tag_string CHAR(32),
    correct_ratio CHAR(32),
    url BLOB
);

INSERT INTO Picture_Response_Pool VALUES
	("0001","Patrick", "Same introduction of Picture-Response questions",
	 "Who is in the following picture?", "http://dev.mysql.com/doc/refman/5.1/en/comments.html",
	 "George Washington", "5", "#his#Cul#", "2/3")
							   