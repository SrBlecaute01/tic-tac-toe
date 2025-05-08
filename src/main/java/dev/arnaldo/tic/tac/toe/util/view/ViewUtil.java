package dev.arnaldo.tic.tac.toe.util.view;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.jline.terminal.Terminal;
import org.jline.utils.InfoCmp;

@UtilityClass
public class ViewUtil {

    public void clearTerminal(@NonNull Terminal terminal) {
        terminal.puts(InfoCmp.Capability.clear_screen);
        terminal.flush();
    }

}
