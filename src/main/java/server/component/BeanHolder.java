package server.component;

import utils.HttpMethod;

import java.lang.reflect.Method;
import java.util.Map;

public class BeanHolder {

    static Map<String, EndpointClassHolder> beanMap;


    private BeanHolder () { }


    static class EndpointClassHolder {

        private final Object classInstance;
        private final Map<HttpMethod, Method> methods;

        public EndpointClassHolder(Object classInstance, Map<HttpMethod, Method> methods) {
            this.classInstance = classInstance;
            this.methods = methods;
        }

        public Object getClassInstance() {
            return classInstance;
        }

        public Map<HttpMethod, Method> getMethods() {
            return methods;
        }
    }
}
