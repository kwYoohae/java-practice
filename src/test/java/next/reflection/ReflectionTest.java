package next.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.optional.User;

class ReflectionTest {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

	@Test
	@DisplayName("테스트1: 리플렉션을 이용해서 클래스와 메소드의 정보를 정확하게 출력해야 한다.")
	void showClass() {
		Class<Question> clazz = Question.class;

		printField(clazz);

		logger.debug("=============================");

		printConstructor(clazz);

		logger.debug("=============================");

		printMethod(clazz);

		logger.debug("Classs Name {}", clazz.getName());
	}

	private void printMethod(final Class<Question> clazz) {
		final Method[] questionMethods = clazz.getDeclaredMethods();
		for (Method method : questionMethods) {
			logger.debug("method name : {}, modifier : {}, return type = {}",
				method.getName(), method.getModifiers(), method.getReturnType());

			printParameters(method.getParameters());
		}
	}

	private void printConstructor(final Class<Question> clazz) {
		final Constructor<?>[] questionConstructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : questionConstructors) {
			logger.debug("constructor name : {} , modifier : {}", constructor.getName(), constructor.getModifiers());

			printParameters(constructor.getParameters());
		}
	}

	private void printParameters(final Parameter[] constructor) {
		final Parameter[] parameters = constructor;
		for (Parameter parameter : parameters) {
			logger.debug("parameter name : {}, type : {}", parameter.getName(), parameter.getType());
		}
	}

	private void printField(final Class<Question> clazz) {
		final Field[] questionDeclaredFields = clazz.getDeclaredFields();
		for (Field field : questionDeclaredFields) {
			logger.debug("field type : {} , name : {}", field.getType(), field.getName());
		}
	}

	@Test
	void constructor() throws Exception {
		Class<User> clazz = User.class;
		final Constructor<?>[] constructors = clazz.getConstructors();
		User user = null;
		for (Constructor<?> constructor : constructors) {
			final Parameter[] parameters = constructor.getParameters();
			Object[] userConstructorParam = new Object[parameters.length];
			int index = 0;
			if (parameters.length > 0) {
				setConstructParameter(parameters, userConstructorParam, index);
				user = (User)constructor.newInstance(userConstructorParam);
				logger.debug(user.toString());
			}
		}
	}

	private void setConstructParameter(final Parameter[] parameters, final Object[] userConstructorParam, int index) throws
		ReflectiveOperationException {
		for (Parameter parameter : parameters) {
			final Class<?> type = parameter.getType();
			final Constructor<?>[] constructors = type.getDeclaredConstructors();
			for (Constructor<?> constructor : constructors) {
				if (constructor.getParameters().length == 0) {
					userConstructorParam[index] = constructor.newInstance();
					index++;
				}
			}
		}
	}

	@Test
	@DisplayName("테스트 4: Student의 private field에 값 할당해야한다.")
	void privateFieldAccess() throws ReflectiveOperationException {
		Class<Student> clazz = Student.class;

		final Student student = getStudent(clazz);

		logger.debug("name = {}, age = {}", student.getName(), student.getAge());
	}

	private Student getStudent(final Class<Student> clazz) throws ReflectiveOperationException {
		final Constructor<Student> constructor = clazz.getConstructor();
		final Student student = constructor.newInstance();

		final Field[] studentFields = clazz.getDeclaredFields();
		for (Field field : studentFields) {
			String fieldName = field.getName();
			field.setAccessible(true);
			setStudentField(student, field, fieldName);
		}
		return student;
	}

	private void setStudentField(final Student student, final Field field, final String fieldName) throws
		IllegalAccessException {
		if (fieldName.equals("name")) {
			field.set(student, "testName");
			return;
		}

		if (fieldName.equals("age")) {
			field.set(student, 20);
		}
	}
}
