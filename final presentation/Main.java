package com.DSFinalProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Scanner;

public class Main {

	static boolean scoreboard = false;

	public static void main(String[] args) throws IOException {

		String fileName = "reducedData";
		System.out.println("NEWS HEADLINE SARCASM DETECTOR BY AARON SCHMITZ!");
		
		if (args.length>0) {
			if(args[0].equalsIgnoreCase("score")) {
			scoreboard = true;
			System.out.println("(scoreboard enabled)");
			}
		}
		
		System.out.println();

		while (1 != 0) {
			System.out.print("ENTER YOUR HEADLINE! >");
			Scanner headline = new Scanner(System.in);

			BufferedReader database = new BufferedReader(new FileReader(fileName));
			StringTokenizer itr = new StringTokenizer(headline.nextLine().toString());
			detectSarcasm(itr, database);
		}
	}

	public static void detectSarcasm(StringTokenizer headline, BufferedReader database) throws IOException {

		String seriousData = database.readLine();
		String sarcasticData = database.readLine();

		String serious[] = seriousData.split(" ");
		String sarcastic[] = sarcasticData.split(" ");

		int wordPoints = 0;
		int seriousPoints = 0;
		int sarcasticPoints = 0;
		double percent = 0.0;

		while (headline.hasMoreTokens()) {

			String word = headline.nextToken().toString();
			word = clean(word);

			wordPoints = 0;
			for (int i = 0; i < serious.length; i++) {
				if (word.equals(serious[i])) {
					wordPoints++;
					if (wordPoints >= 140) {
						break;
					}
				}
			}
			seriousPoints = seriousPoints + wordPoints;

			wordPoints = 0;
			for (int i = 0; i < sarcastic.length; i++) {
				if (word.equals(sarcastic[i])) {
					wordPoints++;
					if (wordPoints >= 140) {
						break;
					}
				}
			}
			sarcasticPoints = sarcasticPoints + wordPoints;
		}

		System.out.println("I THINK...");
		if (seriousPoints >= sarcasticPoints) {
			System.out.println("...your headline is UNLIKELY to be sarcastic");
			percent = (double) sarcasticPoints / (double) seriousPoints;
		}
		if (seriousPoints < sarcasticPoints) {
			System.out.println("...your headline is LIKELY to be sarcastic");
			percent = (double) seriousPoints / (double) sarcasticPoints;
		}

		if (scoreboard) {
			System.out.print("final score: serious " + seriousPoints + "; sarcastic " + sarcasticPoints + ";");

			if (percent >= 0.90) {
				System.out.println(" close call\n");
			} else {
				System.out.println("\n");
			}
		}else {
			System.out.println();
		}
	}

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
