package dev.arnaldo.tic.tac.toe.repository.match;

import com.jaoow.sql.executor.adapter.SQLResultAdapter;
import dev.arnaldo.tic.tac.toe.domain.Match;
import dev.arnaldo.tic.tac.toe.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@RequiredArgsConstructor
public class MatchAdapter implements SQLResultAdapter<Match> {

    private final UserRepository userRepository;

    @Nullable
    @Override
    public Match adaptResult(@NotNull ResultSet result) throws SQLException {
        final var id = result.getLong("id");
        final var challengerId = result.getLong("challenger");
        final var opponentId = result.getLong("opponent");
        final var winnerId = result.getString("winner");

        final var challenger = this.userRepository.findById(challengerId).orElseThrow(() -> new RuntimeException("challenger not found with id: " + challengerId));
        final var opponent = this.userRepository.findById(opponentId).orElseThrow(() -> new RuntimeException("opponent not found with id: " + opponentId));
        final var winner = StringUtils.isNotBlank(winnerId)
                ? this.userRepository.findById(Long.parseLong(winnerId)).orElseThrow(() -> new RuntimeException("opponent not found with id: " + winnerId))
                : null;

        final Timestamp startedAt = result.getTimestamp("started_at");
        final Timestamp finishedAt = result.getTimestamp("finished_at");

        return Match.builder()
                .id(id)
                .challenger(challenger)
                .opponent(opponent)
                .winner(winner)
                .startedAt(startedAt.toInstant())
                .finishedAt(finishedAt != null ? finishedAt.toInstant() : null)
                .build();
    }

}