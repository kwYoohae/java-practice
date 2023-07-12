package next.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElapsedRunner {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

	@Test
	@DisplayName("테스트 5: @ElapsedTime이 붙어있는 어노테이션은 수행시간을 측정해야한다")
	void elapsedRunner() throws ReflectiveOperationException {
		Class clazz = ElapsedTest.class;
		final Constructor constructor = clazz.getConstructor();
		final Object o = constructor.newInstance();

		final Method[] declaredMethods = clazz.getDeclaredMethods();
		for (Method declaredMethod : declaredMethods) {
			if (declaredMethod.isAnnotationPresent(ElapsedTime.class)) {
				long start = System.currentTimeMillis();
				declaredMethod.invoke(o);
				long end = System.currentTimeMillis();
				logger.debug("time : {}", (end - start));
			}
		}

	}
}
