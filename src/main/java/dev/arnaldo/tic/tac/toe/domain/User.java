package dev.arnaldo.tic.tac.toe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class User {

    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Instant createdAt;

}
