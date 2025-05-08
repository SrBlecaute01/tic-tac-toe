package dev.arnaldo.tic.tac.toe.util.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jline.terminal.Terminal;

import java.io.PrintWriter;
import java.util.function.BiConsumer;

@Data
@Builder
@AllArgsConstructor
public class InteractiveOption {

    private final String label;
    private final BiConsumer<Terminal, PrintWriter> onSelect;

}