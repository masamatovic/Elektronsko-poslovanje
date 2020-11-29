package com.project.online_library.Controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LibraryController {

    @GetMapping("/libraryController")
    public ResponseEntity<String> appB(){

        final String uri = "http://localhost:8082/securtyController";

        RestTemplate template = new RestTemplate();
       // String result =  restTemplate.getForObject(uri, ResponseEntity.class).toString();
        ResponseEntity<String> entity = template.getForEntity(uri, String.class);
        return entity;
    }


}
