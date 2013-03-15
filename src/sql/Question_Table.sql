USE c_cs108_jingpu;
   
    
DROP TABLE IF EXISTS Question_Response_Pool;
 -- remove table if it already exists and start from scratch

CREATE TABLE Question_Response_Pool (   
	question_id CHAR(32),
	creator_id CHAR(32),
    time_limit INT,
    question_description BLOB,
    answer BLOB,
    max_score INT,
    tag_string CHAR(32),  
    correct_ratio FLOAT
);

INSERT INTO Question_Response_Pool VALUES
	("0001", "Patrick", 0, "Who is the first president of USA?", "George Washington", 5, 
	 "#his##Cul#", 0.8);
							   
	 

	 
DROP TABLE IF EXISTS Fill_In_Blank_Pool;
 -- remove table if it already exists and start from scratch

CREATE TABLE Fill_In_Blank_Pool (   
	question_id CHAR(32),
	creator_id CHAR(32),
    time_limit INT,	
    question_description BLOB,
    answer BLOB,
    max_score INT,
    tag_string CHAR(32),  
    correct_ratio FLOAT
   
);

INSERT INTO Fill_In_Blank_Pool VALUES
	("0001","Patrick", 0, "#blank# is the first president of USA?", "George Washington", 5,
	 "#his##Cul#", 0.8);
	 

	 
	 
DROP TABLE IF EXISTS Multi_Choice_Pool;
 -- remove table if it already exists and start from scratch

CREATE TABLE Multi_Choice_Pool (  
	question_id CHAR(32),
	creator_id CHAR(32),
    time_limit INT,
    question_description BLOB,
    answer BLOB,
    max_score INT,
    tag_string CHAR(32),
    correct_ratio FLOAT,
    choices BLOB
    
);

INSERT INTO Multi_Choice_Pool VALUES
	("0001","Patrick", 0, "Who is the first president of USA?", "George Washington", 5,
	  "#his##Cul#", 0.8, "#George Washington##George Washington##George Washington##George Washington#");
	 


DROP TABLE IF EXISTS Picture_Response_Pool;
 -- remove table if it already exists and start from scratch

CREATE TABLE Picture_Response_Pool (   
	question_id CHAR(32),
	creator_id CHAR(32),
    time_limit INT,
    question_description BLOB,
    answer BLOB,
    max_score INT,
    tag_string CHAR(32),
    correct_ratio FLOAT,
    url BLOB
);

INSERT INTO Picture_Response_Pool VALUES
	("0001","Patrick", 0,  "Who is in the following picture?",
	 "George Washington", 5, "#his##Cul#", 0.8, "http://dev.mysql.com/doc/refman/5.1/en/comments.html");
	 
	 
	 
	 
	 
DROP TABLE IF EXISTS Multi_Answer_Pool;
 -- remove table if it already exists and start from scratch

CREATE TABLE Multi_Answer_Pool (   
	question_id CHAR(32),
	creator_id CHAR(32),
    time_limit INT,
    question_description BLOB,
    answer BLOB,
    max_score INT,
    tag_string CHAR(32),  
    correct_ratio FLOAT,
    is_order CHAR(8)
);

INSERT INTO Multi_Answer_Pool VALUES
	("0001", "Patrick", 0, "Please name the top 3 populous contries in the world", "#China##India##xxx#", 9, 
	 "#his##Cul#", 0.8, "true");
	 
	 


	 
DROP TABLE IF EXISTS Multi_Choice_Multi_Answer_Pool;
 -- remove table if it already exists and start from scratch

CREATE TABLE Multi_Choice_Multi_Answer_Pool (  
	question_id CHAR(32),
	creator_id CHAR(32),
    time_limit INT,
    question_description BLOB,
    answer BLOB,
    max_score INT,
    tag_string CHAR(32),
    correct_ratio FLOAT,
    choices BLOB
    
);

INSERT INTO Multi_Choice_Multi_Answer_Pool VALUES
	("0001","Patrick",0, "Which ones of the following are correct", "#choiceB##choiceC#", 4 ,
	  "#his##Cul#", 0.8,"#choiceA##choiceB##choiceC##choiceD");
	  

	  
	  
	  
DROP TABLE IF EXISTS Matching_Question_Pool;
 -- remove table if it already exists and start from scratch

CREATE TABLE Matching_Question_Pool (  
	question_id CHAR(32),
	creator_id CHAR(32),
    time_limit INT,
    question_description BLOB,
    answer BLOB,
    max_score INT,
    tag_string CHAR(32),
    correct_ratio FLOAT,
    choices BLOB
    
);

INSERT INTO Matching_Question_Pool VALUES
	("0001","Patrick",0, "Please match the following choice options and answer options", "#answerA##answerB##answerC##answerD#", 4 ,
	  "#his##Cul#", 0.8,"#choiceA##choiceB##choiceC##choiceD")
	  
	  
	 

