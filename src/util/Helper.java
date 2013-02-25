package util;

import java.util.ArrayList;
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
}
