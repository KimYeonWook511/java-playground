package section6;

import static java.lang.Thread.*;

import java.util.concurrent.locks.ReentrantLock;

public class T23_LockedLinkedList {

	private static MyList db = new MyList();

	public static void main(String[] args) throws InterruptedException {
		Thread consumer = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Consumer - begin");
				UserData node;
				while (true) {
					node = db.removeAtHead();
					if(node == null)
						try { sleep(1); }
						catch (InterruptedException e) {
							System.out.println("Consumer - interrupted");
							break;
						}
				}
				System.out.println("Consumer - end");
			}
		});

		Thread producer = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("producer - begin");
				for(int i = 0; i < 10000000; ++i)
					db.appendNode("tester" + i);
				System.out.println("producer - end");
			}
		});

		producer.start();
		consumer.start();
		sleep(5000);
		consumer.interrupt();

		db.printAll();
	}

	private static class UserData {
		UserData(String name) {
			this.name = name;
		}
		String name;
		UserData prev;
		UserData next;
	}

	private static class MyList {
		protected ReentrantLock lock = new ReentrantLock();
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

			lock.lock();
			newUser.prev = tail.prev;
			newUser.next = tail;
			tail.prev.next = newUser;
			tail.prev = newUser;

			++counter;
			lock.unlock();

			return true;
		}

		public boolean isEmpty() {
			return head.next == tail;
		}

		public void printAll() {
			System.out.println("-----------------------");
			
			lock.lock();
			System.out.println("Counter: " + counter);

			UserData tmp = head.next;
			while (tmp != tail) {
				System.out.println(tmp.name);
				tmp = tmp.next;
			}
			lock.unlock();

			System.out.println("-----------------------");
		}

		public UserData removeAtHead() {
			lock.lock();
			try {
				if (isEmpty()) {
					return null;
				}

				// lock.lock(); // 이렇게 하면 inEmpty가 false였다가 다른 스레드에서 remove해서 empty가 되는 경우 아래에서 문제 생김
				UserData node = head.next;
				node.next.prev = head;
				head.next = node.next;

				--counter;

				return node;
			} finally {
				lock.unlock();
			}
		}
	}

}
