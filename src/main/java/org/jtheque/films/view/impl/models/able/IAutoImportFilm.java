package org.jtheque.films.view.impl.models.able;

import org.jtheque.core.managers.view.able.components.IModel;

import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public interface IAutoImportFilm extends IModel {
    /**
     * Return the results of the search.
     *
     * @return A list of the results of the search.
     */
    Collection<String> getTitles();

    /**
     * Set the results of the search.
     *
     * @param titles The results to set.
     */
    void setTitles(Collection<String> titles);
}
