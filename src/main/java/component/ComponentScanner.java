package component;

import annonations.Endpoint;
import exception.MultipleHttpAnnotationsException;
import org.reflections.Reflections;
import utils.HttpMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class ComponentScanner {

    private static final Logger logger = Logger.getAnonymousLogger();

    /*
        1. On init scan all classes with endpoint annotation and also store its methods annotated with get,post and etc.
        Format: Map<T, A>
        T - endpoint (probably string)
        A - class instance, methods (get, post, etc only)

        httpRequest -> value = map.get(httpRequest.getEndpoint()) -> method = value.getMethod(HttpMethod.GET) -> method.invoke(value.getInstance(), params[])
   */

    Map<String, Class<?>> scanForEndpoint(Class<Endpoint> annotation) {
        Map<String, Class<?>> resultMap = new HashMap<>();

        Reflections reflections = new Reflections("");
        Set<Class<?>> endpointClasses = reflections.getTypesAnnotatedWith(annotation);
        for (Class<?> endpointClass : endpointClasses) {
            Endpoint endpointClassAnnotation = endpointClass.getAnnotation(annotation);
            String routeValue = endpointClassAnnotation.route();
            resultMap.put(routeValue, endpointClass);
        }
        return resultMap;
    }

    Map<String, BeanHolder.EndpointClassHolder> mergeClassWithAnnotatedMethods(Iterable<Map.Entry<String, Class<?>>> classIterable) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {

        Map<String, BeanHolder.EndpointClassHolder> resultMap = new HashMap<>();

        for (Map.Entry<String, Class<?>> entry : classIterable) {
            Map<HttpMethod, Method> methodMap = new EnumMap<>(HttpMethod.class);

            Object endpointClassObject = entry.getValue().getDeclaredConstructor().newInstance();

            Method[] declaredMethods = entry.getValue().getDeclaredMethods();

            for (Method declaredMethod : declaredMethods) {
                try {
                    if (declaredMethod.getAnnotations().length == 0) {
                        continue;
                    }
                    validateAnnotations(declaredMethod);
                    HttpMethod httpAnnotation = getHttpAnnotation(declaredMethod);

                    if (httpAnnotation != null) {
                        methodMap.put(httpAnnotation, declaredMethod);
                    }
                } catch (MultipleHttpAnnotationsException e) {
                    logger.log(Level.WARNING, e.getMessage());
                }
            }
            resultMap.put(entry.getKey(), new BeanHolder.EndpointClassHolder(endpointClassObject, methodMap));
        }
        return resultMap;
    }




    private void validateAnnotations(Method method) throws MultipleHttpAnnotationsException {
        Annotation[] annotations = method.getAnnotations();

        int httpMethodAnnotationCounter = 0;

        for (Annotation annotation : annotations) {
            if (HttpMethod.contains(annotation.annotationType().getSimpleName())) {
                httpMethodAnnotationCounter++;
            }
        }

        if (httpMethodAnnotationCounter > 1) {
            throw new MultipleHttpAnnotationsException("More than one HttpMethod annotation on method: " + method);
        }
    }

    private HttpMethod getHttpAnnotation(Method method) {
        Annotation[] annotations = method.getAnnotations();

        for (Annotation annotation : annotations) {
            String annotationName = annotation.annotationType().getSimpleName();
            if (HttpMethod.contains(annotationName)) {
                return HttpMethod.getValue(annotationName);
            }
        }

        return null;
    }

}
