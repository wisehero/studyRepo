package static_member;

public class StaticField {
	public static void main(String[] args) {
		MyScore s1 = new MyScore();
		MyScore s2 = new MyScore();
		s1.setLimit(90);
		System.out.println(s2.getLimit());
	}
}

class MyScore {
	private static int limit = 80;

	public void setLimit(int limit) {
		MyScore.limit = limit;
	}

	public int getLimit() {
		return limit;
	}
}
