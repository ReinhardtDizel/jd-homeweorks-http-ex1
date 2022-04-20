package ru.netology;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String... args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        CloseableHttpResponse response = httpClient.execute(request);
        String json = EntityUtils.toString(response.getEntity());
        List<Fact> factList = objectMapper.readValue(json, new TypeReference<List<Fact>>() {
                })
                .stream()
                .filter(value -> value.getUpvotes() != null && value.getUpvotes() > 0)
                .collect(Collectors.toList());

        factList.forEach(fact -> {
            try {
                String res = objectWriter.writeValueAsString(fact);
                System.out.println(res);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

    }
}
