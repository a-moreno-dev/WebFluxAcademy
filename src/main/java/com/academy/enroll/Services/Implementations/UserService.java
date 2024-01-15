package com.academy.enroll.Services.Implementations;

import com.academy.enroll.Persistence.Documents.RoleDocument;
import com.academy.enroll.Persistence.Documents.StudentDocument;
import com.academy.enroll.Persistence.Documents.UserDocument;
import com.academy.enroll.Persistence.Repositories.IRoleRepository;
import com.academy.enroll.Persistence.Repositories.IUserRepository;
import com.academy.enroll.Services.Abstractions.AbstractGenericService;
import com.academy.enroll.Services.Contracts.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractGenericService<UserDocument, String> implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final BCryptPasswordEncoder bcrypt;

    @Override
    protected IUserRepository getRepository() {
        return userRepository;
    }


    /////////////////////////////////////////////


    @Override
    public Mono<UserDocument> saveDocumentHash(UserDocument document) {
        final String bcryptPassword = bcrypt.encode(document.getPassword());
        document.setPassword(bcryptPassword);
        return getRepository().save(document);
    }

    @Override
    public Mono<UserDocument> findOneByUsername(String username) {
        Mono<UserDocument> monoUser = getRepository().findOneByUsername(username);
        List<RoleDocument> roles = new ArrayList<>();

        return monoUser.flatMap(user -> {
            return Flux.fromIterable(user.getRoles())
                    .flatMap(rol -> {
                        return roleRepository.findById(rol.getId())
                                .map(r -> {
                                    roles.add(new RoleDocument(r.getId(), r.getName()));
                                    return r;
                                });
                    }).collectList()
                    .flatMap(list -> {
                        user.setRoles(list);
                        return Mono.just(user);
                    });
        }).flatMap(u -> Mono.just(new UserDocument(u.getUsername(), u.getPassword(), u.isStatus(), roles)));
    }

    /////////////////////////////////////////////


    @Override
    public Mono<UserDocument> createOne(UserDocument studentDocument) {
        return null;
    }

    @Override
    public Mono<UserDocument> updateOneById(String documentId, UserDocument studentDocument) {
        return null;
    }
}