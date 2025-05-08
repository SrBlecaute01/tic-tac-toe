DROP TABLE IF EXISTS tic_tac_toe_win_moves;
DROP TABLE IF EXISTS tic_tac_toe_moves;
DROP TABLE IF EXISTS tic_tac_toe_matches;
DROP TABLE IF EXISTS tic_tac_toe_users;

-- enable foreign keys in sqlite
PRAGMA foreign_keys = ON;

-- create table for users
CREATE TABLE IF NOT EXISTS tic_tac_toe_users
(
    id         INTEGER      NOT NULL PRIMARY KEY AUTOINCREMENT,
    name       VARCHAR(100) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

/**
 * Create table for user matches
 *
 * # For the winner:
 * 	- if null it was a tie, otherwise the user id
 */
CREATE TABLE IF NOT EXISTS tic_tac_toe_matches
(
    id          INTEGER   NOT NULL PRIMARY KEY AUTOINCREMENT,
    challenger  INTEGER   NOT NULL,
    opponent    INTEGER   NOT NULL,
    winner      INTEGER,
    started_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    finished_at TIMESTAMP,

    FOREIGN KEY (challenger) REFERENCES tic_tac_toe_users (id),
    FOREIGN KEY (opponent) REFERENCES tic_tac_toe_users (id),
    FOREIGN KEY (winner) REFERENCES tic_tac_toe_users (id),

    CHECK (finished_at IS NULL OR finished_at >= started_at),
    CHECK (winner IS NULL OR winner == challenger OR winner == opponent)
);

-- create table for user movements
CREATE TABLE IF NOT EXISTS tic_tac_toe_moves
(
    match_id   INTEGER   NOT NULL,
    user_id    INTEGER   NOT NULL,
    position   INTEGER   NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (match_id, user_id, position),

    FOREIGN KEY (match_id) REFERENCES tic_tac_toe_matches (id),
    FOREIGN KEY (user_id) REFERENCES tic_tac_toe_users (id),

    CHECK (position >= 0 AND position <= 9)
);

-- create a table for win moves
CREATE TABLE IF NOT EXISTS tic_tac_toe_win_moves
(
    id    INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    pos_1 INTEGER NOT NULL,
    pos_2 INTEGER NOT NULL,
    pos_3 INTEGER NOT NULL
);

-- populate with win moves
INSERT INTO tic_tac_toe_win_moves (id, pos_1, pos_2, pos_3)
VALUES (1, 1, 2, 3), -- lines
       (2, 4, 5, 6),
       (3, 7, 8, 9),
       (4, 1, 4, 7), -- columns
       (5, 2, 5, 8),
       (6, 3, 6, 9),
       (7, 1, 5, 9), -- diagonals
       (8, 3, 5, 7);

-- populate users
INSERT INTO tic_tac_toe_users (id, name) VALUES (1, 'José Arnaldo');
INSERT INTO tic_tac_toe_users (id, name) VALUES (2, 'Maria Vitória');
INSERT INTO tic_tac_toe_users (id, name) VALUES (3, 'Yasmin Roberta');