package dev.arnaldo.tic.tac.toe.service.impl;

import dev.arnaldo.tic.tac.toe.domain.User;
import dev.arnaldo.tic.tac.toe.repository.user.UserRepository;
import dev.arnaldo.tic.tac.toe.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @NotNull
    @Override
    public Set<User> findUsers() {
        return this.userRepository.findAll();
    }

    @NotNull
    @Override
    public User findByIdOrName(@NonNull String value) {
        return this.userRepository.findByIdOrName(value)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

}