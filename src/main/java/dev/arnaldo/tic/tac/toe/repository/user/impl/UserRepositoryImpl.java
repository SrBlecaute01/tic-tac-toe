package dev.arnaldo.tic.tac.toe.repository.user.impl;

import com.jaoow.sql.executor.SQLExecutor;
import dev.arnaldo.tic.tac.toe.domain.User;
import dev.arnaldo.tic.tac.toe.repository.user.UserAdapter;
import dev.arnaldo.tic.tac.toe.repository.user.UserQueryType;
import dev.arnaldo.tic.tac.toe.repository.user.UserRepository;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UserRepositoryImpl implements UserRepository {

    private final SQLExecutor executor;

    public UserRepositoryImpl(@NonNull SQLExecutor executor) {
        this.executor = executor;
        this.executor.registerAdapter(User.class, new UserAdapter());
    }

    @Override
    public Optional<User> findById(@NotNull Long id) {
        return this.executor.query(UserQueryType.SELECT_BY_ID.getQuery(),
                statement -> statement.setLong(1, id),
                User.class);
    }

    @NotNull
    @Override
    public Optional<User> findByIdOrName(String value) {
        return this.executor.query(UserQueryType.SELECT_BY_ID.getQuery(),
                statement -> statement.setString(1, value),
                User.class);
    }

    @NotNull
    @Override
    public Set<User> findAll() {
        return this.executor.queryMany(UserQueryType.SELECT_ALL.getQuery(), User.class);
    }

    @Override
    public void save(@NotNull User user) {
        this.executor.execute(UserQueryType.INSERT_ON_CONFLICT.getQuery(),
                consumer -> this.prepareStatement(user, consumer),
                result -> user.setId(result.getLong(1)));
    }

    @Override
    public void save(@NotNull List<User> t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(@NotNull User t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(@NotNull Long id) {
        throw new UnsupportedOperationException();
    }

    private void prepareStatement(@NotNull User user, @NotNull PreparedStatement statement) throws SQLException {
        statement.setObject(1, user.getId());
        statement.setString(2, user.getName());
        statement.setTimestamp(3, Timestamp.from(user.getCreatedAt()));
    }

}