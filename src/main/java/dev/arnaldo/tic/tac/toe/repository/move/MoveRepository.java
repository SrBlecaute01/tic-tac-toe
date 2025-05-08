package dev.arnaldo.tic.tac.toe.repository.move;

import dev.arnaldo.tic.tac.toe.domain.Move;
import dev.arnaldo.tic.tac.toe.repository.Repository;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface MoveRepository extends Repository<Long, Move> {

    @NotNull
    Set<Move> findByMatchId(long matchId);

    boolean existsByMatchIdAndPosition(long matchId, int position);

}