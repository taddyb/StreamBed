package org.streambed;

import com.williamcallahan.tui4j.compat.bubbletea.*;
import org.streambed.msg.NavigateMsg;
import org.streambed.screen.DashboardScreen;
import org.streambed.screen.RunnerScreen;

/**
 * Top-level Elm Architecture model that routes between screens.
 */
public final class App implements Model {

    private Screen activeScreen;
    private DashboardScreen dashboard;
    private RunnerScreen runner;
    private int width;
    private int height;

    public App() {
        this.dashboard = new DashboardScreen();
        this.runner = new RunnerScreen();
        this.activeScreen = Screen.DASHBOARD;
    }

    @Override
    public Command init() {
        return Command.batch(dashboard.init(), Command.checkWindowSize());
    }

    @Override
    public UpdateResult<App> update(Message msg) {
        // ctrl+c always quits regardless of active screen
        if (msg instanceof KeyPressMessage kpm && "ctrl+c".equals(kpm.key())) {
            return UpdateResult.from(this, Command.quit());
        }

        // Store terminal dimensions
        if (msg instanceof WindowSizeMessage wsm) {
            this.width = wsm.width();
            this.height = wsm.height();
            // Forward to runner so it can resize viewport
            runner.update(msg);
            return UpdateResult.from(this);
        }

        // Screen navigation
        if (msg instanceof NavigateMsg nav) {
            this.activeScreen = nav.target();
            return switch (nav.target()) {
                case DASHBOARD -> {
                    this.dashboard = new DashboardScreen();
                    yield UpdateResult.from(this, dashboard.init());
                }
                case RUNNER -> {
                    if (nav.model().isPresent()) {
                        Command cmd = runner.start(nav.model().get(), width, height);
                        yield UpdateResult.from(this, cmd);
                    }
                    yield UpdateResult.from(this);
                }
            };
        }

        // Delegate to active screen
        return switch (activeScreen) {
            case DASHBOARD -> {
                var result = dashboard.update(msg);
                this.dashboard = result.model();
                yield UpdateResult.from(this, result.command());
            }
            case RUNNER -> {
                var result = runner.update(msg);
                this.runner = result.model();
                yield UpdateResult.from(this, result.command());
            }
        };
    }

    @Override
    public String view() {
        return switch (activeScreen) {
            case DASHBOARD -> dashboard.view();
            case RUNNER    -> runner.view();
        };
    }
}
