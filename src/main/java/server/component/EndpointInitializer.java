package server.component;

import annonations.Endpoint;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class EndpointInitializer {

    private static final ComponentScanner scanner = new ComponentScanner();

    private EndpointInitializer() {
    }

    static void initBeans() throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {

        Map<String, Class<?>> stringClassMap = scanner.scanForEndpoint(Endpoint.class);
        BeanHolder.beanMap = scanner.mergeClassWithAnnotatedMethods(stringClassMap.entrySet());

    }
}
