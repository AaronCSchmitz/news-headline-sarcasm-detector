package com.DSFinalProject;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

public class CountSort {

	public static class TokenizerMapper extends Mapper<Object, Text, IntWritable, Text> {

		private IntWritable sum = new IntWritable();
		private Text word = new Text();
		private IntWritable neg = new IntWritable(-1);

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				sum.set(Integer.valueOf(itr.nextToken())*-1);
				context.write(sum, word);
			}
		}
	}

	public static class IntSumReducer extends Reducer<IntWritable, Text, Text, IntWritable> {
		
		public void reduce(IntWritable key, Iterator<Text> values, Context context)
				throws IOException, InterruptedException {
			IntWritable sum = new IntWritable();
			Text word = new Text();
			int temp = 0;
			while(values.hasNext()) {
				temp = key.get()*-1;
				word = values.next();
			}
			sum.set(temp);
			context.write(word, sum);
		}
	}

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		System.setProperty("hadoop.home.dir", "C:\\Program Files\\Hadoop\\hadoop-2.8.0-RC3");

		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "word count");
		job.setJarByClass(WordCount.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}