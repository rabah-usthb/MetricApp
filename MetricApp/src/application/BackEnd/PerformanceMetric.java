package application.BackEnd;

import java.nio.file.Files;
import java.nio.file.Path;

import java.io.IOException;

public class PerformanceMetric {
	public long FileSize =0;
	public double RunTime=0;
	
	public PerformanceMetric(Path filePath){
		fetchFileSize(filePath);
		fetchRunTime(filePath);
	}
	
	void fetchFileSize(Path filePath) {
		try {
			this.FileSize = Files.size(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 void fetchRunTime(Path filePath) {
		
		long startTime = System.nanoTime();

        try {
            // Perform some operation, e.g., reading the file
            byte[] fileContent = Files.readAllBytes(filePath);
            System.out.println("File read successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Measure the end time
        long endTime = System.nanoTime();

        // Calculate the runtime
        this.RunTime = ((double)endTime - startTime)/1_000_000_000.0;

	}

}
