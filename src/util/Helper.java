package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import quiz.Quiz;

public final class Helper {

	public static String generateTags(List<String> tags) {
		StringBuilder s = new StringBuilder();
		for (String t : tags) {
			s.append("#" + t);
		}
		return s.toString();
	}

	public static List<String> parseTags(String tagString) {
		String[] splits = tagString.split("#");
		List<String> list = new ArrayList<String>(splits.length);
		for (int i = 0; i < splits.length; i++)
			if (!splits[i].equals(""))
				list.add(splits[i]);
		return list;
	}

	/**
	 * Calculates MD5 hash from current system time
	 * 
	 * @return An MD5 hash String is expressed as a hexadecimal number, 32
	 *         digits long.
	 */
	public static String getMD5ForTime() {
		String timeString = "" + new Date().getTime();
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(timeString.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			String hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String replaceSpace(String quizName) {
		String[] str = quizName.split(" ");
		String replacedName = "";
		if (str.length == 1)
			return quizName;
		else {
			for (int i = 0; i < str.length; i++) {
				replacedName += str[i];
				if (i != str.length - 1)
					replacedName += "_";
			}
		}
		return replacedName;
	}

	public static String replaceUnderscore(String quizName) {
		String[] str = quizName.split("_");
		String replacedName = "";
		if (str.length == 1)
			return quizName;
		else {
			for (int i = 0; i < str.length; i++) {
				replacedName += str[i];
				if (i != str.length - 1)
					replacedName += " ";
			}
		}
		return replacedName;
	}

	public static String replaceComma(String quizName) {
		String str = quizName.replaceAll("'", "''");
		return str;
	}

	public static String listToString(List<String> tags) {
		String tagString = "";
		if (tags.size() > 1) {
			for (String str : tags) {
				tagString += "#";
				tagString += str;
				tagString += "#";
			}
		}
		return tagString;
	}

	public static String array2String(String[] category) {
		StringBuilder sb = new StringBuilder();
		String delim = "";
		if (category.length >= 1) {
			for (int i = 0; i < category.length; i++) {
				sb.append(delim).append(category[i]);
				delim = "#";
			}
		}
		return sb.toString();
	}

	public static String[] string2Array(String cateStr) {
		String[] category = cateStr.split("[#]+");
		return category;
	}

	public static String getTitle(String str) {
		/*
		 * achieveId- 1- Amateur Author 2- Prolific Author 3- Prodigious Author
		 * 4- Quiz Machine 5- I am the Greatest 6- Practice Makes Perfect
		 */
		String title = str.substring(1);
		if (title.equals("1")) {
			return "Amateur Author";
		} else if (title.equals("2")) {
			return "Prolific Author";
		} else if (title.equals("3")) {
			return "Prodigious Author";
		} else if (title.equals("4")) {
			return "Quiz Machine";
		} else if (title.equals("5")) {
			return "I am the Greatest";
		} else if (title.equals("6")) {
			return "Practice Makes Perfect";
		}
		return "";
	}

	public static String getTitleDescription(String str) {
		/*
		 * achieveId- 1- Amateur Author 2- Prolific Author 3- Prodigious Author
		 * 4- Quiz Machine 5- I am the Greatest 6- Practice Makes Perfect
		 */
		String title = str.substring(1);
		if (title.equals("1")) {
			return "When user created a quiz";
		} else if (title.equals("2")) {
			return "When user created five quizzes";
		} else if (title.equals("3")) {
			return "When user created ten quizzes";
		} else if (title.equals("4")) {
			return "When user took ten quizzes";
		} else if (title.equals("5")) {
			return "When user had the highest score on a quiz";
		} else if (title.equals("6")) {
			return "The user took a quiz in practice mode";
		}
		return "";
	}

	public static String getTitleNDescription(String type) {
		return "<a href='#' title='" + Helper.getTitleDescription(type)
				+ "' style='font-weight:bold;'>" + Helper.getTitle(type)
				+ "</a>";
	}

	public static String displayQuiz(Quiz quiz, boolean showCreator) {
		String quizUrl = quiz.getSummaryPage();
		String creator = quiz.getCreatorId();
		String description = quiz.getQuizName() + "\n"
				+ quiz.getQuizDescription();

		StringBuilder result = new StringBuilder();
		result.append("<a href='" + quizUrl + "' title='" + description + "'>"
				+ quiz.getQuizName() + "</a>");
		if (!showCreator) {
			return result.toString();
		} else {
			result.append(" (by:<a href='userpage.jsp?id=" + creator + "'>"
					+ creator + "</a>)");
			return result.toString();
		}
	}

	public static String displayUser(String user) {
		return "<a href=\"userpage.jsp?id=" + user + "\">" + user + "</a>";
	}

}
