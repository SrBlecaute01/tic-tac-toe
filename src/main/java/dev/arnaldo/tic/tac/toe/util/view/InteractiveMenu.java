package dev.arnaldo.tic.tac.toe.util.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jline.keymap.BindingReader;
import org.jline.keymap.KeyMap;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp.Capability;

@Getter
@RequiredArgsConstructor
public final class InteractiveMenu {

    private final Terminal term;
    private final List<InteractiveOption> options = new ArrayList<>();

    public void display() throws IOException {
        fancyMenu();
    }

    public void addOption(InteractiveOption option) {
        this.options.add(option);
    }

    private int fancyMenu() throws IOException {
        var selection = 0;
        term.enterRawMode();

        final var writer = term.writer();
        final var keyMap = new KeyMap<String>();

        keyMap.setAmbiguousTimeout(200);
        keyMap.bind("up", KeyMap.key(term, Capability.key_up));
        keyMap.bind("down", KeyMap.key(term, Capability.key_down));
        keyMap.bind("exit", KeyMap.esc(), KeyMap.ctrl('c'));
        keyMap.bind("enter", "\r");

        writer.println();

        final var bindingReader = new BindingReader(term.reader());
        for (int i = 0; i < options.size(); i++) {
            final var leftHand = i == 0 ? "> " : "  ";
            writer.println(leftHand + (i + 1) + ". " + options.get(i).getLabel());
        }

        writer.println("  " + (options.size() + 1) + ". Exit");
        writer.flush();

        while (true) {
            var prevSelection = selection;
            var key = bindingReader.readBinding(keyMap);

            switch (key) {
                case "up":
                    if (--selection == -1) selection = options.size();
                    break;
                case "down":
                    if (++selection > options.size()) selection = 0;
                    break;
                case "exit":
                    return -1;
                case "enter":
                    if (selection == options.size()) return -1;
                    options.get(selection).getOnSelect().accept(term, writer);
                    return -1;
            }

            printUp(term, writer, " ", options.size() + 1 - prevSelection);
            printUp(term, writer, ">", options.size() + 1 - selection);
        }
    }

    private void printUp(Terminal terminal, PrintWriter writer, String s, int up) {
        for (int i = 0; i < up; i++) {
            terminal.puts(Capability.cursor_up);
        }

        writer.print(s);
        writer.flush();

        for (int i = 0; i < up; i++) {
            terminal.puts(Capability.cursor_down);
        }

        for (int i = 0; i < s.length(); i++) {
            terminal.puts(Capability.cursor_left);
        }

        terminal.flush();
    }
}