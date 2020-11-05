package com.DSFinalProject;

import org.apache.log4j.BasicConfigurator;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.json.*;

public class HeadlineSplitter {

	public static class Map extends Mapper<LongWritable, Text, IntWritable, Text> {

		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String headline;
			int is_sarcastic;
			String line = value.toString();
			String[] tuple = line.split("\\n");

			try {
				for (int i = 0; i < tuple.length; i++) {
					JSONObject obj = new JSONObject(tuple[i]);
					is_sarcastic = obj.getInt("is_sarcastic");
					headline = obj.getString("headline");
					headline = clean(headline);
					context.write(new IntWritable(is_sarcastic), new Text(headline));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public static class Reduce extends Reducer<IntWritable, Text, Text, Text> {
		Text output = new Text();
		String out;
		Text label = new Text();

		public void reduce(IntWritable key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {

			if (key.get() == 0) {
				out = " ";
				label.set("%ser%");
			}
			if (key.get() == 1) {
				out = " ";
				label.set("%sar%");
			}

			for (Text val : values) {
				out += val.toString() + " ";
			}

			output.set(out);
			context.write(label, output);
			return;
		}
	}

	public static String clean(String headline) {
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

/*		headline = headline.replaceAll("the ", " ");
		headline = headline.replaceAll("how ", " ");
		headline = headline.replaceAll("are ", " ");
		headline = headline.replaceAll("in ", " ");
		headline = headline.replaceAll("is ", " ");
		headline = headline.replaceAll("only ", " ");
		headline = headline.replaceAll("to ", " ");
		headline = headline.replaceAll("be ", " ");
		
		headline = headline.replaceAll(" the ", " ");
		headline = headline.replaceAll(" be ", " ");
		headline = headline.replaceAll(" to ", " ");
		headline = headline.replaceAll(" of ", " ");
		headline = headline.replaceAll(" and ", " ");
		headline = headline.replaceAll(" a ", " ");
		headline = headline.replaceAll(" in ", " ");
		headline = headline.replaceAll(" that ", " ");
		headline = headline.replaceAll(" have ", " ");
		headline = headline.replaceAll(" i ", " ");
		headline = headline.replaceAll(" it ", " ");
		headline = headline.replaceAll(" for ", " ");
		headline = headline.replaceAll(" not ", " ");
		headline = headline.replaceAll(" on ", " ");
		headline = headline.replaceAll(" with ", " ");
		headline = headline.replaceAll(" he ", " ");
		headline = headline.replaceAll(" as ", " ");
		headline = headline.replaceAll(" you ", " ");
		headline = headline.replaceAll(" do ", " ");
		headline = headline.replaceAll(" at ", " ");
		headline = headline.replaceAll(" this ", " ");
		headline = headline.replaceAll(" but ", " ");
		headline = headline.replaceAll(" his ", " ");
		headline = headline.replaceAll(" by ", " ");
		headline = headline.replaceAll(" from ", " ");
		headline = headline.replaceAll(" they ", " ");
		headline = headline.replaceAll(" we ", " ");
		headline = headline.replaceAll(" say ", " ");
		headline = headline.replaceAll(" her ", " ");
		headline = headline.replaceAll(" she ", " ");
		headline = headline.replaceAll(" or ", " ");
		headline = headline.replaceAll(" an ", " ");
		headline = headline.replaceAll(" will ", " ");
		headline = headline.replaceAll(" my ", " ");
		headline = headline.replaceAll(" one ", " ");
		headline = headline.replaceAll(" all ", " ");
		headline = headline.replaceAll(" would ", " ");
		headline = headline.replaceAll(" there ", " ");
		headline = headline.replaceAll(" their ", " ");
		headline = headline.replaceAll(" what ", " ");
		headline = headline.replaceAll(" so ", " ");
		headline = headline.replaceAll(" up ", " ");
		headline = headline.replaceAll(" out ", " ");
		headline = headline.replaceAll(" if ", " ");
		headline = headline.replaceAll(" about ", " ");
		headline = headline.replaceAll(" who ", " ");
		headline = headline.replaceAll(" get ", " ");
		headline = headline.replaceAll(" which ", " ");
		headline = headline.replaceAll(" go ", " ");
		headline = headline.replaceAll(" me ", " ");
		headline = headline.replaceAll(" when ", " ");
		headline = headline.replaceAll(" make ", " ");
		headline = headline.replaceAll(" can ", " ");
		headline = headline.replaceAll(" like ", " ");
		headline = headline.replaceAll(" time ", " ");
		headline = headline.replaceAll(" no ", " ");
		headline = headline.replaceAll(" just ", " ");
		headline = headline.replaceAll(" him ", " ");
		headline = headline.replaceAll(" know ", " ");
		headline = headline.replaceAll(" take ", " ");
		headline = headline.replaceAll(" people ", " ");
		headline = headline.replaceAll(" into ", " ");
		headline = headline.replaceAll(" year ", " ");
		headline = headline.replaceAll(" good ", " ");
		headline = headline.replaceAll(" some ", " ");
		headline = headline.replaceAll(" could ", " ");
		headline = headline.replaceAll(" them ", " ");
		headline = headline.replaceAll(" see ", " ");
		headline = headline.replaceAll(" other ", " ");
		headline = headline.replaceAll(" than ", " ");
		headline = headline.replaceAll(" then ", " ");
		headline = headline.replaceAll(" now ", " ");
		headline = headline.replaceAll(" look ", " ");
		headline = headline.replaceAll(" only ", " ");
		headline = headline.replaceAll(" come ", " ");
		headline = headline.replaceAll(" its ", " ");
		headline = headline.replaceAll(" over ", " ");
		headline = headline.replaceAll(" think ", " ");
		headline = headline.replaceAll(" also ", " ");
		headline = headline.replaceAll(" back ", " ");
		headline = headline.replaceAll(" after ", " ");
		headline = headline.replaceAll(" use ", " ");
		headline = headline.replaceAll(" two ", " ");
		headline = headline.replaceAll(" how ", " ");
		headline = headline.replaceAll(" our ", " ");
		headline = headline.replaceAll(" work ", " ");
		headline = headline.replaceAll(" first ", " ");
		headline = headline.replaceAll(" well ", " ");
		headline = headline.replaceAll(" way ", " ");
		headline = headline.replaceAll(" even ", " ");
		headline = headline.replaceAll(" new ", " ");
		headline = headline.replaceAll(" want ", " ");
		headline = headline.replaceAll(" because ", " ");
		headline = headline.replaceAll(" any ", " ");
		headline = headline.replaceAll(" these ", " ");
		headline = headline.replaceAll(" give ", " ");
		headline = headline.replaceAll(" day ", " ");
		headline = headline.replaceAll(" most ", " ");
		headline = headline.replaceAll(" us ", " ");*/
		return headline;
	}

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		System.setProperty("hadoop.home.dir", "C:\\Program Files\\Hadoop\\hadoop-2.8.0-RC3");

		Configuration conf = new Configuration();
		if (args.length != 2) {
			System.err.println("Usage: HeadlineSplitter <in> <out>");
			System.exit(2);
		}

		Job job = Job.getInstance(conf, "headline splitter");
		job.setJarByClass(HeadlineSplitter.class);
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);

		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}