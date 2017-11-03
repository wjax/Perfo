package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;

import bb.util.Benchmark;

/**
 * @author Jesus
 */

public class Main {

	final static boolean GENERATE_TEST_STRINGS = false;
	
	final static String ALPHABET = "abcdefghijklmnopqrstuvwz ";
	
	static List<int[]> TESTS_SIZE = new ArrayList<int[]>();
			
	final static int ITER = 5;
	
	static String messageFilePath;
	static String soupFilePath;
	final static String statisticsFilePath = "test/statistics.txt";
	
	static Set<String> generatedTestFile;


	public static void main(String[] args) throws Exception {
		
		// Define Test cases
//		TESTS_SIZE.add(new int[]{10, 10_000_000});
//		TESTS_SIZE.add(new int[]{10, 1_000_000});
//		TESTS_SIZE.add(new int[]{10, 1_000});
		TESTS_SIZE.add(new int[]{100, 10_000_000});
//		TESTS_SIZE.add(new int[]{100, 1_000_000});
//		TESTS_SIZE.add(new int[]{100, 1_000});
		TESTS_SIZE.add(new int[]{100_000, 10_000_000});
//		TESTS_SIZE.add(new int[]{10_000, 1_000_000});
//		TESTS_SIZE.add(new int[]{10_000, 1_000});
		
		if (GENERATE_TEST_STRINGS)
		{
			GenerateTestStrings(TESTS_SIZE);
			System.exit(0);
		}
		
		// Read test number from console arguments. JVM need to be fresh for every single test
		int test2Perform = 0;
		if (args.length == 1) {
		    try {
		    	test2Perform = Integer.parseInt(args[0]);
		    } catch (Exception e)
		    {}
		}
		
		if (test2Perform <= 0 || test2Perform > 4)
		{
			System.out.println("Invalid args. Argument has to be single number, 1..4");
			System.exit(1);
		}
		
		// Open Statistics file
		PrintWriter writer = new PrintWriter (new FileWriter(statisticsFilePath, true));
		writer.println(new Date().toString());
		// Print Header
		writer.println("IMPLEMENTATION;MSG;SOUP;MEAN_TIME_NANO;RESULT");
		
		SoupManager soupManager = new SoupManager();
		
		for (int[] test : TESTS_SIZE)
		{
			int MSG_SIZE = test[0];
			int SOUP_SIZE = test[1];
		
			System.out.printf("Starting Test, MSG: %d, SOUP: %d%n", MSG_SIZE, SOUP_SIZE);
			
			String message;
			String soup;
			
			String messageFileName = "test/message_" + MSG_SIZE + ".txt";
			String soupFileName = "test/soup_" + SOUP_SIZE + ".txt";
			
			// Read Message and Soup
			try
			{
				message = new String(Files.readAllBytes(Paths.get(messageFileName)), StandardCharsets.UTF_8);
				soup = new String(Files.readAllBytes(Paths.get(soupFileName)), StandardCharsets.UTF_8);
			}
			catch (Exception e)
			{
				System.out.println("Skipping test. Missing files");
				break;
			}
				
			// Start Test
			Boolean result = false;
			double nsMean = 0;
			double nsMeanAverageITER = 0;
		
			// Benchmark Init
			Benchmark bb = null;
			Benchmark.Params params = new Benchmark.Params();
			params.setNumberMeasurements(5);
			params.setConsoleFeedback(false);
			
			Callable<Boolean> task = null;
			String implementation = "";
			
			for (int i = 0; i < ITER; i++)
			{
				switch (test2Perform)
				{
				case 1:
					// Original Method 
					implementation = "ORIGINAL";
					task = () -> {return soupManager.CheckMessageInSoupOriginal(message, soup);};
					break;
				case 2:
					// NOKEYCHAR Method
					implementation = "NOKEYCHAR";
					task = () -> {return soupManager.CheckMessageInSoup_NOKEYCHAR(message, soup);};
					break;
				case 3:
					// NOMUTABLE Method
					implementation = "NOMUTABLE";
					task = () -> {return soupManager.CheckMessageInSoup_NOMUTABLE(message, soup);};
					break;
				case 4:
					// NOKEYCHAR_NOMUTABLE Method
					implementation = "NOKEYCHAR_NOMUTABLE";
					task = () -> {return soupManager.CheckMessageInSoup_NOKEYCHAR_NOMUTABLE(message, soup);};
					break;
				}
				
				// Start Benchmark
				System.out.printf("IMPLEMENTATION '%s': ITERATION %d RUN%n", implementation, i);
				
				bb = new Benchmark(task, params);

				result = (Boolean) bb.getCallResult();	
				nsMean = bb.getMean()*1_000_000_000;
				
				writer.printf("%s;%d;%d;%f;%b%n", implementation, MSG_SIZE, SOUP_SIZE, nsMean, result );
				System.out.printf("IMPLEMENTATION '%s': ITERATION %d FINISH -- Time %f%n",implementation, i, nsMean);
				
				nsMeanAverageITER += nsMean;
				
			}
			System.out.printf("IMPLEMENTATION '%s': FINAL AVERAGE %f%n%n",implementation, nsMeanAverageITER/ITER);
			writer.printf("AVERAGE %s;%d;%d;%f;%b%n", implementation, MSG_SIZE, SOUP_SIZE, nsMeanAverageITER/ITER, result );
			
		}
		System.out.println("FINISH");
		writer.close();
	}
	
	private static String BuildRandomString(long size) {
		Random randomGenerator = new Random();
		StringBuilder builtString = new StringBuilder();

		for (int i = 0; i < size; i++)
			builtString.append(ALPHABET.charAt(randomGenerator.nextInt(ALPHABET.length())));

		return builtString.toString();
	}
	
	// Generate test strings
	private static void GenerateTestStrings(List<int[]> TESTS_SIZE) throws IOException
	{
		List<Integer> messages2Build = new ArrayList<Integer>(); 
		List<Integer> soup2Build = new ArrayList<Integer>(); 
		
		for (int[] test : TESTS_SIZE)
		{
			if (!messages2Build.contains(test[0]))
				messages2Build.add(test[0]);
			if (!soup2Build.contains(test[1]))
				soup2Build.add(test[1]);
		}
		
		Collections.sort(messages2Build, Collections.reverseOrder());
		Collections.sort(soup2Build, Collections.reverseOrder());
		
		// Get biggest String
		int seedMessageStringSize = messages2Build.get(0);
		int seedSoupStringSize = soup2Build.get(0);
		
		String seedMessageString = BuildRandomString(seedMessageStringSize);
		String seedSoupString = BuildRandomString(seedSoupStringSize);
		
		// Assume descending order
		for (int msgSize : messages2Build)
		{
			if (seedMessageString.length() != msgSize)
				seedMessageString = seedMessageString.substring(0, msgSize-1);
			
			Files.write(Paths.get("test/message_" + msgSize + ".txt"), seedMessageString.getBytes(StandardCharsets.UTF_8));
		}
		for (int soupSize : soup2Build)
		{
			if (seedSoupString.length() != soupSize)
				seedSoupString = seedSoupString.substring(0, soupSize-1);
			
			Files.write(Paths.get("test/soup_" + soupSize + ".txt"), seedSoupString.getBytes(StandardCharsets.UTF_8));
		}
		
//		if (GENERATE_TEST_STRINGS)
//		{
//			if (!generatedTestFile.contains(messageFileName))
//			{
//				Files.write(Paths.get(messageFileName), BuildRandomString(MSG_SIZE).getBytes(StandardCharsets.UTF_8));
//			}
//			if (!generatedTestFile.contains(messageFileName)) 
//			{
//				Files.write(Paths.get(soupFileName), BuildRandomString(SOUP_SIZE).getBytes(StandardCharsets.UTF_8));
//			}
//		}
	}

}

