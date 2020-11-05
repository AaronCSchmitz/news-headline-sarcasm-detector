package com.DSFinalProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Scanner;

public class MainAccuracyTest {

	static boolean scoreboard = false;
	static int totalRun = 0;
	static int totalCorrect = 0;
	static int totalser = 0;
	static int totalsar = 0;
	static int sercorrect = 0;
	static int sarcorrect = 0;
	static int ser = 0;
	static int sar = 0;
	static String correct = "";

	public static void main(String[] args) throws IOException {

//		String fileName = args[0];
		String inputFile = "mappedData";
		String fileName = "reducedData";
//		System.err.println(fileName);
//		System.out.println("NEWS HEADLINE SARCASM DETECTOR BY AARON SCHMITZ!");

//		if (args.length>0) {
//			if(args[0].equalsIgnoreCase("score")) {
//			scoreboard = true;
		System.out.println("(scoreboard enabled)");
//			}
//		}

		System.out.println();

		BufferedReader input = new BufferedReader(new FileReader(inputFile));
		int i = 0;
		while (input.ready()) {
//			System.out.print("ENTER YOUR HEADLINE! >");
//			@SuppressWarnings("resource")
//			Scanner headline = new Scanner(System.in);

			correct = "INCORRECT";

			String headline = input.readLine().toString();
			char temp = headline.charAt(0);
			int answer = Integer.valueOf(temp);
			headline = headline.substring(2, headline.length());

			BufferedReader database = new BufferedReader(new FileReader(fileName));
			StringTokenizer itr = new StringTokenizer(headline.toString());

			detectSarcasm(itr, database, answer);
			i++;

			if (correct.equals("CORRECT!")) {
				System.out.println(
						i + ", " + headline + ", final score: serious " + ser + "; sarcastic " + sar + "; " + correct);
			} else {
				System.err.println(
						i + ", " + headline + ", final score: serious " + ser + "; sarcastic " + sar + "; " + correct);
			}
		}
		System.out.println("FINAL RESULTS: " + totalCorrect + " / " + i);
		System.out.println(
				"SERIOUS: " + sercorrect + " / " + totalser + " ; SARCASTIC: " + sarcorrect + " / " + totalsar);
	}

	public static void detectSarcasm(StringTokenizer headline, BufferedReader database, int answer) throws IOException {

		String seriousData = database.readLine();
		String sarcasticData = database.readLine();

		String serious[] = seriousData.split(" ");
		String sarcastic[] = sarcasticData.split(" ");

		int wordPoints = 0;
		int seriousPoints = 0;
		int sarcasticPoints = 0;
		double percent = 0.0;

		if (answer == 48) {
			totalser++;
		}
		if (answer == 49) {
			totalsar++;
		}

		while (headline.hasMoreTokens()) {

			String word = headline.nextToken().toString();
			word = clean(word);

			wordPoints = 0;
			for (int i = 0; i < serious.length; i++) {
				if (word.equals(serious[i])) {
					wordPoints++;
					if (wordPoints >= 134) {
						break;
					}
				}
			}
			seriousPoints = seriousPoints + wordPoints;

			wordPoints = 0;
			for (int i = 0; i < sarcastic.length; i++) {
				if (word.equals(sarcastic[i])) {
					wordPoints++;
					if (wordPoints >= 146) {
						break;
					}
				}
			}
			sarcasticPoints = sarcasticPoints + wordPoints;
		}

//		System.out.println("I THINK...");
		if (seriousPoints >= sarcasticPoints) {
//			System.out.println("...your headline is UNLIKELY to be sarcastic");
			if (answer == 48) {
				totalCorrect++;
				sercorrect++;
				correct = "CORRECT!";
			}
			percent = (double) sarcasticPoints / (double) seriousPoints;
		}
		if (seriousPoints < sarcasticPoints) {
//			System.out.println("...your headline is LIKELY to be sarcastic");
			percent = (double) seriousPoints / (double) sarcasticPoints;
			if (answer == 49) {
				totalCorrect++;
				sarcorrect++;
				correct = "CORRECT!";
			}
		}

		ser = seriousPoints;
		sar = sarcasticPoints;
		return;

		/*		if (scoreboard) {
					System.out.print("final score: serious " + seriousPoints + "; sarcastic " + sarcasticPoints + ";");
		
					if (percent >= 0.90) {
						System.out.println(" close call\n");
					} else {
						System.out.println("\n");
					}
				}else {
					System.out.println("\n");
				}
			*/}

	public static String clean(String headline) {
		headline = headline.toLowerCase();
		headline = headline.replaceAll("'s", "");
		headline = headline.replaceAll("\'", "");
		headline = headline.replaceAll("\"", " ");
		headline = headline.replaceAll("\\,", "");
		headline = headline.replaceAll("\\.", "");
		headline = headline.replaceAll("\\;", "");
		headline = headline.replaceAll("\\:", "");
		headline = headline.replaceAll("\\:", "");
		headline = headline.replaceAll("\\(", "");
		headline = headline.replaceAll("\\)", "");
		headline = headline.replaceAll("\\“", "");
		headline = headline.replaceAll("\\”", "");
		headline = headline.replaceAll("\\[", "");
		headline = headline.replaceAll("\\]", "");
		headline = headline.replaceAll("\\{", "");
		headline = headline.replaceAll("\\}", "");
		headline = headline.replaceAll("\\…", "");
		headline = headline.replaceAll("\\-", " ");
		headline = headline.replaceAll("\\–", " ");
		headline = headline.replaceAll("\\—", " ");
		headline = headline.replaceAll("\\_", " ");
		headline = headline.replaceAll("\\?", " \\? ");
		headline = headline.replaceAll("\\!", " \\! ");
		headline = headline.replaceAll("\\#", " \\# ");
		headline = headline.replaceAll("\\$", " \\$ ");
		return headline;
	}

}
