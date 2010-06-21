package org.jtheque.films.view.able;

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

import org.jtheque.core.managers.view.able.IWindowView;
import org.jtheque.films.services.impl.utils.web.FilmResult;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.view.impl.models.able.IAutoAddModel;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;

/**
 * @author Baptiste Wicht
 */
public interface IAutoAddView extends IWindowView {
    int PHASE_1 = 1;
    int PHASE_2 = 2;

    /**
     * Return the field of title.
     *
     * @return The JTextField used to set the title of the film searched.
     */
    JTextField getFieldTitle();

    /**
     * Return the model of the list of the films.
     *
     * @return The ListModel used for the of the films.
     */
    DefaultListModel getModelListFilms();

    /**
     * Return the selected site.
     *
     * @return The selected site.
     */
    Constants.Site getSelectedSite();

    /**
     * Return the selected film.
     *
     * @return The selected film.
     */
    FilmResult getSelectedFilm();

    @Override
    IAutoAddModel getModel();

    /**
     * Validate the content of the view.
     *
     * @param phase The current process phase.
     *
     * @return The result of the validation.
     */
    boolean validateContent(int phase);

    /**
     * Stop the wait animation.
     */
    void stopWait();

    /**
     * Start the wait animation.
     */
    void startWait();
}
