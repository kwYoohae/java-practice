package next.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Junit3Runner {

    @Test
    @DisplayName("테스트 2: @test 어노테이션으로 시작하는 메서드만 실행되어야한다.")
    public void runner() throws Exception {
        Class clazz = Junit3Test.class;
        final Method[] junit3Methods = clazz.getDeclaredMethods();

        final Constructor constructor = clazz.getConstructor();
        final Object Junit3Test = constructor.newInstance();

        for (Method method : junit3Methods) {
            final String name = method.getName();
            if (name.startsWith("test")) {
                method.invoke(Junit3Test);
            }
        }
    }
}
