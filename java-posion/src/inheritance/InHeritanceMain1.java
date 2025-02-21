package inheritance;

public class InHeritanceMain1 {
	public static void main(String[] args) {
		MyTestEx t = new MyTestEx();
		System.out.println(t.getData()); // 10
		System.out.println(t.getchildData()); // 20
	}
}

class MyTest {
	private int parentData = 10;

	public int getData() {
		return parentData;
	}

	public void printData() {
		System.out.println(parentData);
	}
}

class MyTestEx extends MyTest {
	private int childData = 20;

	public int getchildData() {
		return childData;
	}
}
