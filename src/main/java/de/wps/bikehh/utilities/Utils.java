package de.wps.bikehh.utilities;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.wps.bikehh.adfc.material.SurveyTest;

public class Utils {

	private static Path startDir = Paths.get(System.getProperty("user.dir"), "Umfragen");

	public static List<SurveyTest> getSurveyJsonsAsArray() {
		List<SurveyTest> surveys = new ArrayList<SurveyTest>();

		// get files in directory as jsonArray
		surveys = getJsonFilesAsSurveyList(startDir.toString());

		return surveys;
	}

	/**
	 * Saves an JsonString in specified directory
	 *
	 * @param JSONString
	 */
	public static void saveJSONSurveyInFiles(String JSONString) {
		FileWriter file = null;
		int filenameCounter = (int) (Math.random() * 999999 + 1);
		String filename = "Umfrage" + String.valueOf(filenameCounter) + ".json";

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
			file.write(JSONString);
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

}
