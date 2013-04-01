/*******************************************************************************
 * The MIT License (MIT)
 * Copyright (c) 2013 Jing Pu, Yang Zhao, You Yuan, Huijie Yu 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to 
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
 * THE SOFTWARE.
 ******************************************************************************/
package user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import quiz.*;
import util.Helper;

/**
 * @author youyuan
 * **/
public class Activity {
	public final String user;
	public final String time;
	public final String type;
	public final String content;

	public Activity(String user, String time, String type, String content){
		this.user = user;
		this.time = time;
		this.type = type;
		this.content = content;
	}

	public String toString(boolean useTimeTrsf){
		QuizManager man = new MyQuizManager();
		String quizDisplay = content + "(deprecated)";
		Quiz quiz = man.getQuiz(content);
		if(quiz != null){
			quizDisplay = Helper.displayQuiz(quiz, false);
		}
		StringBuilder dscr = new StringBuilder();
		dscr.append(Helper.displayUser(user));
		if(type.charAt(0) == 't') {
			dscr.append(" took "+ quizDisplay);

		} else if(type.equals("c")){
			dscr.append(" created a quiz " + quizDisplay);

		} else if(type.charAt(0) == 'a'){
			dscr.append(" earned " + "<a href='#' title='"
					+ Helper.getTitleDescription(type) +"' style='font-weight:bold;'>"
					+ Helper.getTitle(type) + "</a> in "
					+ quizDisplay);
			
		} else {
			System.out.println("activity does not exist");
		}
		dscr.append(" | ");
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		try {
			Date date;
			date = sdf.parse(time);
			Date now = new Date();
			if(useTimeTrsf) dscr.append(TimeTrsf.dscr(date, now));
			else dscr.append(date);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dscr.toString();
	}

	public String toStringMe(boolean useTimeTrsf){
		QuizManager man = new MyQuizManager();
		String quizDisplay = content + "(deprecated)";
		Quiz quiz = man.getQuiz(content);
		if(quiz != null){
			quizDisplay = Helper.displayQuiz(quiz, false);
		}
		StringBuilder dscr = new StringBuilder();
		if(type.charAt(0) == 't') {
			if(quiz == null){
				dscr.append(quizDisplay);
			} else {
				String quizId = type.substring(1);
				String score = quiz.getScore(quizId);
				if(score == null){
					dscr.append(quizDisplay);
				} else{
					String full = quiz.getMaxScore();
					dscr.append("Got " + score + "/" + full + " in "
							+ quizDisplay);
				}
			}
		} else if(type.equals("c")){
			dscr.append(quizDisplay);

		} else if(type.charAt(0) == 'a'){
			dscr.append("Earned " + "<a href='#' title='"
					+ Helper.getTitleDescription(type) +"' style='font-weight:bold;'>"
					+ Helper.getTitle(type) + "</a> in "
					+ quizDisplay);

		} else {
			System.out.println("activity does not exist");
		}

		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		try {
			Date date;
			date = sdf.parse(time);
			Date now = new Date();
			dscr.append(" | ");
			if(useTimeTrsf) dscr.append(TimeTrsf.dscr(date, now));
			else dscr.append(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dscr.toString();
	}
}
