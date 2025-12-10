package section6;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 *  CAS(Compare-And-Swap) 연산
 *  - Lock-Free 구조를 구현하기 위한 핵심 원리
 *  - 별도의 동기화 코드(Lock) 없이 원자적 연산 가능
 *  	- 변수 값 확인, 비교, 교환 등 여러 연산을 하나 CPU 연산으로 처리
 *  	- H/W 수준에서 동기화 보장
 *  - CPU의 Atomic instruction 사용
 *  	- cmpxchg (Compare and Exchange)
 *
 *
 *  Spin Lock
 *  - Lock을 획득할 때 까지 반복문을 수행함 (CPU 타임을 계속 사용함으로써 스레드가 Runnable 상태를 유지하면서 동기화)
 *  - 스레드 스위칭에 따른 오버헤드를 제거하는 방식으로 향상된 성능을 얻는 방식임
 *  	- 임계 영역 코드가 짧고 경쟁하는 스레드 개수가 적을 수록 유리함
 *  	- 구현하기에 따라 오히려 성능이 저하 될 수 있음
 * 	- CAS(Compare-And-Swap)로 구현
 *
 *
 * 	** 의문점
 * 	// 하이브리드 스핀 락 (순수 스핀 락이랑 다름!! -> TIMED_WAITING 상태로 가짐)
 * 	// (BLOCKED 상태로 되었다가 깨는 Lock구조보다 문맥 교환이 적다고 하는데 잘 이해 못 했음!!!)
 * 	// 의문1. synchronized같은 Lock을 쓰는 것도 똑같이 문맥 교환이 일어나고 TIMED_WAITING도 마찬가지 아닌가?
 * 	// 의문2. 하이브리드 스핀 락 vs BLOCKED 락 이걸 비교하여 효율성을 따지는 것은 어떤 것을 기준으로 판단하는 것인지?
 * 	// 의문3. 하이브리드 스핀
 */
public class T24_SpinLock {
	public class Main {
		public static void main(String[] args) throws InterruptedException {
			MyList db = new MyList();
			int threadCount = 5;
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
			System.out.println("size: " + db.size());
			System.out.println("getCount(): " + db.getCount());
		}

		// 단순 lock이 되었는지만 확인하는 경우엔 참조형태는 비효율
		private static class SpinLock {
			private final AtomicReference<Thread> owner = new AtomicReference<>();

			public void lock() {
				Thread currentThread = Thread.currentThread();
				// CAS를 이용하여 락 획득 (반복적으로 시도)
				while (!owner.compareAndSet(null, currentThread)) {
					// 계속 시도 (바쁜 대기 busy-wait, Spin)
				}
			}

			public void unlock() {
				Thread currentThread = Thread.currentThread();
				// 현재 스레드만 락을 해제할 수 있음
				owner.compareAndSet(currentThread, null);
			}

			public boolean isLocked() {
				return owner.get() != null;
			}
		}

		private static class SpinLockBool {
			private final AtomicBoolean owner = new AtomicBoolean(false);

			public void lock() {
				while (!owner.compareAndSet(false, true)) {
					LockSupport.parkNanos(1); // 하이브리드 스핀 락 (순수 스핀 락이랑 다름!! -> TIMED_WAITING 상태로 가짐)
					// (BLOCKED 상태로 되었다가 깨는 Lock구조보다 문맥 교환이 적다고 하는데 잘 이해 못 했음!!!)
					// 의문1. synchronized같은 Lock을 쓰는 것도 똑같이 문맥 교환이 일어나고 TIMED_WAITING도 마찬가지 아닌가?
					// 의문2. 하이브리드 스핀 락 vs BLOCKED 락 이걸 비교하여 효율성을 따지는 것은 어떤 것을 기준으로 판단하는 것인지?
					// 의문3. 하이브리드 스핀 락이 BLOCKED가 되는 락보다 문맥 교환 발생 빈도가 적다는 것이 이해가 안 됨!!
				}
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
			//protected SpinLock lock = new SpinLock(); //2100ms
			protected SpinLockBool lock = new SpinLockBool(); //1150 ms
			//protected ReentrantLock lock = new ReentrantLock(); //1230 ms
			protected AtomicInteger counter = new AtomicInteger();
			protected UserData head = new UserData("DummyHead");

			public int size() {
				return counter.get();
			}

			public int getCount() {
				int count = 0;
				UserData tmp = head.next;
				while(tmp != null) {
					++count;
					tmp = tmp.next;
				}
				return count;
			}

			public boolean appendNode(String name) {
				UserData newUser = new UserData(name);

				lock.lock();
				newUser.next = head.next;
				head.next = newUser;
				lock.unlock();

				counter.incrementAndGet(); // 기존 임계 구간에서 제외됨
				return true;
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
	}
}
