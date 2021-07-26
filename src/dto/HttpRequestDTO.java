package dto;

import utils.HttpMethod;

public class HttpRequestDTO {

    private final HttpMethod methodType;
    private final String endpoint;

    public HttpRequestDTO(HttpMethod methodType, String endpoint) {
        this.methodType = methodType;
        this.endpoint = endpoint;
    }

    public HttpMethod getMethodType() {
        return methodType;
    }

    public String getEndpoint() {
        return endpoint;
    }

}
