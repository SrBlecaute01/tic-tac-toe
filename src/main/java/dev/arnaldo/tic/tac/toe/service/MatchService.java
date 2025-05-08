package dev.arnaldo.tic.tac.toe.service;

import dev.arnaldo.tic.tac.toe.domain.Match;
import dev.arnaldo.tic.tac.toe.domain.Move;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface MatchService {

    @NotNull
    Match createMatch(@NonNull String challengerId, @NonNull String opponentId);

    @NotNull
    Match findMatchById(long matchId);

    long findCurrentTurn(long matchId);

    @NotNull
    Move createMove(long matchId, long userId, int position);

    @NotNull
    Set<Move> findMoves(long matchId);

    boolean validateWinner(long matchId, long userId);

    boolean validateTie(long matchId);

}