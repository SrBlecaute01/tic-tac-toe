package dev.arnaldo.tic.tac.toe.service;

import dev.arnaldo.tic.tac.toe.domain.User;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface UserService {

    @NotNull
    Set<User> findUsers();

    @NotNull
    User findByIdOrName(@NonNull String value);

}