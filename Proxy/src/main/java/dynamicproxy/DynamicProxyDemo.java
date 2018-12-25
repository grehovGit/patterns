package dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class DynamicProxyDemo {
    public static void main(String[] args) {
        Person person = new Person();
        Human logged = withLogging(person, Human.class);
        logged.talk();
        logged.talk();
        logged.walk();
        logged.walk();


//        CAN NOT!!!
//        ((Person)logged).sleep();

        System.out.println(logged);
    }

    @SuppressWarnings("unchecked")
    public static <T> T withLogging(T target, Class<T> itf) {
        return (T) Proxy.newProxyInstance(
                itf.getClassLoader(),
                new Class<?>[] {itf},
                new LogginHandler(target)
        );
    }
}

interface Human {
    void walk();
    void talk();
}

class Person implements Human {

    @Override
    public void walk() {
        System.out.println("walkin");
    }

    @Override
    public void talk() {
        System.out.println("talking");
    }

    public void sleep() {
        System.out.println("sleeping");
    }




}

class LogginHandler implements InvocationHandler {

    private final Object target;
    private Map<String, Integer> calls = new HashMap<>();

    public LogginHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();

        if (name.contains("toString")) {
            return calls.toString();
        }
        calls.merge(name, 1, Integer::sum);
        return method.invoke(target, args);
    }
}