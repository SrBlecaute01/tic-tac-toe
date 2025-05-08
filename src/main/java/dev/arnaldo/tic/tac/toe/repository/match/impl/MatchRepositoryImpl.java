package dev.arnaldo.tic.tac.toe.repository.match.impl;

import com.jaoow.sql.executor.SQLExecutor;
import dev.arnaldo.tic.tac.toe.domain.Match;
import dev.arnaldo.tic.tac.toe.repository.match.MatchAdapter;
import dev.arnaldo.tic.tac.toe.repository.match.MatchQueryType;
import dev.arnaldo.tic.tac.toe.repository.match.MatchRepository;
import dev.arnaldo.tic.tac.toe.repository.user.UserRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class MatchRepositoryImpl implements MatchRepository {

    private final SQLExecutor executor;

    public MatchRepositoryImpl(@NonNull SQLExecutor executor, @NonNull UserRepository userRepository) {
        this.executor = executor;
        this.executor.registerAdapter(Match.class, new MatchAdapter(userRepository));
    }

    @Override
    public Optional<Match> findById(@NotNull Long id) {
        return this.executor.query(MatchQueryType.SELECT_BY_ID.getQuery(),
                consumer -> consumer.setLong(1, id),
                Match.class);
    }

    @NotNull
    @Override
    public Set<Match> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(@NotNull Match match) {
        this.executor.execute(MatchQueryType.INSERT_ON_CONFLICT.getQuery(), consumer -> this.prepare(match, consumer), result -> {
            if (match.getId() != null) return;
            match.setId(result.getLong(1));
        });
    }

    @Override
    public void save(@NotNull List<Match> t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(@NotNull Match t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(@NotNull Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull Optional<Long> findCurrentTurn(long matchId) {
        return this.executor.query(MatchQueryType.SELECT_CURRENT_TURN.getQuery(),
                consumer -> consumer.setLong(1, matchId),
                result -> result.getLong("current_player_id"));
    }

    @Override
    public boolean existsWinner(long matchId, long userId) {
        return this.executor.query(MatchQueryType.EXISTS_WINNER.getQuery(), statement -> {
            statement.setLong(1, matchId);
            statement.setLong(2, userId);
        }, ResultSet::next).orElse(false);
    }

    @Override
    public boolean existsTie(long matchId) {
        return this.executor.query(MatchQueryType.EXISTS_TIE.getQuery(), statement -> {
            statement.setLong(1, matchId);
        }, ResultSet::next).orElse(false);
    }

    @SneakyThrows
    private void prepare(@NonNull Match match, @NonNull PreparedStatement statement) {
        statement.setObject(1, match.getId());
        statement.setLong(2, Objects.requireNonNull(match.getChallenger().getId()));
        statement.setLong(3, Objects.requireNonNull(match.getOpponent().getId()));
        statement.setObject(4, match.getWinner() != null ? Objects.requireNonNull(match.getWinner().getId()) : null);
        statement.setTimestamp(5, Timestamp.from(match.getStartedAt()));
        statement.setObject(6, match.getFinishedAt() != null ? Timestamp.from(match.getFinishedAt()) : null);
    }

}