package dev.arnaldo.tic.tac.toe.repository.match;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MatchQueryType {

    SELECT_BY_ID(
            "SELECT * FROM tic_tac_toe_matches WHERE id = ?;"),

    INSERT_ON_CONFLICT(
            "INSERT INTO tic_tac_toe_matches (id, challenger, opponent, winner, started_at, finished_at) " +
            "VALUES (?, ?, ?, ?, ?, ?) " +
            "ON CONFLICT (id) " +
            "DO UPDATE SET " +
            "challenger = excluded.challenger, " +
            "opponent = excluded.opponent, " +
            "winner = excluded.winner, " +
            "started_at = excluded.started_at, " +
            "finished_at = excluded.finished_at;"),

    SELECT_CURRENT_TURN(
            "SELECT CASE COUNT(DISTINCT moves.position) % 2 WHEN 0 THEN matches.challenger ELSE matches.opponent END AS current_player_id " +
            "FROM tic_tac_toe_matches matches " +
            "LEFT JOIN tic_tac_toe_moves moves " +
            "ON moves.match_id = matches.id " +
            "WHERE matches.id = ?;"),

    EXISTS_WINNER(
            "SELECT " +
            "moves.match_id, " +
            "users.id, " +
            "users.name, " +
            "GROUP_CONCAT(moves.\"position\") positions, " +
            "win_moves.id AS win_move_id " +
            "FROM tic_tac_toe_moves moves " +
            "LEFT JOIN tic_tac_toe_win_moves win_moves " +
            "ON win_moves.pos_1 == moves.\"position\" OR win_moves.pos_2 == moves.\"position\" OR win_moves.pos_3 == moves.\"position\" " +
            "LEFT JOIN tic_tac_toe_users users " +
            "ON users.id = moves.user_id " +
            "WHERE moves.match_id = ? AND moves.user_id = ?" +
            "GROUP BY win_moves.id " +
            "HAVING COUNT(DISTINCT moves.position) >= 3;"),

    EXISTS_TIE(
            "SELECT matches.id " +
            "FROM tic_tac_toe_matches matches " +
            "LEFT JOIN tic_tac_toe_moves moves " +
            "ON moves.match_id = matches.id " +
            "WHERE matches.id = ? " +
            "AND matches.finished_at IS NULL " +
            "AND matches.winner IS NULL " +
            "GROUP BY matches.id " +
            "HAVING COUNT(moves.position) >= 9;");

    ;

    private final String query;

}
