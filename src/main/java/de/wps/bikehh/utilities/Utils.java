package de.wps.bikehh.utilities;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.wps.bikehh.adfc.material.SurveyTest;

public class Utils {

	public static List<SurveyTest> getSurveyJsonsAsArray() {
		return surveys();
	}

	public static List<SurveyTest> surveys() {
		List<SurveyTest> surveys = new ArrayList<SurveyTest>();

		// get path to ~/Dokumente/BikeHH/Umfragen/ for .json files
		// Path startDir = Paths.get(System.getProperty("user.home"), "Dokumente",
		// "BikeHH", "Umfragen");

		/**
		 * alternative path within project to minimized external errors like missing
		 * read rights for directory under MacOS
		 */
		Path startDir = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "survey-jsons");

		// get files in directory as jsonArray
		surveys = getJsonFilesAsSurveyList(startDir.toString());

		// debug only
		// System.out.print("surveys: " + surveys.toString());

		return surveys;
	}

	private static List<SurveyTest> getJsonFilesAsSurveyList(String startDir) {
		// JSONObjects in JSONArray to give to thymeleaf
		List<SurveyTest> listSurveys = new ArrayList<>();

		// try-catch block to handle exceptions
		try {
			File f = new File(startDir);
			System.out.println(f);

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

			// initialize parser
			@SuppressWarnings("deprecation")
			JsonParser parser = new JsonParser();

			Gson g = new Gson();

			// add every json file in files to jsonArray
			for (File jsonFile : files) {
				// pare jsonFile
				@SuppressWarnings("deprecation")
				Object obj = parser.parse(new FileReader(jsonFile));
				// convert to jsonObject
				JsonObject jsonObject = (JsonObject) obj;
				// add jsonObject to list of surveys
				SurveyTest survey = g.fromJson(jsonObject, SurveyTest.class);
				listSurveys.add(survey);
			}
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		System.out.println("Success building arrays from JSONs");
		return listSurveys;
	}

}
