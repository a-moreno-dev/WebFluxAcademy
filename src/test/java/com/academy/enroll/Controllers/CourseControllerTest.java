package com.academy.enroll.Controllers;

import com.academy.enroll.DTOs.CourseDTO;
import com.academy.enroll.Persistence.Documents.CourseDocument;
import com.academy.enroll.Services.Contracts.ICourseService;
import com.academy.enroll.configurations.Mappings.CourseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CourseController.class)
class CourseControllerTest {

    @MockBean
    private CourseMapper mapper;

    @Autowired
    private WebTestClient client;

    @MockBean
    private ICourseService service;

    private CourseDocument course_01;
    private CourseDocument course_02;
    private CourseDTO course_dto_01;
    private CourseDTO course_dto_02;
    private List<CourseDocument> courses;


    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        course_01 = new CourseDocument("123456", "PHP y MYSQL 2024", "PMO2024", true);
        course_02 = new CourseDocument("456789", "", "", true);

        course_dto_01 = new CourseDTO("123456", "PHP y MYSQL 2024", "PMO2024", true);
        course_dto_02 = new CourseDTO("456789", "", "", true);

        courses = List.of(course_01, course_02);

        Mockito.when(mapper.toDTO(course_01)).thenReturn(course_dto_01);
        Mockito.when(mapper.toDocument(course_dto_01)).thenReturn(course_01);
        Mockito.when(mapper.toDTO(course_02)).thenReturn(course_dto_02);
        Mockito.when(mapper.toDocument(course_dto_02)).thenReturn(course_02);

        Mockito.when(service.findAll()).thenReturn(Flux.fromIterable(courses));
        Mockito.when(service.findOneById("123456")).thenReturn(Mono.just(course_01));
        Mockito.when(service.findOneById("456789")).thenReturn(Mono.just(course_02));
        Mockito.when(service.deleteOneById("123456")).thenReturn(Mono.just(true));
        Mockito.when(service.deleteOneById("1234567")).thenReturn(Mono.just(false));
        Mockito.when(service.createOne(course_01)).thenReturn(Mono.just(course_01));
    }

    @Test
    void getAll_MustBe_OK() {
        client.get()
                .uri("/v1/courses")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBodyList(CourseDTO.class);
    }

    @Test
    void getOneById_MustBe_OK() {
        client.get()
                .uri("/v1/courses/123456")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(CourseDTO.class);
    }

    @Test
    void getOneById_MustBe_Bad_Request() {
        client.get()
                .uri("/v1/courses/1234567")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void deleteOneById_MustBe_OK() {
        client.delete()
                .uri("/v1/courses/123456")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(Boolean.class);
    }

    @Test
    void deleteOneById_MustBe_Not_Found() {
        client.delete()
                .uri("/v1/courses/1234567")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void updateOneById() {
        client.put()
                .uri("/v1/courses/123456")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(CourseDTO.class);
    }

    @Test
    void createOne_MustBe_Created() {
        client.post()
                .uri("/v1/courses")
                .bodyValue(course_dto_01)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(CourseDTO.class);
    }

    @Test
    void createOne_MustBe_Bad_Request() {
        client.post()
                .uri("/v1/courses")
                .bodyValue(course_dto_02)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
                .expectBody(CourseDTO.class);
    }
}