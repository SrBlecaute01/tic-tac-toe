package dev.arnaldo.tic.tac.toe.view;

import dev.arnaldo.tic.tac.toe.TicTacToe;
import dev.arnaldo.tic.tac.toe.service.UserService;
import dev.arnaldo.tic.tac.toe.util.TableUtil;
import dev.arnaldo.tic.tac.toe.util.view.InteractiveMenu;
import dev.arnaldo.tic.tac.toe.util.view.InteractiveOption;
import dev.arnaldo.tic.tac.toe.util.view.ViewUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jline.terminal.Terminal;

public class UsersView {

    private static final UserService USER_SERVICE = TicTacToe.getInstance().getUserService();

    @SneakyThrows
    public UsersView(Terminal terminal) {
        final var menu = new InteractiveMenu(terminal);
        for (var option : Options.values()) {
            menu.addOption(option.option);
        }
        menu.display();
    }

    @RequiredArgsConstructor
    private enum Options {

        LIST_USERS(InteractiveOption.builder()
                .label("Listar Usuários")
                .onSelect((terminal, writer) -> {
                    ViewUtil.clearTerminal(terminal);

                    writer.println();
                    writer.println(TableUtil.renderUsers(USER_SERVICE.findUsers()).render());
                    writer.flush();

                    new UsersView(terminal);
                }).build()),

        MENU(InteractiveOption.builder()
                .label("Menu Principal")
                .onSelect((terminal, writer) -> {
                    ViewUtil.clearTerminal(terminal);
                    new HomeView(terminal);
                }).build())

        ;

        private final InteractiveOption option;

    }

}
