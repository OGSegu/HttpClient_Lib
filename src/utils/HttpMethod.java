package utils;

public enum HttpMethod {

    GET,
    HEAD,
    POST,
    PUT,
    DELETE;

    public static boolean contains(String value) {
        for (HttpMethod httpMethodValue : HttpMethod.values()) {
            if (httpMethodValue.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
