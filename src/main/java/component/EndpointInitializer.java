package component;

import annonations.Endpoint;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

class EndpointInitializer {

    private final ComponentScanner scanner = new ComponentScanner();

    void initBeans() throws InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {

        Map<String, Class<?>> stringClassMap = scanner.scanForEndpoint(Endpoint.class);
        BeanHolder.beanMap = scanner.mergeClassWithAnnotatedMethods(stringClassMap.entrySet());

    }
}
