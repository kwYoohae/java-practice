package next.reflection;

public class ElapsedTest {

	@ElapsedTime
	void elapsedTimeTest() throws InterruptedException {
		Thread.sleep(3000L);
	}

	void nonElapsedTimeTest() {
		System.out.println("이건 안됩니다~");
	}
}
