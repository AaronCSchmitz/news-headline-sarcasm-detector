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

		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("score")) {
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
					if (wordPoints >= 155) {
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
			System.out.print("FINAL SCORE: serious " + seriousPoints + "; sarcastic " + sarcasticPoints + ";");

			if (percent >= 0.90) {
				System.out.println(" close call\n");
			} else {
				System.out.println("\n");
			}
		} else {
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
/*
		headline = headline.replaceAll("the", "");
		headline = headline.replaceAll("be", "");
		headline = headline.replaceAll("to", "");
		headline = headline.replaceAll("of", "");
		headline = headline.replaceAll("and", "");
		headline = headline.replaceAll("a", "");
		headline = headline.replaceAll("in", "");
		headline = headline.replaceAll("that", "");
		headline = headline.replaceAll("have", "");
		headline = headline.replaceAll("i", "");
		headline = headline.replaceAll("it", "");
		headline = headline.replaceAll("for", "");
		headline = headline.replaceAll("not", "");
		headline = headline.replaceAll("on", "");
		headline = headline.replaceAll("with", "");
		headline = headline.replaceAll("he", "");
		headline = headline.replaceAll("as", "");
		headline = headline.replaceAll("you", "");
		headline = headline.replaceAll("do", "");
		headline = headline.replaceAll("at", "");
		headline = headline.replaceAll("this", "");
		headline = headline.replaceAll("but", "");
		headline = headline.replaceAll("his", "");
		headline = headline.replaceAll("by", "");
		headline = headline.replaceAll("from", "");
		headline = headline.replaceAll("they", "");
		headline = headline.replaceAll("we", "");
		headline = headline.replaceAll("say", "");
		headline = headline.replaceAll("her", "");
		headline = headline.replaceAll("she", "");
		headline = headline.replaceAll("or", "");
		headline = headline.replaceAll("an", "");
		headline = headline.replaceAll("will", "");
		headline = headline.replaceAll("my", "");
		headline = headline.replaceAll("one", "");
		headline = headline.replaceAll("all", "");
		headline = headline.replaceAll("would", "");
		headline = headline.replaceAll("there", "");
		headline = headline.replaceAll("their", "");
		headline = headline.replaceAll("what", "");
		headline = headline.replaceAll("so", "");
		headline = headline.replaceAll("up", "");
		headline = headline.replaceAll("out", "");
		headline = headline.replaceAll("if", "");
		headline = headline.replaceAll("about", "");
		headline = headline.replaceAll("who", "");
		headline = headline.replaceAll("get", "");
		headline = headline.replaceAll("which", "");
		headline = headline.replaceAll("go", "");
		headline = headline.replaceAll("me", "");
		headline = headline.replaceAll("when", "");
		headline = headline.replaceAll("make", "");
		headline = headline.replaceAll("can", "");
		headline = headline.replaceAll("like", "");
		headline = headline.replaceAll("time", "");
		headline = headline.replaceAll("no", "");
		headline = headline.replaceAll("just", "");
		headline = headline.replaceAll("him", "");
		headline = headline.replaceAll("know", "");
		headline = headline.replaceAll("take", "");
		headline = headline.replaceAll("people", "");
		headline = headline.replaceAll("into", "");
		headline = headline.replaceAll("year", "");
		headline = headline.replaceAll("good", "");
		headline = headline.replaceAll("some", "");
		headline = headline.replaceAll("could", "");
		headline = headline.replaceAll("them", "");
		headline = headline.replaceAll("see", "");
		headline = headline.replaceAll("other", "");
		headline = headline.replaceAll("than", "");
		headline = headline.replaceAll("then", "");
		headline = headline.replaceAll("now", "");
		headline = headline.replaceAll("look", "");
		headline = headline.replaceAll("only", "");
		headline = headline.replaceAll("come", "");
		headline = headline.replaceAll("its", "");
		headline = headline.replaceAll("over", "");
		headline = headline.replaceAll("think", "");
		headline = headline.replaceAll("also", "");
		headline = headline.replaceAll("back", "");
		headline = headline.replaceAll("after", "");
		headline = headline.replaceAll("use", "");
		headline = headline.replaceAll("two", "");
		headline = headline.replaceAll("how", "");
		headline = headline.replaceAll("our", "");
		headline = headline.replaceAll("work", "");
		headline = headline.replaceAll("first", "");
		headline = headline.replaceAll("well", "");
		headline = headline.replaceAll("way", "");
		headline = headline.replaceAll("even", "");
		headline = headline.replaceAll("new", "");
		headline = headline.replaceAll("want", "");
		headline = headline.replaceAll("because", "");
		headline = headline.replaceAll("any", "");
		headline = headline.replaceAll("these", "");
		headline = headline.replaceAll("give", "");
		headline = headline.replaceAll("day", "");
		headline = headline.replaceAll("most", "");
		headline = headline.replaceAll("us", "");*/
		return headline;
	}

}
