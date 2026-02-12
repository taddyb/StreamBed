package org.streambed.screen;

import com.williamcallahan.tui4j.compat.bubbletea.*;
import org.streambed.model.HydroModel;
import org.streambed.model.ModelService;
import org.streambed.msg.NavigateMsg;
import org.streambed.style.Logo;
import org.streambed.style.Theme;

import java.util.List;

/**
 * Main menu screen listing available hydro models for selection.
 *
 * <p>Keyboard: j/down = next, k/up = previous, enter = run, q = quit.
 */
public final class DashboardScreen implements Model {

    private final List<HydroModel> models;
    private int cursor;

    public DashboardScreen() {
        this.models = ModelService.listModels();
        this.cursor = 0;
    }

    @Override
    public Command init() {
        return null;
    }

    @Override
    public UpdateResult<DashboardScreen> update(Message msg) {
        if (msg instanceof KeyPressMessage kpm) {
            return switch (kpm.key()) {
                case "q"     -> UpdateResult.from(this, Command.quit());
                case "up", "k" -> {
                    if (cursor > 0) cursor--;
                    yield UpdateResult.from(this);
                }
                case "down", "j" -> {
                    if (cursor < models.size() - 1) cursor++;
                    yield UpdateResult.from(this);
                }
                case "enter" -> UpdateResult.from(this,
                        () -> NavigateMsg.toRunner(models.get(cursor)));
                default -> UpdateResult.from(this);
            };
        }
        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        var sb = new StringBuilder();

        sb.append(Theme.LOGO.render(Logo.ART)).append('\n');
        sb.append(Theme.SUBTITLE.render("  Hydro Model Orchestrator")).append("\n\n");

        for (int i = 0; i < models.size(); i++) {
            HydroModel m = models.get(i);
            String prefix = (i == cursor) ? "▸ " : "  ";
            String line = prefix + m.name() + "  —  " + m.description();
            sb.append(i == cursor
                    ? Theme.SELECTED_ITEM.render(line)
                    : Theme.NORMAL_ITEM.render(line));
            sb.append('\n');
        }

        sb.append('\n');
        sb.append(Theme.HELP.render("  j/k: navigate • enter: run model • q: quit"));
        return sb.toString();
    }
}
