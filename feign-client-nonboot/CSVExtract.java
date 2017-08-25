

import java.io.*;
import java.nio.file.Files;

public class CSVExtract {
	
	static String ERROR1 ="DOES NOT EXIST IN THE METADATA REPOSITORY"; 

	public static void main(String[] args) throws IOException {
		String fileName = args[0];
		iteriate(fileName);
	}

	static void iteriate(String fileName) throws IOException{
		File inputFile = new File(fileName);
		//if (!inputFile.exists() ) {
		//	System.exit(-1);
		//}
		
		if (inputFile.isDirectory()){
			for (String aFile: inputFile.list() ){
				iteriate(inputFile.getAbsolutePath()+"\\"+aFile);
			};
		}
		else {
			// extractFile(fileName);
			// System.out.println("Found file:" + fileName);
			//
			cleanErrorFile(fileName);
		}
		
	}
	
	static void cleanErrorFile(String fileName){
		File csvFile = new File(fileName);
		
		
		String[] files = getFilePath2 (fileName);
		
		String csvFileName = files[1];
		
		if ( files[0].endsWith("Archive")) {
			String[] files2 = getFilePath2(files[0] );
			String upperFolder = files2[0];
			String errorFolder = upperFolder +"\\Error";
			String errorFile = errorFolder+"\\"+csvFileName;
			File errorCsvFile = new File(errorFile);
			if ( errorCsvFile.exists() && isOlder(errorCsvFile, csvFile) )  {
				
				System.out.print("Found old:" + errorFile);
			    //errorCsvFile.delete();
				System.out.println("-----Deleted.");
			}	
		}
		
	}
	
	static boolean isOlder(File file1, File file2){
		return file1.lastModified() < file2.lastModified();
	}
	
	static String[] getFilePath2(String fileName){
		String[] name2 = new String[2];
		
		String[] names = fileName.split("\\\\");
		name2[1]= names[names.length-1];
		name2[0] =fileName.substring(0, fileName.length() - name2[1].length() -1);
		
		return name2;
		
	}
	
	static void extractFile(String fileName) throws IOException{
		System.out.println(fileName);
		String[] names = fileName.split("\\\\");
		System.out.println(names[names.length-1]);
		

		File file = new File(fileName);
		BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		String outFileName = fileName +".err";
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outFileName)));


		
		for ( String line; (line = bfr.readLine())!= null; ){
			if ( contains(line, ERROR1) ) {
				System.out.println(line);
				out.println(line);
			}
		};
		out.close();
		
	}
	
	static boolean contains(String source, String toFind){
		return source.matches(".*"+toFind+".*");
	}

}
