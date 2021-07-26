package service;

import dto.HttpRequestDTO;
import exception.HttpSyntaxException;
import utils.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestMapper {

    private HttpRequestMapper() {
    }

    public static HttpRequestDTO parse(String httpRequest) throws HttpSyntaxException {
        String[] splitByLine = httpRequest.split("\r\n");
        String[] httpMainHeader = splitByLine[0].split(" ");

        HttpMethod httpRequestMethod = getHttpMethod(httpMainHeader[0]);

        String[] endpointSplit = httpMainHeader[1].split("\\?");
        String endpointWithoutParams = endpointSplit[0];

        if (endpointSplit.length == 1) {
            return new HttpRequestDTO(httpRequestMethod, endpointWithoutParams, null);
        } else {
            String rawParams = endpointSplit[1];
            Map<String, String> paramsMap = parseParams(rawParams);
            return new HttpRequestDTO(httpRequestMethod, endpointWithoutParams, paramsMap);
        }
    }

    private static HttpMethod getHttpMethod(String httpMethod) throws HttpSyntaxException {
        if (HttpMethod.contains(httpMethod)) {
            return HttpMethod.valueOf(httpMethod);
        } else {
            throw new HttpSyntaxException("Http method is not valid: " + httpMethod);
        }
    }

    private static Map<String, String> parseParams(String endpoint) {
        Map<String, String> resultMap = new HashMap<>();
        String paramSplitter = "&";
        String keyValueSplitter = "=";

        String[] paramsPairArray = endpoint.split(paramSplitter);

        for (String paramPair : paramsPairArray) {
            String[] keyValueArray = paramPair.split(keyValueSplitter);
            resultMap.put(keyValueArray[0], keyValueArray[1]);
        }

        return resultMap;
    }

}
