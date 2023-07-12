package next.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Junit4Runner {

    @Test
    @DisplayName("테스트3: @MyTest 애노테이션으로 설정되어 있는 메소드만 실행")
    public void run() throws Exception {
        Class clazz = Junit4Test.class;

        final Constructor constructor = clazz.getConstructor();
        final Object Junit4Test = constructor.newInstance();

        final Method[] junit4Methods = clazz.getDeclaredMethods();
        for (Method method : junit4Methods) {
            if (method.isAnnotationPresent(MyTest.class)) {
                method.invoke(Junit4Test);
            }
        }
    }
}


