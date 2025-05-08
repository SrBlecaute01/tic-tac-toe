package dev.arnaldo.tic.tac.toe.repository.match;

import com.jaoow.sql.executor.adapter.SQLResultAdapter;
import dev.arnaldo.tic.tac.toe.domain.Match;
import dev.arnaldo.tic.tac.toe.domain.User;
import dev.arnaldo.tic.tac.toe.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        final var startedAt = result.getTimestamp("started_at");
        final var finishedAt = result.getTimestamp("finished_at");

        final var challenger = this.findUserById(challengerId, "challenger");
        final var opponent = this.findUserById(opponentId, "opponent");
        final var winner = StringUtils.isNotBlank(winnerId) ? this.findUserById(Long.parseLong(winnerId), "winner") : null;

        return Match.builder()
                .id(id)
                .challenger(challenger)
                .opponent(opponent)
                .winner(winner)
                .startedAt(startedAt.toInstant())
                .finishedAt(finishedAt != null ? finishedAt.toInstant() : null)
                .build();
    }

    private User findUserById(long id, String label) {
        return this.userRepository.findById(id).orElseThrow(() -> new RuntimeException(label + " not found with id: " + id));
    }

}