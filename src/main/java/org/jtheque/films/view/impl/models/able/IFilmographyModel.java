package org.jtheque.films.view.impl.models.able;

import org.jtheque.core.managers.view.able.components.IModel;

/**
 * @author Baptiste Wicht
 */
public interface IFilmographyModel extends IModel {
    /**
     * Set the filmography.
     *
     * @param filmo The filmography.
     */
    void setFilmo(Object filmo);

    /**
     * Return the filmography.
     *
     * @return The filmography.
     */
    Object getFilmo();

    /**
     * Set the flag indicating if the view is builded or not.
     */
    void setBuilded();

    /**
     * Indicate if the view is builded or not.
     *
     * @return true if the view is builded else false,.
     */
    boolean isBuilded();
}
