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
/**
 * 
 */
package util;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import quiz.FillInBlank;
import quiz.MAQuestion;
import quiz.MCMAQuestion;
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
		String creatorId = getTextValue(quizEle, "creator-id");
		List<String> tags = new ArrayList<String>();
		tags.add(getTextValue(quizEle, "tags"));

		List<QuestionBase> questionList = getQuestions(dom, creatorId);
		return new MyQuiz(quizName, creatorId, quizDescription, tags,
				canPractice, isRandom, isOnePage, isImmCorrection,
				questionList, createTime, category);

	}

	public static void exportQuizToXml(MyQuiz quiz, String xmlFile) {
		// get attributes from quiz
		String quizName = quiz.getQuizName();
		String quizDescription = quiz.getQuizDescription();
		String category = quiz.getCategory();
		String creatorId = quiz.getCreatorId();
		String tags = Helper.listToString(quiz.getTags());
		// Timestamp createTime = quiz.getCreateTime();
		boolean isRandom = quiz.isRandom();
		boolean isOnePage = quiz.isOnePage();
		boolean canPractice = quiz.isCanPractice();
		boolean isImmCorrection = quiz.isImmCorrection();
		List<QuestionBase> questionList = quiz.getQuestionList();

		try {

			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element quizElement = doc.createElement("quiz");
			doc.appendChild(quizElement);

			// set attribute to quiz root element
			Attr attr = doc.createAttribute("random");
			attr.setValue(Boolean.toString(isRandom));
			quizElement.setAttributeNode(attr);

			attr = doc.createAttribute("on-page");
			attr.setValue(Boolean.toString(isOnePage));
			quizElement.setAttributeNode(attr);

			attr = doc.createAttribute("practice-mode");
			attr.setValue(Boolean.toString(canPractice));
			quizElement.setAttributeNode(attr);

			attr = doc.createAttribute("immediate-correction");
			attr.setValue(Boolean.toString(isImmCorrection));
			quizElement.setAttributeNode(attr);

			// add title (quizName) element
			Element title = doc.createElement("title");
			title.appendChild(doc.createTextNode(quizName));
			quizElement.appendChild(title);

			// add description element
			Element descriptionElem = doc.createElement("description");
			descriptionElem.appendChild(doc.createTextNode(quizDescription));
			quizElement.appendChild(descriptionElem);

			// add category Element
			Element categoryElem = doc.createElement("category");
			categoryElem.appendChild(doc.createTextNode(category));
			quizElement.appendChild(categoryElem);

			// add creator-id element
			Element creatorIdElem = doc.createElement("creator-id");
			creatorIdElem.appendChild(doc.createTextNode(creatorId));
			quizElement.appendChild(creatorIdElem);

			// add tags element
			Element tagsElem = doc.createElement("tags");
			tagsElem.appendChild(doc.createTextNode(tags));
			quizElement.appendChild(tagsElem);

			// add question elements
			List<Element> questionElemList = getQuestionElements(questionList,
					doc);
			for (Element question : questionElemList) {
				quizElement.appendChild(question);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("src/xml/"
					+ xmlFile));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	private static List<Element> getQuestionElements(
			List<QuestionBase> questionList, Document doc) {
		List<Element> questionElemList = new ArrayList<Element>();
		for (QuestionBase question : questionList) {
			questionElemList.add(question.toElement(doc));
		}
		return questionElemList;
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
			String choices = parseList(questionEl, "option");
			String answer = parseAnswerAttribute(questionEl, "option");

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

		if (type.equals("multi-answer")) {
			String questionType = QuestionBase.MA;
			int timeLimit = getIntValue(questionEl, "time-limit");
			String questionDescription = getTextValue(questionEl, "query");
			String answer = parseList(questionEl, "answer");
			int maxScore = getIntValue(questionEl, "score");
			String tagString = getTextValue(questionEl, "tag");
			String isOrder = getAttribute(questionEl, "answer-list", "isorder");
			// at creation time, ratio = 0
			return new MAQuestion(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, 0,
					isOrder);
		}

		if (type.equals("multi-choice-multi-answer")) {
			String questionType = QuestionBase.MCMA;
			int timeLimit = getIntValue(questionEl, "time-limit");
			String questionDescription = getTextValue(questionEl, "query");
			String choices = parseList(questionEl, "option");
			String answer = parseAnswerAttribute(questionEl, "option");
			int maxScore = getIntValue(questionEl, "score");
			String tagString = getTextValue(questionEl, "tag");
			// at creation time, ratio = 0
			return new MCMAQuestion(questionType, creatorId, timeLimit,
					questionDescription, answer, maxScore, tagString, 0,
					choices);
		}

		return null;
	}

	private static String getAttribute(Element questionEl, String tagName,
			String attrName) {
		NodeList nl = questionEl.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			return el.getAttribute(attrName);
		}
		return "false";
	}

	// tagname is "blank-query"
	private static String parseBlankDescription(Element questionEl,
			String tagName) {
		String description = "";
		NodeList nl = questionEl.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			description += getTextValue(el, "pre");
			description += "#blank#";
			description += getTextValue(el, "post");
		}
		return description;
	}

	private static String parseAnswerAttribute(Element questionEl,
			String tagName) {
		List<String> answers = new ArrayList<String>();
		NodeList nl = questionEl.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element el = (Element) nl.item(i);
				if (el.getAttribute("answer").equals("answer"))
					answers.add(el.getFirstChild().getNodeValue());
			}
		}
		if (answers.size() == 1)
			return answers.get(0);
		else {
			String answerString = "";
			for (String answer : answers) {
				answerString += "#";
				answerString += answer;
				answerString += "#";
			}
			return answerString;
		}
	}

	/**
	 * 
	 * @param questionEl
	 * @param tagName
	 * @return
	 */
	private static String parseList(Element questionEl, String tagName) {
		String text = "";
		NodeList nl = questionEl.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				text += "#";
				Element el = (Element) nl.item(i);
				text += el.getFirstChild().getNodeValue();
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
		MyQuiz quiz = getQuizFromXml("src/xml/bunny.xml");
		quiz.saveToDatabase();
		exportQuizToXml(quiz, "testXml.xml");
	}
}
