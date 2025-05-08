package dev.arnaldo.tic.tac.toe.view;

import dev.arnaldo.tic.tac.toe.TicTacToe;
import dev.arnaldo.tic.tac.toe.domain.Match;
import dev.arnaldo.tic.tac.toe.domain.User;
import dev.arnaldo.tic.tac.toe.service.MatchService;
import dev.arnaldo.tic.tac.toe.service.UserService;
import dev.arnaldo.tic.tac.toe.util.TableUtil;
import dev.arnaldo.tic.tac.toe.util.view.ViewUtil;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;

import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class MatchView {

    private static final UserService USER_SERVICE = TicTacToe.getInstance().getUserService();
    private static final MatchService MATCH_SERVICE = TicTacToe.getInstance().getMatchService();

    @SneakyThrows
    public MatchView(@NonNull Terminal terminal) {
        final var writer = terminal.writer();
        final var reader = this.getReader(terminal);
        final var matcher = this.createMatch(reader, writer);
        if (matcher == null) {
            new HomeView(terminal);
            return;
        }

        this.startMatch(matcher, reader, writer);

        Thread.sleep(Duration.ofSeconds(5));
        ViewUtil.clearTerminal(reader.getTerminal());

        new HomeView(terminal);
    }

    @NotNull
    private LineReader getReader(Terminal terminal) {
        return LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(new StringsCompleter(() -> {
                    final Set<String> candidates = new HashSet<>();
                    USER_SERVICE.findUsers().forEach(user -> {
                        candidates.add(user.getName());
                        candidates.add(String.valueOf(user.getId()));
                    });
                    return candidates;
                }))
                .build();
    }

    @Nullable
    private Match createMatch(@NotNull LineReader reader, @NotNull PrintWriter writer) {
        try {
            writer.println("\nInforme os dados para iniciar a partida:\n");
            writer.flush();

            final var challenger = reader.readLine("Digite o seu nome ou ID: ");
            final var opponent = reader.readLine("Digite o nome do adversário ou ID: ");

            if (StringUtils.isBlank(challenger) || StringUtils.isBlank(opponent)) {
                throw new RuntimeException("Os nomes não podem ser vazios");
            }

            return MATCH_SERVICE.createMatch(challenger.trim(), opponent.trim());

        } catch (Exception exception) {
            ViewUtil.clearTerminal(reader.getTerminal());

            writer.println("\n[ERROR] Error ao criar partida: " + exception.getMessage());
            writer.flush();

            return null;
        }
    }

    @SneakyThrows
    private void startMatch(@NotNull Match match, @NotNull LineReader reader, @NotNull PrintWriter writer) {
        ViewUtil.clearTerminal(reader.getTerminal());
        while (true) {
            try {
                this.renderTables(match, writer);

                final var currentId = MATCH_SERVICE.findCurrentTurn(match.getId());
                final var current = currentId == match.getChallenger().getId() ? match.getChallenger() : match.getOpponent();
                final var input = reader.readLine("Turno de: " + current.getName() + ". Digite a posição: ");
                final var position = NumberUtils.toInt(input);

                MATCH_SERVICE.createMove(match.getId(), current.getId(), position);

                ViewUtil.clearTerminal(reader.getTerminal());
                if (MATCH_SERVICE.existsWinner(match.getId(), current.getId())) {
                    this.finishMatch(match, current);
                    this.renderTables(match, writer);

                    writer.println("Parabéns " + current.getName() + ", você venceu!");
                    writer.flush();
                    break;
                }

                if (MATCH_SERVICE.existsTie(match.getId())) {
                    this.finishMatch(match, null);
                    this.renderTables(match, writer);

                    writer.println("Oops, Parece que temos um empate!");
                    writer.flush();
                    break;
                }

            } catch (UserInterruptException exception) {
                log.info("User interrupt exception: {}", exception.getMessage());
                System.exit(0);
                break;

            } catch (Exception exception) {
                ViewUtil.clearTerminal(reader.getTerminal());

                writer.println("\n[ERROR] Error ao executar movimento: " + exception.getMessage() + "\n");
                writer.flush();
            }
        }

    }

    private void finishMatch(@NotNull Match match, @Nullable User winner) {
        match.setWinner(winner);
        match.setFinishedAt(Instant.now());

        MATCH_SERVICE.updateMatch(match);
    }

    private void renderTables(@NotNull Match match, @NotNull PrintWriter writer) {
        final var moves = MATCH_SERVICE.findMoves(match.getId());

        writer.write(TableUtil.renderMatch(match).render());
        writer.write("\n\n");
        writer.write(TableUtil.renderMoves(match, moves).render(16));
        writer.write("\n\n");
        writer.flush();
    }

}