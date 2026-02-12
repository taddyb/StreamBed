package org.streambed.model;

/**
 * Immutable descriptor of a hydrological model available for orchestration.
 */
public record HydroModel(String id, String name, String description) {

    public HydroModel {
        java.util.Objects.requireNonNull(id, "id");
        java.util.Objects.requireNonNull(name, "name");
        java.util.Objects.requireNonNull(description, "description");
    }
}
