import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Soulution00 {

	public static void main(String[] args) throws Exception {
		
//		InputStreamReader isr = new InputStreamReader(System.in);
		File file = new File("input.txt");
		FileInputStream fis = new FileInputStream(file);
		
		InputStreamReader isr = new InputStreamReader(fis);
		
		
		BufferedReader br = new BufferedReader(isr);
		
		int T = Integer.parseInt(br.readLine());
		for (int t = 0; t < T; t++) {
			
		}

	}

}
