package org.streambed.msg;

import com.williamcallahan.tui4j.compat.bubbletea.Message;

/**
 * Message indicating a stub model run has finished.
 *
 * @param output the (placeholder) result text
 */
public record RunCompleteMsg(String output) implements Message {

    public RunCompleteMsg {
        java.util.Objects.requireNonNull(output, "output");
    }
}
