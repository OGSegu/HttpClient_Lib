package service;

import dto.HttpRequestDTO;
import exception.HttpSyntaxException;
import utils.HttpMethod;

public class HttpRequestMapper {

    private HttpRequestMapper() {
    }

    public static HttpRequestDTO parse(String httpRequest) throws HttpSyntaxException {
        String[] splitByLine = httpRequest.split("\r\n");
        String[] httpMainHeader = splitByLine[0].split(" ");

        HttpMethod httpRequestMethod = getHttpMethod(httpMainHeader);
        String endpoint = httpMainHeader[1];

        return new HttpRequestDTO(httpRequestMethod, endpoint);
    }

    private static HttpMethod getHttpMethod(String[] httpMainHeader) throws HttpSyntaxException {
        if (HttpMethod.contains(httpMainHeader[0])) {
            return HttpMethod.valueOf(httpMainHeader[0]);
        } else {
            throw new HttpSyntaxException("Http Method is not valid: " + httpMainHeader[0]);
        }
    }

}
