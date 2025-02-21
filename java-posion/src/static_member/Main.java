package static_member;

public class Main {
	public static void main(String[] args) {
		MyTest test = new MyTest();
		test.setData(15);
		test.printData(test);
	}
}

class MyTest {
	private int data;

	public void setData(int param) {
		this.data = param;
	}

	public int getDate() {
		return this.data;
	}

	public static void printData(MyTest obj) {
		System.out.println(obj.data);
	}
}
