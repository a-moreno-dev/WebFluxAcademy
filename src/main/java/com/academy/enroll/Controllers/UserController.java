package com.academy.enroll.Controllers;


import ch.qos.logback.core.model.Model;
import com.academy.enroll.Services.Contracts.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final ModelMapper mapper;
    private final IUserService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<StudentResponseDTO>>> getAll() {
        return Mono.just(ResponseEntity
                        .ok()
                        .body(service.findAll().map(mapper::toDTO)))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<StudentResponseDTO>> getOneById(@PathVariable("id") String id) {
        return service.findOneById(id)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Boolean>> deleteOneById(@PathVariable("id") String id) {
        return service.deleteOneById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<StudentResponseDTO>> updateOneById(
            @Valid @RequestBody StudentRequestDTO dto, @PathVariable("id") String id) {
        return service.updateOneById(id, mapper.toDocument(dto))
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<StudentResponseDTO>> createOne(@Valid @RequestBody StudentRequestDTO dto) {
        try{
        return service.createOne(mapper.toDocument(dto))
                .map(mapper::toDTO)
                .map(monoDTO -> ResponseEntity.created(URI.create("/" + dto.getId())).body(monoDTO))
                .defaultIfEmpty(ResponseEntity.notFound().build());
        }catch (Exception ex){
            log.error(ex.getMessage());
            return Mono.just(ResponseEntity.badRequest().build());
        }
    }

}
