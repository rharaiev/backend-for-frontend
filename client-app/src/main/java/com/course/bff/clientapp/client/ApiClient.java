package com.course.bff.clientapp.client;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiClient {
    private static final String DETAILS_URL = "http://localhost:8080/api/v1/details/";
    private static final String AUTH_TOKEN = "abc";

    RestTemplate restTemplate;
    HttpHeaders headers;

    public ApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, AUTH_TOKEN);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getDetails() throws URISyntaxException {
        return restTemplate.exchange(new URI(DETAILS_URL), HttpMethod.GET, new HttpEntity<>(headers), Map.class).getBody();
    }
}
