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
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.view.able.ILendFilmView;
import org.jtheque.primary.od.able.Person;

/**
 * A lend controller specification.
 *
 * @author Baptiste Wicht
 */
public interface ILendController extends Controller {
    /**
     * Create a new lending.
     *
     * @param borrower The borrower.
     * @param film     The lend film.
     */
    void newLending(Person borrower, Film film);

    @Override
    ILendFilmView getView();
}
