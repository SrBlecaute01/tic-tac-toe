package dev.arnaldo.tic.tac.toe.repository.move;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MoveQueryType {

    SELECT_BY_MATCH_ID("SELECT * FROM tic_tac_toe_moves WHERE match_id = ?"),
    EXISTS_MATCH_BY_ID_AND_POSITION("SELECT 1 FROM tic_tac_toe_moves WHERE match_id = ? AND position = ?;"),
    INSERT("INSERT INTO tic_tac_toe_moves (match_id, user_id, position, created_at) VALUES (?, ?, ?, ?);"),

    ;

    private final String query;

}
