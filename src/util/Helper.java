package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

	public static String replacePound(String quizName) {
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
		String str = quizName.replaceAll("'","''");
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

}
