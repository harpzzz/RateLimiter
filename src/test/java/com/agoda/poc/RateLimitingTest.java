package com.agoda.poc;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

@SpringBootTest(classes = PocApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RateLimitingTest {

    @LocalServerPort
    private int port;


    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    String API_URI1 = "/api/v1/city/";
    String API_URI2 = "/api/v1/test4/";
    String API_URI3 = "/api/v1/test1/";
    String API_URI4 = "/api/v1/test2/";
    String API_URI5 = "/api/v1/test3/";
    String API_URI6 = "/api/v1/test6";




    @Test
    public void testRateExhausted() {
        // same url diff param

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        for(int i=0;i<10;i++) {
            ResponseEntity<String> response = restTemplate.exchange(
                    createURLWithPort(API_URI1+"h1"),
                    HttpMethod.GET, entity, String.class);
            Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
        }
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(API_URI1+"h2?sortBy=0"),
                HttpMethod.GET, entity, String.class);
        Assert.assertEquals(HttpStatus.SERVICE_UNAVAILABLE,response.getStatusCode());

    }

    @Test
    public void testNewURL(){

        //defauld condition check
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        for(int i=0;i<50;i++) {
            ResponseEntity<String> response1 = restTemplate.exchange(
                    createURLWithPort(API_URI5),
                    HttpMethod.GET, entity, String.class);
            Assert.assertEquals(HttpStatus.NOT_FOUND,response1.getStatusCode());
                 }
        ResponseEntity<String> response1 = restTemplate.exchange(
                createURLWithPort(API_URI5),
                HttpMethod.GET, entity, String.class);
        Assert.assertEquals(HttpStatus.SERVICE_UNAVAILABLE,response1.getStatusCode());


    }

    @Test
    public void testRunMutipleAPi(){

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        for(int i=0;i<10;i++) {
            ResponseEntity<String> response1 = restTemplate.exchange(
                    createURLWithPort(API_URI3),
                    HttpMethod.GET, entity, String.class);
            Assert.assertEquals(HttpStatus.NOT_FOUND,response1.getStatusCode());
            ResponseEntity<String> response2 = restTemplate.exchange(
                    createURLWithPort(API_URI2),
                    HttpMethod.GET, entity, String.class);
            Assert.assertEquals(HttpStatus.NOT_FOUND,response2.getStatusCode());

        }

    }



    @Test
    public void testRateLimitNextFrame() throws InterruptedException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        int i=0;
        while(i<2) {
            ResponseEntity<String> response = restTemplate.exchange(
                    createURLWithPort(API_URI4),
                    HttpMethod.GET, entity, String.class);
            Assert.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
            i++;
            Thread.sleep(5000);
        }

    }

    @Test
    public void testWorkafterDown() throws InterruptedException {

        HttpEntity<String> entity = new HttpEntity<String>(null, headers);


        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(API_URI6),
                HttpMethod.GET, entity, String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        response = restTemplate.exchange(
                createURLWithPort(API_URI6),
                HttpMethod.GET, entity, String.class);
        Assert.assertEquals(HttpStatus.SERVICE_UNAVAILABLE,response.getStatusCode());
        Thread.sleep(5000);
        response = restTemplate.exchange(
                createURLWithPort(API_URI6),
                HttpMethod.GET, entity, String.class);
        Assert.assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());


    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
