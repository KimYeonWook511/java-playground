package section6;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class T25_LockFreeQueue {
	public static void main(String[] args) throws InterruptedException {
		// testNormalList();
		testLockFreeList();
	}

	private static void testNormalList() throws InterruptedException {
		MyList db = new MyList();
		int threadCount = 2;
		TestThread[] threads = new TestThread[threadCount];

		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < threadCount; i++)
			threads[i] = new TestThread(db);
		for (int i = 0; i < threadCount; i++)
			threads[i].start();
		for (int i = 0; i < threadCount; i++)
			threads[i].join();
		long endTime = System.currentTimeMillis();

		System.out.println("Duration: " + (endTime - beginTime) + " ms");
		System.out.println("getCount(): " + db.getCount());
	}

	private static void testLockFreeList() throws InterruptedException {
		LockFreeList db = new LockFreeList();
		// int threadCount = 2;
		int threadCount = 20;
		LockFreeTestThread[] threads = new LockFreeTestThread[threadCount];

		long beginTime = System.currentTimeMillis();
		for (int i = 0; i < threadCount; i++)
			threads[i] = new LockFreeTestThread(db);
		for (int i = 0; i < threadCount; i++)
			threads[i].start();
		for (int i = 0; i < threadCount; i++)
			threads[i].join();
		long endTime = System.currentTimeMillis();

		System.out.println("Duration: " + (endTime - beginTime) + " ms");
		System.out.println("getCount(): " + db.getCount());
	}

	private static class SpinLockBool {
		private final AtomicBoolean owner = new AtomicBoolean(false);

		public void lock() {
			while (!owner.compareAndSet(false, true))
				LockSupport.parkNanos(1);
		}

		public void unlock() {
			owner.set(false);
		}
	}

	private static class UserData {
		UserData(String name) {
			this.name = name;
		}
		String name;
		UserData next;
	}

	private static class MyList {
		//protected SpinLockBool lock = new SpinLockBool();
		protected ReentrantLock lock = new ReentrantLock();
		protected UserData headNode = new UserData("DummyHead");
		protected UserData tailPointer = headNode;

		public int getCount() {
			int count = 0;
			UserData tmp = headNode.next;
			while(tmp != null) {
				++count;
				tmp = tmp.next;
			}
			return count;
		}

		public void appendNode(String name) {
			UserData newUser = new UserData(name);
			lock.lock();
			tailPointer.next = newUser;
			tailPointer = newUser;
			lock.unlock();
		}
	}

	private static class LockFreeUser {
		LockFreeUser(String name) {
			this.name = name;
			next = new AtomicReference<LockFreeUser>(null);
		}
		String name;
		AtomicReference<LockFreeUser> next;
	}

	private static class LockFreeList {
		protected LockFreeUser headNode;
		protected AtomicReference<LockFreeUser> tail;

		LockFreeList() {
			headNode = new LockFreeUser("DummyHead");
			tail = new AtomicReference<LockFreeUser>(headNode);
		}

		public int getCount() {
			int count = 0;
			LockFreeUser tmp = headNode.next.get();
			while(tmp != null) {
				++count;
				tmp = tmp.next.get();
			}
			return count;
		}

		public void push(String name) {
			LockFreeUser newUser = new LockFreeUser(name);
			while (true) {
				LockFreeUser last = tail.get(); //last = tail;
				LockFreeUser next = last.next.get(); //next = last.next;
				if (last == tail.get()) { //last == tail;
					if (next == null) { //last node?
						if (last.next.compareAndSet(null, newUser)) {
							//last.next = newUser;
							tail.compareAndSet(last, newUser); //tail = newUser;
							return;
						}
					} else {
						// System.out.println("\t helping 동작");
						tail.compareAndSet(last, next); // 이건 없어도 되지 않나? - helping이라고 함 (있어야 성능 향상 됨)
						// helping이 없어도 정상적으로 동작은 함
						// 다만 있으면 뒤쳐지는 tail에 대해 미리미리 앞 당겨올 수 있어서 성능 향상이 된다고 함
						// -> 성능 향상하는 것을 체감하기 위해선 경쟁을 높이면 됨 (여러 스레드를 생성하기)
						// -> 체감이 안 됨. 오히려 helping 없는게 더 빠르기도 함..
					}
				}
				LockSupport.parkNanos(1);
			}
		}
	}

	private static class TestThread extends Thread {
		TestThread(MyList db) {
			list = db;
		}

		private final MyList list;
		@Override
		public void run() {
			for (int i = 0; i < 1000000; i++)
				list.appendNode("TEST" + i);
		}
	}

	private static class LockFreeTestThread extends Thread {
		LockFreeTestThread(LockFreeList db) {
			list = db;
		}

		private final LockFreeList list;
		@Override
		public void run() {
			for (int i = 0; i < 1000000; i++)
				list.push("TEST" + i);
		}
	}
}
