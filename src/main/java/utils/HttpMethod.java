package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum HttpMethod {

    GET,
    HEAD,
    POST,
    PUT,
    DELETE;


    private static final Map<String, HttpMethod> httpMethodMap = new HashMap<>();

    static {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            httpMethodMap.put(httpMethod.name().toUpperCase(Locale.ROOT), httpMethod);
        }
    }

    public static HttpMethod getValue(String value) {
        return Arrays.stream(HttpMethod.values())
                .filter(httpMethod -> httpMethod.name()
                        .equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);
    }

    public static boolean contains(String value) {
        HttpMethod returnedMethod = httpMethodMap.get(value.toUpperCase(Locale.ROOT));
        return returnedMethod != null;
    }



}
