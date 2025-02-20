package copy_constructor;

public class Main {
	public static void main(String[] args) {
		MyTest t1 = new MyTest(3);
		t1.setAt(0, 10);
		t1.setAt(1, 20);
		t1.setAt(2, 30);

		MyTest t2 = new MyTest(3);
		t2.shallowCopy(t1);
		MyTest t3 = new MyTest(3);
		t3.deepCopy(t1);
		MyTest t4 = new MyTest(t1);

		t1.setAt(0, -1);
		System.out.println("t1[0] = " + t1.getAt(0)); // t1 원본
		System.out.println("t2[0] = " + t2.getAt(0)); // t2 얕은 복사
		System.out.println("t3[0] = " + t3.getAt(0)); // t3 깊은 복사
		System.out.println("t4[0] = " + t4.getAt(0)); // t4 복사 생성자
	}
}

class MyTest {
	private int[] array = null;

	public MyTest(int size) {
		array = new int[size];
	}

	public MyTest(MyTest rhs) {
		this(rhs.array.length);
		this.deepCopy(rhs);
	}

	public int getAt(int index) {
		return array[index];
	}

	public void setAt(int index, int value) {
		array[index] = value;
	}

	public void shallowCopy(MyTest rhs) {
		array = rhs.array;
	}

	public void deepCopy(MyTest rhs) {
		// rhs.array를 0번째부터 rhs.array.length만큼 array에 복사
		System.arraycopy(rhs.array, 0, array, 0, array.length);
	}
}
