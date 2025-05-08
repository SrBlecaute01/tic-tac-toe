package dev.arnaldo.tic.tac.toe.view;

import dev.arnaldo.tic.tac.toe.util.view.InteractiveMenu;
import dev.arnaldo.tic.tac.toe.util.view.InteractiveOption;
import dev.arnaldo.tic.tac.toe.util.view.ViewUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class HomeView {

    @SneakyThrows
    public HomeView(Terminal terminal)  {
        final var menu = new InteractiveMenu(terminal == null
                ? TerminalBuilder.builder().ffm(true).dumb(false).build()
                : terminal);

        if (terminal == null) ViewUtil.clearTerminal(menu.getTerm());
        for (var option : Options.values()) {
            menu.addOption(option.option);
        }

        menu.display();
    }

    @RequiredArgsConstructor
    private enum Options {

        USERS_HOME(InteractiveOption.builder()
                .label("Gerenciar Usuários")
                .onSelect((terminal, unused) -> {
                    ViewUtil.clearTerminal(terminal);
                    new UsersView(terminal);
                }).build()),

        INIT_MATCH(InteractiveOption.builder()
                .label("Iniciar Partida")
                .onSelect((terminal, writer) -> {
                    ViewUtil.clearTerminal(terminal);
                    new MatchView(terminal);
                }).build()),

        ;

        private final InteractiveOption option;

    }

}
