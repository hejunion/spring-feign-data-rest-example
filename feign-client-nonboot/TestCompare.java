
public class TestCompare {

	public static void main(String[] args) {
		String currentFileName="LLRA_Level_1_to_Level_3___Tax-1.csv";
		String newcurrentFileName;
		// TODO Auto-generated method stub
		String fileName =  currentFileName.substring(0, currentFileName.lastIndexOf(".csv"));
		if (fileName.endsWith("-1")) {
			fileName = fileName.substring(0, fileName.length() - 2);
			newcurrentFileName = fileName.concat("-2.csv");
		} else {
			newcurrentFileName = fileName.concat("-1.csv");
		}
		System.out.println(newcurrentFileName);
	}

}
