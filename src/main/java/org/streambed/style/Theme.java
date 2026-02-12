package org.streambed.style;

import com.williamcallahan.tui4j.compat.lipgloss.Style;
import com.williamcallahan.tui4j.compat.lipgloss.color.Color;
import com.williamcallahan.tui4j.compat.lipgloss.color.TerminalColor;

/**
 * Centralized Lipgloss styles derived from the StreamBed logo palette.
 *
 * <p>All styles are pre-built singletons. Because {@link Style} is mutable,
 * callers that need a modified variant must call {@code copy()} first.
 */
public final class Theme {

    private Theme() {} // non-instantiable

    // ── Palette ──────────────────────────────────────────────────────
    public static final TerminalColor LIGHT_BLUE  = Color.color("#66CCFF");
    public static final TerminalColor BRIGHT_BLUE = Color.color("#0055FF");
    public static final TerminalColor DARK_NAVY   = Color.color("#000088");
    public static final TerminalColor WHITE        = Color.color("#E0E0E0");
    public static final TerminalColor DIM_GREY    = Color.color("#4A4A4A");

    // ── Styles ───────────────────────────────────────────────────────
    public static final Style TITLE = Style.newStyle()
            .foreground(LIGHT_BLUE)
            .background(DARK_NAVY)
            .bold(true)
            .padding(0, 1);

    public static final Style SUBTITLE = Style.newStyle()
            .foreground(DIM_GREY)
            .italic(true);

    public static final Style SELECTED_ITEM = Style.newStyle()
            .foreground(BRIGHT_BLUE)
            .bold(true);

    public static final Style NORMAL_ITEM = Style.newStyle()
            .foreground(WHITE);

    public static final Style SPINNER = Style.newStyle()
            .foreground(BRIGHT_BLUE);

    public static final Style SUCCESS = Style.newStyle()
            .foreground(LIGHT_BLUE)
            .bold(true);

    public static final Style HELP = Style.newStyle()
            .foreground(DIM_GREY);

    public static final Style LOGO = Style.newStyle()
            .foreground(LIGHT_BLUE);
}
