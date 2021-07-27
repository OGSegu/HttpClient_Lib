package component;

import annonations.Endpoint;
import org.junit.jupiter.api.Test;
import utils.HttpMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

class ComponentScannerTest {

    final ComponentScanner scanner = new ComponentScanner();


    @Test
    void scanTest() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Map<String, Class<?>> stringClassMap = scanner.scanForEndpoint(Endpoint.class);

        Map<String, BeanHolder.EndpointClassHolder> beanHolderMap = scanner.mergeClassWithAnnotatedMethods(stringClassMap.entrySet());


        BeanHolder.EndpointClassHolder endpointClassHolder = beanHolderMap.get("/user");
        Method method = endpointClassHolder.getMethods().get(HttpMethod.GET);
        method.invoke(endpointClassHolder.getClassInstance());

    }

}