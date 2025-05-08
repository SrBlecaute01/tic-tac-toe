package dev.arnaldo.tic.tac.toe.repository.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserQueryType {

    SELECT_ALL("SELECT * FROM tic_tac_toe_users;"),

    SELECT_BY_ID("SELECT * FROM tic_tac_toe_users WHERE id = ?;"),

    SELECT_BY_ID_OR_NAME("SELECT * FROM tic_tac_toe_users WHERE id = CAST(? AS INT) OR UPPER(name) = UPPER(?);"),

    INSERT_ON_CONFLICT(
            "INSERT INTO tic_tac_toe_users (id, name, created_at) " +
            "VALUES (?, ?, ?) " +
            "ON CONFLICT(id) " +
            "DO UPDATE SET name = excluded.name, created_at = excluded.created_at;"),

    ;

    private final String query;

}
