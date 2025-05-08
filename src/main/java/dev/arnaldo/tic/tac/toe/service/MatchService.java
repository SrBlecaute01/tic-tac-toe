package dev.arnaldo.tic.tac.toe.service;

import dev.arnaldo.tic.tac.toe.domain.Match;
import dev.arnaldo.tic.tac.toe.domain.Move;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@SuppressWarnings("UnusedReturnValue")
public interface MatchService {

    @NotNull
    Match createMatch(@NonNull String challengerId, @NonNull String opponentId);

    @NotNull
    Match updateMatch(@NonNull Match match);

    @NotNull
    Match findMatchById(long matchId);

    long findCurrentTurn(long matchId);

    @NotNull
    Move createMove(long matchId, long userId, int position);

    @NotNull
    Set<Move> findMoves(long matchId);

    boolean existsWinner(long matchId, long userId);

    boolean existsTie(long matchId);

}