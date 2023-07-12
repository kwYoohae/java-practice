package next.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

public class Junit3Runner {
    @Test
    public void runner() throws Exception {
        Class clazz = Junit3Test.class;
        final Method[] declaredMethods = clazz.getDeclaredMethods();

        final Constructor constructor = clazz.getConstructor();
        final Object Junit3Test = constructor.newInstance();

        for (Method declaredMethod : declaredMethods) {
            final String name = declaredMethod.getName();
            if (name.startsWith("test")) {
                declaredMethod.invoke(Junit3Test);
            }
        }
    }
}
