package dev.arnaldo.tic.tac.toe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class Move {

    @NonNull
    private Match match;

    @NonNull
    private User user;

    private int position;

    @NonNull
    private Instant createdAt;

}