package ex.client.service;

import ex.client.dto.Req;
import ex.client.dto.UserRequest;
import ex.client.dto.UserResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class RestTemplateService {

    //http://localhost:8080/api/server/hello
    //response
    public UserResponse hello(){
        URI uri = UriComponentsBuilder
                .fromUriString(("http://localhost:9090"))
                .path("/api/server/hello")
                // queryParam을 사용하면 주소 뒤에 ? name=steve&age=10 이 붙게된다.
                .queryParam("name","aaaa")
                .queryParam("age",99)
                .encode()
                .build()
                .toUri();
        System.out.println(uri.toString());
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri,String.class);
        //여기서의 get은 http 메소드의 get임
        ResponseEntity<UserResponse> result2 = restTemplate.getForEntity(uri,UserResponse.class);
        System.out.println(result2.getStatusCode());
        System.out.println(result2.getBody());
        //헤더내용이나 상세한 정보를 얻기 위해서는 ResponseEntity를 활용해서 받는것이 좋다.

        return result2.getBody();
    }
    public UserResponse post(){
        //http://localhost:9090:api/server/user/{userId}/name/{userName}
        //유저를등록시키는것을 만들것임
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100, "steve") // 차례대로 괄호에 매칭된다.
                .toUri();
        System.out.println(uri);
        //http body -> object ->object mapper -> json ->rest template -> http body json

        UserRequest req = new UserRequest();
        req.setName("steve");
        req.setAge(10);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.postForEntity(uri,req,UserResponse.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getBody());
        return response.getBody();
    }
    public UserResponse exchange(){
        //http://localhost:9090:api/server/user/{userId}/name/{userName}
        //유저를등록시키는것을 만들것임
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100, "steve") // 차례대로 괄호에 매칭된다.
                .toUri();
        System.out.println(uri);
        //http body -> object ->object mapper -> json ->rest template -> http body json

        UserRequest req = new UserRequest();
        req.setName("steve");
        req.setAge(10);

        // 응답 받을때는 Response Entity였으면
        // 보낼때는 Request Entity
        RequestEntity<UserRequest> requestEntity =RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization","abcd")
                .header("custom-header","ffff")
                .body(req);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<UserResponse> response = restTemplate.exchange(requestEntity, UserResponse.class);
        return response.getBody();
    }

    public Req<UserResponse> genericExchange() {
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:9090")
                .path("/api/server/user/{userId}/name/{userName}")
                .encode()
                .build()
                .expand(100, "steve") // 차례대로 괄호에 매칭된다.
                .toUri();
        System.out.println(uri);
        //http body -> object ->object mapper -> json ->rest template -> http body json
        UserRequest userRequest = new UserRequest();
        userRequest.setName("steve");
        userRequest.setAge(10);

        Req req = new Req();
        req.setHeader(
                new Req.Header()
        );
        req.setResBody(
                userRequest
        );

        RequestEntity<Req<UserRequest>> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-authorization", "abcd")
                .header("custom-header", "ffff")
                .body(req);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Req<UserResponse>> response
                = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<>(){});

        //Req<UserResponse>>    rBody
        return response.getBody();
    }


}
