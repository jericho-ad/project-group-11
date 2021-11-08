package ca.mcgill.ecse321.townlibrary.controller;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static io.restassured.module.mockmvc.matcher.RestAssuredMockMvcMatchers.*;
import static org.hamcrest.Matchers.*;


// Heavily inspired by Paul's integration testing files

@Tag("integration")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EventControllerTest {

        @Autowired
        private WebApplicationContext webApplicationContext;

        @BeforeEach
        public void setup() {
            RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

            post("/libraries/10001?address=sad street");
        }

        @AfterEach
        public void cleanup() {
            RestAssuredMockMvc.reset();
        }

        @Test
        public void testStartEvents() {
            // Make sure its empty
            when().get("/events")
                .then().statusCode(200);

            // Since empty, any event search should return error
            when().get("/events/event1")
                .then().statusCode(400);
        }

        @Test
        public void testCreateEventAndQuery() {
            final String name = given()
                .param("id", "10000")
                .param("lib", "10001")
                .param("tr", "10002")
                .when().post("/events/event1")
                .then().statusCode(200)
                .body("id", equalTo(10000))
                .body("libId", equalTo(10001))
                .body("trId", equalTo(10002))
                .extract().response().body().path("name");

                when().get("/events/" + name)
                    .then()
                    .statusCode(200)
                    .body("name", equalTo(name))
                    .body("id", equalTo(10000))
                    .body("lib", equalTo(10001))
                    .body("trId", equalTo(10002));

                when().get("/events")
                    .then()
                    .statusCode(200)
                    .body("size()", equalTo(1))
                    .body("[0].name", equalTo(name))
                    .body("[0].id", equalTo(10000))
                    .body("[0].libId", equalTo(10001))
                    .body("[0].trId", equalTo(10002));
        }       
}
