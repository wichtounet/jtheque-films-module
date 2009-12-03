package org.jtheque.films.view.impl.models.able;

import org.jtheque.core.managers.view.able.components.IModel;
import org.jtheque.films.services.impl.utils.web.FilmResult;

import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public interface IAutoAddModel extends IModel {
    /**
     * Return the results of the search.
     *
     * @return A list of the results of the search.
     */
    Collection<FilmResult> getResults();

    /**
     * Set the results of the search.
     *
     * @param results The results to set.
     */
    void setResults(Collection<FilmResult> results);
}
