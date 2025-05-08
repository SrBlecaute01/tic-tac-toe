package dev.arnaldo.tic.tac.toe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class Match {

    private Long id;

    @NonNull
    private User challenger;

    @NonNull
    private User opponent;

    @Nullable
    private User winner;

    @NonNull
    private Instant startedAt;

    @Nullable
    private Instant finishedAt;

}