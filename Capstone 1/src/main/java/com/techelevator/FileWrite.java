package com.techelevator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileWrite {
	
	private LocalDateTime dateTime;
	private File logFile;

	public FileWrite() {
		logFile = new File("logFile.txt");
	}
	
	private String getDateTime() {
		dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return dateTime.format(formatter);
	}
	
	public void writeLog(String s) throws NullPointerException, FileNotFoundException {	
			try(FileWriter fWriter = new FileWriter(logFile, true)) {
				try(BufferedWriter bWriter = new BufferedWriter(fWriter)){
					try(PrintWriter writer = new PrintWriter(bWriter)){
						writer.println(getDateTime() + s); 
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}	
}
