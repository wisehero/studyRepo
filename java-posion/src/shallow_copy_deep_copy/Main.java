package shallow_copy_deep_copy;

public class Main {
	public static void main(String[] args) {
		User user1 = new User("Jiwoong", "Hanam", "010-1234-5678");
		User user2 = new User("Hosung", "Seoul", "010-9876-5432");

		System.out.println(user1.getName()); // Jiwoong
		System.out.println(user1.getAddress().address); // Hanam
		System.out.println(user1.getAddress().phone); // 010-1234-5678

		user1.copy(user2); // shallow copy
		user2.getAddress().phone = "010-1111-2222";
		user2.getAddress().address = "Busan";

		System.out.println(user1.getName()); // Hosung
		System.out.println(user1.getAddress().address); // Busan
		System.out.println(user1.getAddress().phone); // 010-1111-2222

	}
}

class Address {

	public String address;
	public String phone;

	public Address(String address, String phone) {
		this.address = address;
		this.phone = phone;
	}
}

class User {
	private String name;
	private Address address;

	public User(String name, String address, String phone) {
		this.name = name;
		this.address = new Address(address, phone);
	}

	public Address getAddress() {
		return address;
	}

	public String getName() {
		return name;
	}

	public void copy(User rhs) {
		this.name = rhs.name;
		this.address = rhs.address;
	}

	public void deepCopy(User rhs) {
		this.name = rhs.name;
		this.address = new Address(rhs.address.address, rhs.address.phone);
	}
}
