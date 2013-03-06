/**
 * 
 */
package util;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import quiz.FillInBlank;
import quiz.MultiChoice;
import quiz.MyQuiz;
import quiz.PResponse;
import quiz.QResponse;
import quiz.QuestionBase;

/**
 * @author yang
 * 
 */
public class XMLParser {

	public XMLParser(String xmlFile) {

	}

	public static MyQuiz getQuizFromXml(String xmlFile) {
		Document dom = parseXmlFile(xmlFile);
		Element quizEle = dom.getDocumentElement();

		// get boolean attributes from quiz tag
		boolean isRandom = Boolean.parseBoolean(quizEle.getAttribute("random"));
		boolean isOnePage = Boolean.parseBoolean(quizEle
				.getAttribute("one-page"));
		boolean canPractice = Boolean.parseBoolean(quizEle
				.getAttribute("practice-mode"));
		boolean isImmCorrection = Boolean.parseBoolean(quizEle
				.getAttribute("immediate-correction"));

		Timestamp createTime = new Timestamp(new java.util.Date().getTime());

		String quizName = Helper.replaceSpace(getTextValue(quizEle, "title"));
		String category = getTextValue(quizEle, "category");
		String quizDescription = getTextValue(quizEle, "description");
		String creatorId = getTextValue(quizEle, "creatorId");
		List<String> tags = new ArrayList<String>();
		tags.add(getTextValue(quizEle, "tag"));

		List<QuestionBase> questionList = getQuestions(dom, creatorId);
		return new MyQuiz(quizName, creatorId, quizDescription, tags,
				canPractice, isRandom, isOnePage, isImmCorrection,
				questionList, createTime, category);

	}

	public static void exportQuizToXml(MyQuiz quiz, String xmlFile) {
	}

	private static Document parseXmlFile(String xmlFile) {
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document dom = null;
		try {
			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			dom = db.parse(xmlFile);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return dom;
	}

	private static List<QuestionBase> getQuestions(Document dom,
			String creatorId) {
		// get the root element
		Element docEle = dom.getDocumentElement();
		List<QuestionBase> questionList = new ArrayList<QuestionBase>();

		// get a nodelist of question elements
		NodeList nl = docEle.getElementsByTagName("question");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				// get the question element
				Element el = (Element) nl.item(i);

				// get the Employee object
				QuestionBase question = createQuestion(el, creatorId);
				question.saveToDatabase();
				// add it to list
				questionList.add(question);
			}
		}
		return questionList;
	}

	private static QuestionBase createQuestion(Element questionEl,
			String creatorId) {

		String type = questionEl.getAttribute("type");
		if (type.equals("question-response")) {
			String questionType = QuestionBase.QR;
			int timeLimit = getIntValue(questionEl, "time-limit");
			String questionDescription = getTextValue(questionEl, "query");
			String answer = getTextValue(questionEl, "answer");
			int maxScore = getIntValue(questionEl, "score");
			String tagString = getTextValue(questionEl, "tag");
			// at creation time, ratio = 0
			return new QResponse(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, 0);
		}

		if (type.equals("fill-in-blank")) {
			String questionType = QuestionBase.FIB;
			int timeLimit = getIntValue(questionEl, "time-limit");
			String answer = getTextValue(questionEl, "answer");
			int maxScore = getIntValue(questionEl, "score");
			String tagString = getTextValue(questionEl, "tag");
			// special take care of questionDescription here
			String questionDescription = parseBlankDescription(questionEl,
					"blank-query");
			// at creation time, ratio = 0
			return new FillInBlank(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, 0);
		}

		if (type.equals("multiple-choice")) {
			String questionType = QuestionBase.MC;
			int timeLimit = getIntValue(questionEl, "time-limit");
			String questionDescription = getTextValue(questionEl, "query");
			int maxScore = getIntValue(questionEl, "score");
			String tagString = getTextValue(questionEl, "tag");
			String choices = parseChoices(questionEl, "option");
			String answer = parseAnswer(questionEl, "option");

			// at creation time, ratio = 0
			return new MultiChoice(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, 0,
					choices);
		}

		if (type.equals("picture-response")) {
			String questionType = QuestionBase.PR;
			int timeLimit = getIntValue(questionEl, "time-limit");
			String questionDescription = getTextValue(questionEl, "query");
			String answer = getTextValue(questionEl, "answer");
			int maxScore = getIntValue(questionEl, "score");
			String tagString = getTextValue(questionEl, "tag");
			String url = getTextValue(questionEl, "image-location");

			// at creation time, ratio = 0
			return new PResponse(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, 0, url);
		}

		return null;
	}

	// tagname is "blank-query"
	private static String parseBlankDescription(Element questionEl,
			String tagname) {
		String description = "";
		NodeList nl = questionEl.getElementsByTagName(tagname);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			description += getTextValue(el, "pre");
			description += "#blank#";
			description += getTextValue(el, "post");
		}
		return description;
	}

	private static String parseAnswer(Element questionEl, String tagName) {
		String text = "";
		NodeList nl = questionEl.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element el = (Element) nl.item(i);
				if (el.getAttribute("answer") != null)
					text = el.getFirstChild().getNodeValue();
			}
		}
		return text;
	}

	private static String parseChoices(Element questionEl, String tagName) {
		String text = "";
		NodeList nl = questionEl.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				text += "#";
				Element el = (Element) nl.item(i);
				text = el.getFirstChild().getNodeValue();
				text += "#";
			}
		}
		return text;
	}

	private static String getTextValue(Element questionEl, String tagName) {
		// if no this tag, return ""
		String text = "";
		NodeList nl = questionEl.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			text = el.getFirstChild().getNodeValue();
		}
		return text;
	}

	private static int getIntValue(Element questionEl, String tagName) {
		String intValue = getTextValue(questionEl, tagName);
		if (!intValue.equals(""))
			return Integer.parseInt(intValue);
		else
			return 0;
	}

	public static void main(String[] args) {
		getQuizFromXml("src/xml/bunny.xml").saveToDatabase();
	}
}
