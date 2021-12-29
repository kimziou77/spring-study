package com.example.restaurant.naver;

import com.example.restaurant.naver.dto.SearchImageReq;
import com.example.restaurant.naver.dto.SearchImageRes;
import com.example.restaurant.naver.dto.SearchLocalReq;
import com.example.restaurant.naver.dto.SearchLocalRes;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NaverClient {
    @Value("${naver.client.id}")
    private String naverClientId;
    @Value("${naver.client.secret}")
    private String naverClientSecret;

    @Value("${naver.url.search.local}")
    private String naverLocalSearchUrl;
    @Value("${naver.url.search.image}")
    private String naverImageSearchUrl;

    private HttpHeaders headers;

    public void headerInit(){
        headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id",naverClientId);
        headers.set("X-Naver-Client-Secret", naverClientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    public SearchLocalRes searchLocal(SearchLocalReq searchLocalReq){
        URI uri = UriComponentsBuilder.fromUriString(naverLocalSearchUrl)
                .queryParams(searchLocalReq.toMultiValueMap())
                .build()
                .encode()
                .toUri();

        headerInit();
        var httpEntity = new HttpEntity<>(headers);
        var responseType = new ParameterizedTypeReference<SearchLocalRes>(){};


        var responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );
        return responseEntity.getBody();
    }

    public SearchImageRes searchImage(SearchImageReq searchImageReq){
        URI uri = UriComponentsBuilder.fromUriString(naverImageSearchUrl)
                .queryParams(searchImageReq.toMultiValueMap())
                .build()
                .encode()
                .toUri();
        headerInit();

        var httpEntity = new HttpEntity<>(headers);
        var responseType = new ParameterizedTypeReference<SearchImageRes>(){};
        var responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );

        return responseEntity.getBody();

    }
}
