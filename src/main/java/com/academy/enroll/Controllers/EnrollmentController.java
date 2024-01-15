package com.academy.enroll.Controllers;

import com.academy.enroll.DTOs.EnrollmentDTO;
import com.academy.enroll.Persistence.Documents.EnrollmentDocument;
import com.academy.enroll.Services.Contracts.IEnrollmentService;
import com.academy.enroll.configurations.Mappings.EnrollmentMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;


@RestController
@RequiredArgsConstructor
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentMapper mapper;
    private final IEnrollmentService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<EnrollmentDTO>>> getAll() {
        return Mono.just(ResponseEntity
                        .ok()
                        .body(service.findAll().map(mapper::toDTO)))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<EnrollmentDTO>> getOneById(@PathVariable("id") String id) {
        return service.findOneById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<?>> deleteOneById(@PathVariable("id") String id) {
        return service.deleteOneById(id)
                .flatMap(monoBoolean -> Boolean.TRUE.equals(monoBoolean)
                        ? Mono.just(ResponseEntity.ok().body(true))
                        : Mono.just(ResponseEntity.notFound().build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<EnrollmentDTO>> updateOneById(@Valid @RequestBody EnrollmentDTO dto, @PathVariable("id") String id) {
        return service.updateOneById(id, mapper.toDocument(dto))
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<EnrollmentDTO>> createOne(@Valid @RequestBody EnrollmentDTO dto) {
        return service.createOne(mapper.toDocument(dto))
                .map(mapper::toDTO)
                .map(monoDTO -> ResponseEntity.created(URI.create("/" + dto.getId())).body(monoDTO))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}