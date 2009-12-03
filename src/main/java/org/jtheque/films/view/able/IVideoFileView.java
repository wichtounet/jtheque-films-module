package org.jtheque.films.view.able;

import org.jtheque.core.managers.view.able.IWindowView;
import org.jtheque.films.persistence.od.able.Film;

/**
 * A video file view specification.
 *
 * @author Baptiste Wicht
 */
public interface IVideoFileView extends IWindowView {
    /**
     * Return the specified file path.
     *
     * @return The specified file path.
     */
    String getFilePath();

    /**
     * Return the selected film.
     *
     * @return The selected film.
     */
    Film getFilm();
}
