
public class Tester {

	public static void main(String[] args) {
		int eight = 805;
		int pri = 1373;
		int sol = 397;
		
		for (int a = 1; a < 1373; a++) {
			if (((a*eight) % pri) == sol)
			{
				System.out.println(a);
			}
		}

	}

}
