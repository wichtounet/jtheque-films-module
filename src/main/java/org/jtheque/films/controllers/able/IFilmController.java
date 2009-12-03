package org.jtheque.films.controllers.able;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.view.able.IFilmView;
import org.jtheque.films.view.impl.models.able.IFilmsModel;
import org.jtheque.primary.controller.able.IPrincipalController;

import javax.swing.event.TreeSelectionListener;
import java.awt.event.MouseListener;

/**
 * A film controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IFilmController extends IPrincipalController<Film>, MouseListener, TreeSelectionListener {
    /**
     * Save the current film.
     */
    void save();

    /**
     * Display a film in the interface.
     *
     * @param film The film to edit.
     */
    void view(Film film);

    /**
     * Edit manually a film.
     */
    void manualEdit();

    /**
     * Create a new film.
     */
    void createFilm();

    /**
     * Delete the current film.
     */
    void deleteCurrentFilm();

    /**
     * Cancel the current state.
     */
    void cancel();

    /**
     * Send the current film by email.
     */
    void sendCurrentFilmByMail();

    /**
     * Print the current film.
     */
    void printCurrentFilm();

    @Override
    IFilmsModel getViewModel();

    @Override
    IFilmView getView();
}
