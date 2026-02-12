package org.streambed.msg;

import com.williamcallahan.tui4j.compat.bubbletea.Message;
import org.streambed.Screen;
import org.streambed.model.HydroModel;

import java.util.Optional;

/**
 * Message requesting a screen transition.
 *
 * @param target the screen to navigate to
 * @param model  the selected model (present when navigating to RUNNER)
 */
public record NavigateMsg(Screen target, Optional<HydroModel> model) implements Message {

    public NavigateMsg {
        java.util.Objects.requireNonNull(target, "target");
        java.util.Objects.requireNonNull(model, "model");
    }

    /** Navigate to a screen without a model context. */
    public static NavigateMsg to(Screen target) {
        return new NavigateMsg(target, Optional.empty());
    }

    /** Navigate to RUNNER with a selected model. */
    public static NavigateMsg toRunner(HydroModel hydroModel) {
        return new NavigateMsg(Screen.RUNNER, Optional.of(hydroModel));
    }
}
