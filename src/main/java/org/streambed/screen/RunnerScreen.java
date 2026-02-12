package org.streambed.screen;

import com.williamcallahan.tui4j.compat.bubbletea.*;
import com.williamcallahan.tui4j.compat.bubbles.spinner.Spinner;
import com.williamcallahan.tui4j.compat.bubbles.spinner.SpinnerType;
import com.williamcallahan.tui4j.compat.bubbles.spinner.TickMessage;
import com.williamcallahan.tui4j.compat.bubbles.viewport.Viewport;
import org.streambed.model.HydroModel;
import org.streambed.model.ModelService;
import org.streambed.msg.NavigateMsg;
import org.streambed.msg.RunCompleteMsg;
import org.streambed.style.Theme;

import static org.streambed.Screen.DASHBOARD;

/**
 * Executes a selected model, showing a spinner during the run
 * and scrollable results when complete.
 */
public final class RunnerScreen implements Model {

    private HydroModel model;
    private Spinner spinner;
    private Viewport viewport;
    private boolean running;
    private boolean done;
    private int width;
    private int height;

    public RunnerScreen() {
        this.spinner = new Spinner(SpinnerType.DOT);
        this.spinner.setStyle(Theme.SPINNER);
        this.viewport = new Viewport();
        this.running = false;
        this.done = false;
    }

    /** Start a run for the given model. Returns the init command batch. */
    public Command start(HydroModel selectedModel, int termWidth, int termHeight) {
        this.model = selectedModel;
        this.running = true;
        this.done = false;
        this.width = termWidth;
        this.height = termHeight;
        viewport.setWidth(termWidth);
        viewport.setHeight(termHeight - 6); // header + help bar
        viewport.setContent("");
        return Command.batch(spinner.init(), ModelService.runModel(selectedModel));
    }

    @Override
    public Command init() {
        return null;
    }

    @Override
    public UpdateResult<RunnerScreen> update(Message msg) {
        if (msg instanceof KeyPressMessage kpm) {
            return switch (kpm.key()) {
                case "esc", "b" -> UpdateResult.from(this,
                        () -> NavigateMsg.to(DASHBOARD));
                default -> {
                    if (done) {
                        var vr = viewport.update(msg);
                        this.viewport = vr.model();
                        yield UpdateResult.from(this, vr.command());
                    }
                    yield UpdateResult.from(this);
                }
            };
        }

        if (msg instanceof TickMessage) {
            var sr = spinner.update(msg);
            this.spinner = sr.model();
            return UpdateResult.from(this, sr.command());
        }

        if (msg instanceof RunCompleteMsg rcm) {
            this.running = false;
            this.done = true;
            viewport.setContent(rcm.output());
            return UpdateResult.from(this);
        }

        if (msg instanceof WindowSizeMessage wsm) {
            this.width = wsm.width();
            this.height = wsm.height();
            viewport.setWidth(wsm.width());
            viewport.setHeight(wsm.height() - 6);
            return UpdateResult.from(this);
        }

        return UpdateResult.from(this);
    }

    @Override
    public String view() {
        if (model == null) return "";

        var sb = new StringBuilder();
        sb.append(Theme.TITLE.render(" " + model.name() + " ")).append('\n');
        sb.append(Theme.SUBTITLE.render("  " + model.description())).append("\n\n");

        if (running) {
            sb.append("  ").append(spinner.view()).append(" Running simulation...\n");
        } else if (done) {
            sb.append(Theme.SUCCESS.render("  ✓ Complete")).append("\n\n");
            sb.append(viewport.view()).append('\n');
        }

        sb.append('\n');
        String help = done
                ? "  j/k: scroll • esc/b: back to dashboard"
                : "  esc/b: cancel and return to dashboard";
        sb.append(Theme.HELP.render(help));
        return sb.toString();
    }
}
