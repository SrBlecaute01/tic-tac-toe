package dev.arnaldo.tic.tac.toe.repository.move.impl;

import com.jaoow.sql.executor.SQLExecutor;
import com.jaoow.sql.executor.function.StatementConsumer;
import dev.arnaldo.tic.tac.toe.domain.Move;
import dev.arnaldo.tic.tac.toe.repository.match.MatchRepository;
import dev.arnaldo.tic.tac.toe.repository.move.MoveAdapter;
import dev.arnaldo.tic.tac.toe.repository.move.MoveQueryType;
import dev.arnaldo.tic.tac.toe.repository.move.MoveRepository;
import dev.arnaldo.tic.tac.toe.repository.user.UserRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MoveRepositoryImpl implements MoveRepository {

    private final SQLExecutor executor;

    public MoveRepositoryImpl(@NonNull SQLExecutor executor, @NonNull UserRepository userRepository, @NonNull MatchRepository matchRepository) {
        this.executor = executor;
        this.executor.registerAdapter(Move.class, new MoveAdapter(userRepository, matchRepository));
    }

    @NotNull
    @Override
    public Set<Move> findByMatchId(long matchId) {
        return this.executor.queryMany(MoveQueryType.SELECT_BY_MATCH_ID.getQuery(),
                statement -> statement.setLong(1, matchId),
                Move.class);
    }

    @Override
    public boolean existsByMatchIdAndPosition(long matchId, int position) {
        return this.executor.query(MoveQueryType.EXISTS_MATCH_BY_ID_AND_POSITION.getQuery(), statement -> {
            statement.setLong(1, matchId);
            statement.setInt(2, position);
        }, ResultSet::next).orElse(false);
    }

    @Override
    public Optional<Move> findById(@NotNull Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Set<Move> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(@NotNull Move t) {
        this.executor.execute(MoveQueryType.INSERT.getQuery(), (StatementConsumer) statement -> this.prepare(t, statement));
    }

    @Override
    public void save(@NotNull List<Move> t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(@NotNull Move t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(@NotNull Long id) {
        throw new UnsupportedOperationException();
    }

    @SneakyThrows
    private void prepare(@NonNull Move move, @NonNull PreparedStatement statement) {
        statement.setLong(1, move.getMatch().getId());
        statement.setLong(2, move.getUser().getId());
        statement.setLong(3, move.getPosition());
        statement.setTimestamp(4, Timestamp.from(move.getCreatedAt()));
    }
}