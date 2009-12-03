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

import org.jtheque.core.managers.view.able.controller.Controller;
import org.jtheque.films.services.impl.utils.EditArguments;
import org.jtheque.films.services.impl.utils.web.FilmResult;
import org.jtheque.films.view.able.IAutoAddView;

/**
 * An auto add controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IAutoAddController extends Controller {
    /**
     * Add a new film with the auto add view.
     */
    void add();

    /**
     * Edit a film with the auto add view.
     */
    void edit();

    /**
     * Set the arguments of the edit, it seems the fields we edit or not.
     *
     * @param args The arguments of the edit.
     */
    void setFields(EditArguments args);

    /**
     * Auto edit the current film.
     *
     * @param selectedFilm The film on the site to get informations
     */
    void auto(FilmResult selectedFilm);

    @Override
    IAutoAddView getView();
}
