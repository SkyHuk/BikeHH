package de.wps.bikehh.utilities;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.wps.bikehh.adfc.material.Question;
import de.wps.bikehh.adfc.material.SurveyTest;

public class Utils {

	private static Path startDir = Paths.get(System.getProperty("user.dir"), "Umfragen");

	/**
	 * Gets surveys as List<SurveyTest> from JSON strings in storage
	 * 
	 * @return List<SurveyTest>
	 */
	public static List<SurveyTest> getSurveyJsonsAsArray() {
		List<SurveyTest> surveys = new ArrayList<SurveyTest>();

		// get files in directory as jsonArray
		surveys = getJsonFilesAsSurveyList(startDir.toString());

		return surveys;
	}

	// OLD METHOD, replaced by saveJSONSurveyInFiles()
	public static void saveJSONSurveyInFilesOld(String JSONString) {
		FileWriter file = null;
		int filenameCounter = (int) (Math.random() * 999999 + 1);
		String filename = "Umfrage" + String.valueOf(filenameCounter) + ".json";

		// get SurveyTest from jsonString to validate jsonString
		Gson g = new Gson();
		SurveyTest surveyTest = g.fromJson(JSONString, SurveyTest.class);

		// save file
		try {
			// create file
			File fileToSaveIn = new File(startDir + "/" + filename);
			if (fileToSaveIn.createNewFile()) {
				System.out.println("File created: " + fileToSaveIn.getName());
			} else {
				System.out.println("File already exists.");
			}

			// write to file
			file = new FileWriter(startDir + "/" + filename);
			// covnert SurveyTest to jsonString
			String jsonString = g.toJson(surveyTest);
			// old: file.write(JSONString);
			file.write(jsonString);
			System.out.println("Successfully Copied JSON Object " + filename + " to " + startDir);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				file.flush();
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Saves an JsonString in specified directory
	 *
	 * @param JSONString
	 * @throws ParseException
	 */
	public static void saveJSONSurveyInFiles(String JSONString) throws IllegalArgumentException, ParseException {
		FileWriter file = null;
		int filenameCounter = (int) (Math.random() * 999999 + 1);
		String filename = "Umfrage" + String.valueOf(filenameCounter) + ".json";

		// get SurveyTest from jsonString to validate jsonString
		Gson g = new Gson();
		SurveyTest surveyTest = g.fromJson(JSONString, SurveyTest.class);

		Date dateFrom = null;
		Date dateTo = null;

		// format dates to validate Date simple
		if (surveyTest.getDateFrom() != null) {
			// possible ParseException
			dateFrom = convertStringDateToDate(surveyTest.getDateFrom());
		}
		if (surveyTest.getDateTo() != null) {
			// possible ParseException
			dateFrom = convertStringDateToDate(surveyTest.getDateTo());
		}

		// validate Survey and save to files
		if (surveyTest.getTitle() != null && !surveyTest.getTitle().isEmpty() && // title is not empty
				(dateFrom.after(getCurrentDate()) || dateFrom.equals(getCurrentDate())) && // dateFrom is greater or
																							// equals currentDate()
				(dateFrom.after(dateTo) || dateFrom.equals(dateTo)) && // dateFrom is greater or equals dateTo
				surveyTest.getQuestions() != null && // questions are not null
				questionsHaveTitle(surveyTest.getQuestions()) && questionsHaveAnswer(surveyTest.getQuestions()) && // questions
																													// have
																													// title
																													// and
																													// answer
				surveyTest.getCreatedAtDate() != null && !surveyTest.getCreatedAtDate().isEmpty() && // createdDate is
																										// not null and
																										// not empty
				surveyTest.getLat() != 0 && surveyTest.getLng() != 0 && // lat, long are not 0
				surveyTest.getCategory() != null && !surveyTest.getCategory().isEmpty() && // category is not null and
																							// not empty
				surveyTest.getConfirmedByUsers() != null && // confirmedByUsers array is not null
				surveyTest.getCreator() != null && // creator is not null
				!surveyTest.getCreator().isEmpty() && surveyTest.getAddress() != null) { // address is not null

			// TODO
			// save file
			try {
				// create file
				File fileToSaveIn = new File(startDir + "/" + filename);
				if (fileToSaveIn.createNewFile()) {
					System.out.println("File created: " + fileToSaveIn.getName());
				} else {
					System.out.println("File already exists.");
				}

				// write to file
				file = new FileWriter(startDir + "/" + filename);
				// covnert SurveyTest to jsonString
				String jsonString = g.toJson(surveyTest);
				// old: file.write(JSONString);
				file.write(jsonString);
				System.out.println("Successfully Copied JSON Object " + filename + " to " + startDir);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					file.flush();
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			// throw Exception
			throw new IllegalArgumentException("Survey" + surveyTest + " has not the right format. File is not saved!");
		}
	}

	/**
	 * Returns jsonStrings in file directory as List<SurveyTest>
	 * 
	 * @param startDir
	 * @return List<SurveyTest>
	 */
	private static List<SurveyTest> getJsonFilesAsSurveyList(String startDir) {
		// JSONObjects in JSONArray to give to thymeleaf
		List<SurveyTest> listSurveys = new ArrayList<>();

		// try-catch block to handle exceptions
		try {
			File f = new File(startDir);

			FilenameFilter filter = new FilenameFilter() {
				@Override
				public boolean accept(File f, String name) {
					// We want to find only .json files
					return name.endsWith(".json");
				}
			};

			// array of all files with specific ending
			File[] files = f.listFiles(filter);

			// prints numbers of files found in dir, for testing
			System.out.println("Numbers of files found: " + files.length);

			Gson g = new Gson();

			// add every json file in files to jsonArray
			for (File jsonFile : files) {
				// parse file
				Object obj = JsonParser.parseReader(new FileReader(jsonFile));
				// convert to jsonObject
				JsonObject jsonObject = (JsonObject) obj;
				// add jsonObject to list of surveys
				SurveyTest survey = g.fromJson(jsonObject, SurveyTest.class);
				listSurveys.add(survey);
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		System.out.println("Success building Surveys from JSONs");
		return listSurveys;
	}

	/**
	 * Returns the current date as yyyy-MM-dd as a Date
	 * 
	 * @return Date
	 */
	private static Date getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		System.out.println(formatter.format(date));
		return date;
	}

	/**
	 * Converts a String to a date
	 * 
	 * @param stringDate String
	 * @return Date
	 * @throws ParseException
	 */
	private static Date convertStringDateToDate(String stringDate) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
	}

	/**
	 * Validates questions array and checks if every question has a title
	 * 
	 * @param questions
	 * @return true, if questions is valid
	 */
	private static boolean questionsHaveTitle(Question[] questions) {
		boolean bQuestionsHaveTitle = false;
		for (Question question : questions) {
			if (question.getTitle() != null && !question.getTitle().isEmpty()) {
				bQuestionsHaveTitle = true;
			} else {
				return false;
			}
		}

		return bQuestionsHaveTitle;
	}

	/**
	 * Validates questions array and checks if every question has an answer
	 * 
	 * @param questions
	 * @return true, if questions is valid
	 */
	private static boolean questionsHaveAnswer(Question[] questions) {
		boolean bQuestionsHaveAnswer = false;
		for (Question question : questions) {
			if (question.getAnswers() != null && question.getAnswers().size() > 0) {
				bQuestionsHaveAnswer = true;
			} else {
				return false;
			}
		}

		return bQuestionsHaveAnswer;
	}

}
