package org.streambed;

import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.ProgramOption;

/**
 * Entry point for the StreamBed TUI orchestrator.
 */
public final class StreamBed {

    private StreamBed() {} // non-instantiable

    public static void main(String[] args) {
        new Program(new App(), ProgramOption.withAltScreen()).run();
    }
}
