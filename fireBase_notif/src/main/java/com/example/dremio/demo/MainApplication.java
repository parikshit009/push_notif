package com.example.dremio.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class MainApplication {
	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(MainApplication.class, args);

		//g4tvuqglacm09h3u5vmlvpaivg

		Scanner s = new Scanner(System.in);
		System.out.println("Enter no. of Segments: ");
		int segment_no = s.nextInt();
		System.out.println("Enter name of Segments: ");
		s.nextLine();
		String segmentName = s.next();
		System.out.println("Enter PAT: ");
		s.nextLine();
		String patInput = s.next();

		Pat pat = new Pat(patInput);
		System.out.println(pat.token);

		PostQuery count = new PostQuery();
		int totalRecords=count.getCount(pat.token);
		int perSegment = totalRecords/segment_no;

		System.out.println(totalRecords);
		System.out.println(perSegment);
		
		ApiInitiator finalResult = new ApiInitiator();
		finalResult.initiate(totalRecords,perSegment,segmentName,pat.token);

	}
}
