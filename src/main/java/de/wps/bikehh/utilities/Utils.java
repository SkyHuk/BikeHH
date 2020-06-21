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

import de.wps.bikehh.adminplattform.material.Question;
import de.wps.bikehh.adminplattform.material.Umfrage;

public class Utils {

	private static Path startDir = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "Umfragen");

	/**
	 * Gets surveys as List<SurveyTest> from JSON strings in storage
	 * 
	 * @return List<SurveyTest>
	 */
	public static List<Umfrage> getSurveyJsonsAsArray() {
		List<Umfrage> surveys = new ArrayList<Umfrage>();

		// get files in directory as jsonArray
		surveys = getJsonFilesAsSurveyList(startDir.toString());

		return surveys;
	}

	public static Umfrage getUmfrageById(int Id) {
		List<Umfrage> umfragen = getSurveyJsonsAsArray();
		for (Umfrage umfrage : umfragen) {
			if (umfrage.getId() == Id) {
				return umfrage;
			}
		}
		return null;
	}

	/**
	 * Saves an JsonString in specified directory
	 *
	 * @param JSONString
	 * @throws ParseException
	 */
	public static void saveJSONSurveyInFiles(String JSONString) throws IllegalArgumentException, ParseException {
		FileWriter file = null;

		// get SurveyTest from jsonString to validate jsonString
		Gson g = new Gson();
		Umfrage surveyTest = g.fromJson(JSONString, Umfrage.class);

		int filenameCounter = surveyTest.getId();
		String filename = "Umfrage" + String.valueOf(filenameCounter) + ".json";

		if (surveyTest != null) {

			Date dateFrom = null;
			Date dateTo = null;

			// format dates to validate Date simple
			if (surveyTest.getDateFrom() != null) {
				// possible ParseException
				dateFrom = convertStringDateToDate(surveyTest.getDateFrom());
				// System.out.println("dateFrom successfully parsed: " + dateFrom);
			}
			if (surveyTest.getDateTo() != null) {
				// possible ParseException
				dateTo = convertStringDateToDate(surveyTest.getDateTo());
				// System.out.println("dateTo successfully parsed: " + dateTo);
			}

//			// important for debugging
//			if ((dateFrom.after(getCurrentDate()) || dateFrom.equals(getCurrentDate()))) {
//				System.out.println("dateFrom.after(getCurrentDate()) || dateFrom.equals(getCurrentDate()) returns: "
//						+ (dateFrom.after(getCurrentDate()) || dateFrom.equals(getCurrentDate())));
//			} else {
//				System.out.println("dateFrom.after(getCurrentDate()) || dateFrom.equals(getCurrentDate()) returns: "
//						+ (dateFrom.after(getCurrentDate()) || dateFrom.equals(getCurrentDate())));
//			}
//
//			if ((dateTo.after(dateFrom) || dateTo.equals(dateFrom))) {
//				System.out.println("(dateTo.after(dateFrom) || dateTo.equals(dateFrom)) returns: "
//						+ (dateTo.after(dateFrom) || dateTo.equals(dateFrom)));
//			} else {
//				System.out.println("(dateTo.after(dateFrom) || dateTo.equals(dateFrom)) returns: "
//						+ (dateTo.after(dateFrom) || dateTo.equals(dateFrom)));
//			}

			System.out.println("Starting validation if ...");

			// TODO test condition array
			// validate Survey and save to files
			if (surveyTest.getTitle() != null && !surveyTest.getTitle().isEmpty() && dateFrom != null && dateTo != null
					&& (dateFrom.after(getCurrentDate()) || dateFrom.equals(getCurrentDate()))
					&& (dateTo.after(dateFrom) || dateTo.equals(dateFrom)) && surveyTest.getQuestions() != null
					&& surveyTest.getQuestions().length > 0 && questionsHaveTitle(surveyTest.getQuestions())
					&& questionsHaveAnswer(surveyTest.getQuestions()) && surveyTest.getCreatedAtDate() != null
					&& !surveyTest.getCreatedAtDate().isEmpty() && surveyTest.getLat() != 0 && surveyTest.getLng() != 0
					&& surveyTest.getCategory() != null && !surveyTest.getCategory().isEmpty()
					&& surveyTest.getConfirmedByUsers() != null && surveyTest.getCreator() != null
					&& !surveyTest.getCreator().isEmpty() && surveyTest.getAddress() != null) {

				System.out.println("JSONString is valid. JSON is saved to storage ...");

				// save file
				try {
					// create file
					File fileToSaveIn = new File(startDir + "/" + filename);
					if (fileToSaveIn.createNewFile()) {
						System.out.println("File created: " + fileToSaveIn.getName());
					} else {
						System.out.println("File already exists, will be overwritten.");
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
		} else {
			System.out.println("----- If-condition failed -----");
//			// print survey
//			System.out.println("----- survey info -----");
//			System.out.println("id: " + surveyTest.getId());
//			System.out.println("lng: " + surveyTest.getLng());
//			System.out.println("lat: " + surveyTest.getLat());
//			System.out.println("category: " + surveyTest.getCategory());
//			System.out.println("dateFrom: " + surveyTest.getDateFrom());
//			System.out.println("dateTo: " + surveyTest.getDateTo());
//			System.out.println("createdAtDate: " + surveyTest.getCreatedAtDate());
//			System.out.println("confirmedByUsers: " + surveyTest.getConfirmedByUsers());
//			System.out.println("confirmedThreshhold: " + surveyTest.getConfirmedThreshhold());
//			System.out.println("title: " + surveyTest.getTitle());
//			System.out.println("questions: " + surveyTest.getQuestions());
//			System.out.println("creator: " + surveyTest.getCreator());
//			System.out.println("createdManually: " + surveyTest.isCreatedManually());
//			System.out.println("address: " + surveyTest.getAddress().toString());

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
	private static List<Umfrage> getJsonFilesAsSurveyList(String startDir) {
		// JSONObjects in JSONArray to give to thymeleaf
		List<Umfrage> listSurveys = new ArrayList<>();

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
				Umfrage survey = g.fromJson(jsonObject, Umfrage.class);
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
	 * @throws ParseException
	 */
	private static Date getCurrentDate() throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		// System.out.println("getCurrentDate returns: " +
		// convertStringDateToDate(formatter.format(date)));
		return convertStringDateToDate(formatter.format(date));
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
				System.out.println("questionsHaveTitle return: " + false);
				return false;
			}
		}
		// System.out.println("questionsHaveTitle return: " + bQuestionsHaveTitle);
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
				System.out.println("questionsHaveAnswer return: " + false);
				return false;
			}
		}
		// System.out.println("questionsHaveAnswer return: " + bQuestionsHaveAnswer);
		return bQuestionsHaveAnswer;
	}
}

/*
 * // OLD METHOD, replaced by saveJSONSurveyInFiles() public static void
 * saveJSONSurveyInFilesOld(String JSONString) { FileWriter file = null; int
 * filenameCounter = (int) (Math.random() * 999999 + 1); String filename =
 * "Umfrage" + String.valueOf(filenameCounter) + ".json";
 * 
 * // get SurveyTest from jsonString to validate jsonString Gson g = new Gson();
 * SurveyTest surveyTest = g.fromJson(JSONString, SurveyTest.class);
 * 
 * // save file try { // create file File fileToSaveIn = new File(startDir + "/"
 * + filename); if (fileToSaveIn.createNewFile()) {
 * System.out.println("File created: " + fileToSaveIn.getName()); } else {
 * System.out.println("File already exists."); }
 * 
 * // write to file file = new FileWriter(startDir + "/" + filename); // covnert
 * SurveyTest to jsonString String jsonString = g.toJson(surveyTest); // old:
 * file.write(JSONString); file.write(jsonString);
 * System.out.println("Successfully Copied JSON Object " + filename + " to " +
 * startDir); } catch (IOException e) { e.printStackTrace(); } finally { try {
 * file.flush(); file.close(); } catch (IOException e) { e.printStackTrace(); }
 * } }
 */
