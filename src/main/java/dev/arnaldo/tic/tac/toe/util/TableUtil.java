package dev.arnaldo.tic.tac.toe.util;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import dev.arnaldo.tic.tac.toe.domain.Match;
import dev.arnaldo.tic.tac.toe.domain.Move;
import dev.arnaldo.tic.tac.toe.domain.User;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class TableUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            .withLocale(Locale.getDefault())
            .withZone(ZoneId.systemDefault());

    @NotNull
    public String getSymbol(@NonNull Match match, long userId) {
        return match.getChallenger().getId() == userId ? "X" : "0";
    }

    @NotNull
    public AsciiTable renderUsers(@NonNull Set<User> users) {
        final var table = new AsciiTable();

        table.addRule();
        table.addRow("ID", "Nome", "Data de criação");
        table.addRule();

        for (final var user : users) {
            table.addRow(
                    user.getId() != null ? user.getId().toString() : "(null)",
                    user.getName(),
                    DATE_FORMATTER.format(user.getCreatedAt())
            );
        }

        table.addRule();
        return table;
    }

    @NotNull
    public AsciiTable renderMatch(@NonNull Match match) {
        final var table = new AsciiTable();
        final var challenger = match.getChallenger();
        final var opponent = match.getOpponent();

        table.addRule();
        table.addRow("Desafiante", challenger.getName(), getSymbol(match, challenger.getId()));
        table.addRule();
        table.addRow("Oponente", opponent.getName(), getSymbol(match, opponent.getId()));
        table.addRule();
        table.setTextAlignment(TextAlignment.CENTER);

        return table;
    }

    @NotNull
    public AsciiTable renderMoves(@NonNull Match match, @NonNull Set<Move> moves) {
        final var table = new AsciiTable();
        final var moveMap = moves.stream().collect(Collectors.toMap(Move::getPosition, Function.identity()));

        table.addRule();

        for (int i = 0; i < 3; i++) {
            final List<String> symbols = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                final var position = (i * 3) + j + 1;
                final var move = moveMap.get(position);
                final var symbol = move != null
                        ? getSymbol(match, move.getUser().getId())
                        : String.valueOf(position);

                symbols.add(symbol);
            }

            table.addRow(symbols);
            table.addRule();
        }

        table.setTextAlignment(TextAlignment.CENTER);


        return table;
    }

}