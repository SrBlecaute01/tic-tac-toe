package dev.arnaldo.tic.tac.toe.repository.match;

import dev.arnaldo.tic.tac.toe.domain.Match;
import dev.arnaldo.tic.tac.toe.repository.Repository;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface MatchRepository extends Repository<Long, Match> {

    @NotNull
    Optional<Long> findCurrentTurn(long matchId);

    boolean existsWinner(long matchId, long userId);

    boolean existsTie(long matchId);

}