package org.streambed.model;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import org.streambed.msg.RunCompleteMsg;

import java.util.List;

/**
 * Stub service providing hardcoded hydro models.
 *
 * <p>Future: this will become a gRPC client that streams results
 * from running model containers.
 */
public final class ModelService {

    private ModelService() {} // non-instantiable

    private static final List<HydroModel> MODELS = List.of(
            new HydroModel("hec-ras",
                    "Model A",
                    "Sample text A"),
            new HydroModel("epa-swmm",
                    "Model B",
                    "Sample text B"),
            new HydroModel("lisflood-fp",
                    "Model C",
                    "Sample text C")
    );

    /** Returns the catalogue of available models. */
    public static List<HydroModel> listModels() {
        return MODELS;
    }

    /**
     * Returns a {@link Command} that simulates running the given model.
     *
     * <p>Sleeps for 2 seconds, then emits a {@link RunCompleteMsg} with
     * placeholder results. // TODO: replace with gRPC streaming call
     */
    public static Command runModel(HydroModel model) {
        return () -> {
            try {
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return new RunCompleteMsg(
                    "═══ " + model.name() + " Results ═══\n\n"
                    + "Simulation completed successfully.\n\n"
                    + "  Peak discharge : 1,247.3 m³/s\n"
                    + "  Time to peak   : 14.5 hours\n"
                    + "  Max stage      : 8.92 m\n"
                    + "  Flooded area   : 23.7 km²\n\n"
                    + "Output written to /results/" + model.id() + "/run_001/\n"
            );
        };
    }
}
