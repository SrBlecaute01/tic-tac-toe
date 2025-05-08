package dev.arnaldo.tic.tac.toe.repository.user;

import com.jaoow.sql.executor.adapter.SQLResultAdapter;
import dev.arnaldo.tic.tac.toe.domain.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAdapter implements SQLResultAdapter<User> {

    @Nullable
    @Override
    public User adaptResult(@NotNull ResultSet resultSet) throws SQLException {
        final var id = resultSet.getLong("id");
        final var name = resultSet.getString("name");
        final var createdAt = resultSet.getTimestamp("created_at");

        return new User(id, name, createdAt.toInstant());
    }

}