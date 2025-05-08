package dev.arnaldo.tic.tac.toe.service.impl;

import dev.arnaldo.tic.tac.toe.domain.Match;
import dev.arnaldo.tic.tac.toe.domain.Move;
import dev.arnaldo.tic.tac.toe.domain.User;
import dev.arnaldo.tic.tac.toe.repository.match.MatchRepository;
import dev.arnaldo.tic.tac.toe.repository.move.MoveRepository;
import dev.arnaldo.tic.tac.toe.repository.user.UserRepository;
import dev.arnaldo.tic.tac.toe.service.MatchService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Set;

@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final UserRepository userRepository;
    private final MatchRepository matchRepository;
    private final MoveRepository moveRepository;

    @NotNull
    @Override
    public Match createMatch(@NonNull String challengerId, @NonNull String opponentId) {
        final User challenger = this.userRepository.findByIdOrName(challengerId)
                .orElseThrow(() -> new RuntimeException("challenger not found with id: " + challengerId));

        final User opponent = this.userRepository.findByIdOrName(opponentId)
                .orElseThrow(() -> new RuntimeException("opponent not found with id: " + opponentId));

        if (challenger.getId().equals(opponent.getId())) {
            throw new RuntimeException("challenger and opponent cannot be the same user");
        }

        final Match match = Match.builder()
                .challenger(challenger)
                .opponent(opponent)
                .startedAt(Instant.now())
                .build();

        this.matchRepository.save(match);
        return match;
    }

    @NotNull
    @Override
    public Match findMatchById(long matchId) {
        return this.matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("match not found with id: " + matchId));
    }

    @Override
    public long findCurrentTurn(long matchId) {
        return matchRepository.findCurrentTurn(matchId).orElseThrow(() -> new RuntimeException("match not found"));
    }

    @NotNull
    @Override
    public Move createMove(long matchId, long userId, int position) {
        if (position < 1 || position > 9)
            throw new RuntimeException("invalid position, must be between 1 and 9");

        final Match match = this.findMatchById(matchId);
        if (this.moveRepository.existsByMatchIdAndPosition(match.getId(), position)) {
            throw new RuntimeException("position already occupied");
        }

        if (match.getChallenger().getId() != userId && match.getOpponent().getId() != userId) {
            throw new RuntimeException("user not part of the match");
        }

        final long current = this.findCurrentTurn(match.getId());
        if (current != userId) {
            throw new RuntimeException("it's not your turn");
        }

        final Move move = Move.builder()
                .match(match)
                .user(userId == match.getChallenger().getId() ? match.getChallenger() : match.getOpponent())
                .position(position)
                .createdAt(Instant.now())
                .build();

        this.moveRepository.save(move);
        return move;
    }

    @NotNull
    @Override
    public Set<Move> findMoves(long matchId) {
        return this.moveRepository.findByMatchId(matchId);
    }

    @Override
    public boolean validateWinner(long matchId, long userId) {
        return this.matchRepository.existsWinner(matchId, userId);
    }

    @Override
    public boolean validateTie(long matchId) {
        return this.matchRepository.existsTie(matchId);
    }

}
