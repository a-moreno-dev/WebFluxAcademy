//package com.academy.enroll.Controllers;
//
//import com.academy.enroll.DTOs.CourseDTO;
//import com.academy.enroll.DTOs.EnrollmentDTO;
//import com.academy.enroll.DTOs.StudentRequestDTO;
//import com.academy.enroll.Persistence.Documents.CourseDocument;
//import com.academy.enroll.Persistence.Documents.EnrollmentDocument;
//import com.academy.enroll.Persistence.Documents.StudentDocument;
//import com.academy.enroll.Services.Contracts.IEnrollmentService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@ExtendWith(SpringExtension.class)
//@WebFluxTest(controllers = EnrollmentController.class)
//class EnrollmentControllerTest {
//
//    @MockBean
//    @Qualifier("default")
//    private ModelMapper mapper;
//
//    @Autowired
//    private WebTestClient client;
//
//    @MockBean
//    private IEnrollmentService service;
//
//    // Enrollment data
//    private EnrollmentDocument enrollment_01;
//    private EnrollmentDocument enrollment_02;
//    private EnrollmentDTO enrollment_dto_01;
//    private EnrollmentDTO enrollment_dto_02;
//    private List<EnrollmentDocument> enrollments;
//    private List<EnrollmentDTO> enrollments_dtos;
//
//    // Course data
//    private CourseDocument course_01;
//    private CourseDocument course_02;
//    private CourseDTO course_dto_01;
//    private CourseDTO course_dto_02;
//
//    // Student data
//    private StudentDocument student_01;
//    private StudentRequestDTO student_request_dto;
//
//    @BeforeEach
//    void setUp() {
//
//        MockitoAnnotations.openMocks(this);
//
//        // Course data
//        course_01 = new CourseDocument("123456", null, null, true);
//        course_02 = new CourseDocument("456789", null, null, true);
//
//        course_dto_01 = new CourseDTO("123456", null, null, null);
//        course_dto_02 = new CourseDTO("456789", null, null, null);
//
//        // Student data
//        student_01 = new StudentDocument("123456", null, null, null, null, null);
//        student_request_dto = new StudentRequestDTO("123456", null, null, null, null);
//
//        // Enrollment data
//        enrollment_01 = new EnrollmentDocument("123456", true, student_01, List.of(course_01, course_02), LocalDateTime.now());
//        enrollment_02 = new EnrollmentDocument("456789", true, student_01, List.of(course_01, course_02), LocalDateTime.now());
//
//        enrollment_dto_01 = new EnrollmentDTO("123456", true, student_request_dto, List.of(course_dto_01, course_dto_02), LocalDateTime.now());
//        enrollment_dto_02 = new EnrollmentDTO("456789", true, student_request_dto, List.of(course_dto_01, course_dto_02), LocalDateTime.now());
//
//        enrollments = List.of(enrollment_01, enrollment_02);
//
//        Mockito.when(mapper.map(enrollment_01, EnrollmentDTO.class)).thenReturn(enrollment_dto_01);
//        Mockito.when(mapper.map(enrollment_dto_01, EnrollmentDocument.class)).thenReturn(enrollment_01);
//
//        Mockito.when(service.findAll()).thenReturn(Flux.fromIterable(enrollments));
//
//        Mockito.when(service.findOneById("123456")).thenReturn(Mono.just(enrollment_01));
//        Mockito.when(service.findOneById("456789")).thenReturn(Mono.just(enrollment_02));
//
//        Mockito.when(service.deleteOneById("123456")).thenReturn(Mono.just(true));
//        Mockito.when(service.deleteOneById("1234567")).thenReturn(Mono.just(false));
//
//        Mockito.when(service.createOne(enrollment_01)).thenReturn(Mono.just(enrollment_01));
//        Mockito.when(service.updateOneById("123456", enrollment_01)).thenReturn(Mono.just(enrollment_01));
//    }
//
//    @Test
//    void getAll_MustBe_OK() {
//        client.get()
//                .uri("/enrollments")
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
//                .expectBodyList(EnrollmentDocument.class);
//    }
//
//    @Test
//    void getAll_MustBe_OK2() {
//        client.get()
//                .uri("/enrollments")
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
//                .expectBodyList(EnrollmentDTO.class);
//    }
//
//    @Test
//    void getOneById_MustBe_OK() {
//        client.get()
//                .uri("/enrollments/123456")
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
//                .expectBody(EnrollmentDTO.class);
//    }
//
//    @Test
//    void getOneById_MustBe_Bad_Request() {
//        client.get()
//                .uri("/enrollments/1234567")
//                .exchange()
//                .expectStatus().isBadRequest();
//    }
//
//    @Test
//    void deleteOneById_MustBe_OK() {
//        client.delete()
//                .uri("/enrollments/123456")
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
//                .expectBody(Boolean.class);
//    }
//
//    @Test
//    void deleteOneById_MustBe_Not_Found() {
//        client.delete()
//                .uri("/enrollments/1234567")
//                .exchange()
//                .expectStatus().isNotFound();
//    }
//
//    @Test
//    void updateOneById_MustBe_OK() {
//        client.put()
//                .uri("/enrollments/123456")
//                .bodyValue(enrollment_dto_01)
//                .exchange()
//                .expectStatus().isOk()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
//                .expectBody(EnrollmentDTO.class);
//    }
//
//    @Test
//    void updateOneById_MustBe_Bad_Request() {
//        client.put()
//                .uri("/enrollments/1234567")
//                .bodyValue(enrollment_dto_01)
//                .exchange()
//                .expectStatus().isBadRequest();
//    }
//
//    @Test
//    void createOne_MustBe_Created() {
//        client.post()
//                .uri("/enrollments")
//                .bodyValue(enrollment_dto_01)
//                .exchange()
//                .expectStatus().isCreated()
//                .expectHeader().contentType(MediaType.APPLICATION_JSON_VALUE)
//                .expectBody(EnrollmentDTO.class);
//    }
//
//    @Test
//    void createOne_MustBe_Bad_Request() {
//        client.post()
//                .uri("/enrollments")
//                .bodyValue(course_dto_02)
//                .exchange()
//                .expectStatus().isBadRequest();
//    }
//}