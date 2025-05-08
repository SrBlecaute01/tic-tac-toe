package dev.arnaldo.tic.tac.toe.repository.user;

import dev.arnaldo.tic.tac.toe.domain.User;
import dev.arnaldo.tic.tac.toe.repository.Repository;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface UserRepository extends Repository<Long, User> {

    @NotNull
    Optional<User> findByIdOrName(String value);

}