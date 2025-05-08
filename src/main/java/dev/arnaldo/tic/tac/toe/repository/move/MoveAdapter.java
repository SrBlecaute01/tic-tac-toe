package dev.arnaldo.tic.tac.toe.repository.move;

import com.jaoow.sql.executor.adapter.SQLResultAdapter;
import dev.arnaldo.tic.tac.toe.domain.Move;
import dev.arnaldo.tic.tac.toe.repository.match.MatchRepository;
import dev.arnaldo.tic.tac.toe.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class MoveAdapter implements SQLResultAdapter<Move> {

    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

    @Nullable
    @Override
    public Move adaptResult(@NotNull ResultSet result) throws SQLException {
        final var matchId = result.getLong("match_id");
        final var userId = result.getLong("user_id");
        final var position = result.getInt("position");
        final var createdAt = result.getTimestamp("created_at");

        final var match = this.matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("match not found with id: " + matchId));

        final var user = this.userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found with id: " + userId));

        return Move.builder()
                .match(match)
                .user(user)
                .position(position)
                .createdAt(createdAt.toInstant())
                .build();
    }

}
