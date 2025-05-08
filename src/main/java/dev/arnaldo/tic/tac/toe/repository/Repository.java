package dev.arnaldo.tic.tac.toe.repository;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Repository interface that will handle all database operations.
 *
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 */
public interface Repository<K, V> {

    Optional<V> findById(@NotNull K id);

    @NotNull
    Set<V> findAll();

    void save(@NotNull V t);

    void save(@NotNull List<V> t);

    void delete(@NotNull V t);

    void deleteById(@NotNull K id);

}