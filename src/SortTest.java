import java.util.ArrayList;
import java.util.Random;


public class SortTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<String> gArray = new ArrayList<String>();
		ArrayList<String> sArray = new ArrayList<String>();
		for (int i=1; i<=20; i++){
			sArray.add(""+i);
		}
		
		System.out.println("Original array, size=" + sArray.size());
		for (String s : sArray){
			System.out.print(s+" ");
		}
		System.out.println();
		
		
		Random r = new Random();
		int count = 1;
		for (int i=1; i<=count; i++){
			int index = (int) (r.nextDouble() * sArray.size());
			System.out.println("The generated random number: " + index);
			gArray.add(sArray.get(index));
			sArray.remove(index);
		}
		
				
		
		System.out.println("Modified array, size=" + sArray.size());
		for (String s : sArray){
			System.out.print(s+" ");
		}
		
		System.out.println();
		
		System.out.println("Generated array, size=" + gArray.size());
		for (String s : gArray){
			System.out.print(s+" ");
		}

	}

}
