package org.jtheque.films;

import org.jtheque.core.managers.feature.Feature;
import org.jtheque.films.services.impl.utils.config.Configuration;

/**
 * @author Baptiste Wicht
 */
public interface IFilmsModule {
    /**
     * Return the configuration of the module.
     *
     * @return The configuration of the module.
     */
    Configuration getConfiguration();

    /**
     * Return the films feature.
     *
     * @return The feature for films.
     */
    Feature getFilmsFeature();

    /**
     * Return the actors feature.
     *
     * @return The feature for actors.
     */
    Feature getActorsFeature();

    /**
     * Return the realizers feature.
     *
     * @return The feature for realizers.
     */
    Feature getRealizersFeature();
}
