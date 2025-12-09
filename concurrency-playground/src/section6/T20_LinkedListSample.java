package section6;

public class T20_LinkedListSample {
	public static void main(String[] args) {
		MyList db = new MyList();

		db.appendNode("tester01");
		db.appendNode("tester02");
		db.appendNode("tester03");
		db.printAll();

		db.removeAtHead();
		db.printAll();
		db.removeAtHead();
		db.printAll();
		db.removeAtHead();
		db.printAll();
	}
}

class UserData {
	UserData(String name) {
		this.name = name;
	}
	String name;
	UserData prev;
	UserData next;
}

class MyList {
	protected int counter = 0;
	protected UserData head = new UserData("DummyHead");
	protected UserData tail = new UserData("DummyTail");
	MyList() {
		head.next = tail;
		tail.prev = head;
	}

	public int size() {
		return counter;
	}

	public boolean appendNode(String name) {
		UserData newUser = new UserData(name);
		newUser.prev = tail.prev;
		newUser.next = tail;
		tail.prev.next = newUser;
		tail.prev = newUser;

		++counter;
		return true;
	}

	public boolean isEmpty() {
		if(head.next == tail)
			return true;

		return false;
	}

	public void printAll() {
		System.out.println("-----------------------");
		System.out.println("Counter: " + counter);
		UserData tmp = head.next;
		while(tmp != tail) {
			System.out.println(tmp.name);
			tmp = tmp.next;
		}
		System.out.println("-----------------------");
	}

	public UserData removeAtHead() {
		if(isEmpty())
			return null;

		UserData node = head.next;
		node.next.prev = head;
		head.next = node.next;

		--counter;
		return node;
	}
}