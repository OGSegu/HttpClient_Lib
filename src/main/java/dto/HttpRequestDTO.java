package dto;

import utils.HttpMethod;

import java.util.Map;

public class HttpRequestDTO {

    private final HttpMethod methodType;
    private final String endpoint;
    private final Map<String, String> params;


    public HttpRequestDTO(HttpMethod methodType, String endpoint, Map<String, String> params) {
        this.methodType = methodType;
        this.endpoint = endpoint;
        this.params = params;
    }

    public HttpMethod getMethodType() {
        return methodType;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
