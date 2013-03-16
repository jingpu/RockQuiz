<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="java.util.*"%>
<!DOCTYPE >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<link href="CSS/page_style.css" rel="stylesheet" type="text/css" />
<link href="friendpage_style.css" rel="stylesheet" type="text/css" />
<style>
#body li {
	list-style-type: square;
	margin: 1em;
}
</style>
<title>About RockQuiz</title>
</head>
<body>
	<div id="wrapper">
		<div id="inner">
			<div id="header">
				<h1>About RockQuiz</h1>
				<h3><%=new Date()%></h3>
				<div id="nav">
					<h2>
						<a href="home.jsp">Home</a> | <a href="Logout">Log out</a>
					</h2>
				</div>
			</div>
			<div id="body">
				<div class="inner">
					<p>
						<b><i>Required Feature</i></b>
					</p>
					<ul>
						<li>Question Type:
							<ul>
								<li>Question-Response</li>
								<li>Fill In the Blank</li>
								<li>Multi-Choice</li>
								<li>Picture-Response Questions</li>
							</ul>
						</li>
						<li>Quiz Properties and options:
							<ul>
								<li>Random Question</li>
								<li>One Page vs Multiple Page</li>
								<li>Immediate Correction</li>
							</ul>
						</li>
						<li>Scoring:
							<ul>
								<li>Basic score display for each quiz</li>
								<li>Separate scoring for Multi-Answer</li>
								<li>Quiz history about how users did in this quiz</li>
								<li>Rank based on scoring (top scorer)</li>
							</ul>
						</li>
						<li>User:
							<ul>
								<li>Create new account</li>
								<li>Login/Password encrypted</li>
							</ul>
						</li>
						<li>Friends:
							<ul>
								<li>Send/Cancel/Confirm friend request</li>
								<li>Remove friend</li>
								<li>Search users</li>
							</ul>
						</li>
						<li>Mail Messages:
							<ul>
								<li>Friend request</li>
								<li>Challenge request</li>
								<li>Simple message</li>
							</ul>
						</li>
						<li>History
							<ul>
								<li>Quiz performance - Taking or Creating quizzes</li>
							</ul>
						</li>
					</ul>


					<p>
						<b><i>Recommended Feature</i></b>
					</p>
					<ul>
						<li>Achievements
							<ul>
								<li>Add achievement whenever finishing a quiz</li>
								<li>Achievement list page</li>
							</ul>
						</li>
						<li>Administration</li>
						<ul>
							<li>Create announcements</li>
							<li>Remove user accounts</li>
							<li>Remove quizzes</li>
							<li>Clear all history information for a certain quiz</li>
							<li>Change users' rights - Normal user or Admin</li>
							<li>See site statistics:
								<ul>
									<li>Registered users list</li>
									<li>Quizzes list and taken counts</li>
								</ul>
							</li>
						</ul>
					</ul>


					<p>
						<b><i>Extension</i></b>
					</p>
					<ul>
						<li>New Question Types:
							<ul>
								<li>Multi-Answer Question(ordered/unordered answer)</li>
								<li>Multiple Choice With Multiple Answers</li>
								<li>Timed Questions(check if isOnePage is true, use
									javascript to disable this field)</li>
								<li>Matching</li>
								<li>Random order of choices</li>
							</ul>
						</li>
						<li>Dynamic webpage design for quiz and question creation:
							<ul>
							<li>Changeable number of question per quiz</li>
							<li>Changeable number of options and answers per question</li>
							</ul>
						</li>					
						<li>Tags and Categories:
							<ul>
								<li>Categorize quiz</li>
								<li>User can follow interesting categories</li>
								<li>Create new categories</li>
							</ul>
						</li>
						<li>Non-Registered Access
							<ul>
								<li>Guest home</li>
								<ul>
									<li>Popular quizzes</li>
									<li>Recent quizzes</li>
									<li>Search quizzes/users</li>
									<li>Forbidden to take quizzes</li>
								</ul>
							</ul>
						</li>
						<li>Privacy Setting
							<ul>
								<li>if user sets privacy,</li>
								<ul>
									<li>non-friends cannot get access to their page</li>
									<li>their names become "anonymous" when shown to
										non-friends</li>
								</ul>
							</ul>
						</li>

						<li>Cookies</li>
						<ul>
							<li>If users tick "Keep me logged in" when they log in,
								their logging information will last for as long as 1 month if
								they don't log out.
							</li>
						</ul>
						
						<li>Quiz editing</li>
						<li>Improved error handling</li>
						<li>XML loading</li>
						<li>Improved dynamic quiz creation page</li>
						<li>Advanced search
							<ul>
							<li>Creator Id</li>
							<li>Category</li>
							<li>Tag</li>
							<li>Automatically highlights keyword in results</li>
							<li>Ordering: Popularity, Creationg time, Best match</li>
							</ul>
						</li>
						<li>Awesome Naming :)</li>
					</ul>

				</div>

			</div>
		</div>
</body>
</html>
