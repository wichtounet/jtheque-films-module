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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.primary.view.impl.listeners.CurrentObjectListener;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import java.util.Collection;

/**
 * @author Baptiste Wicht
 */
public interface IInfosActorsView extends CurrentObjectListener {

    /**
     * Return the model of the list of actors.
     *
     * @return The <code>DefaultListModel</code> associated to the list of actors.
     */
    DefaultListModel getActorsModel();

    /**
     * Return the actors for film model.
     *
     * @return The actors for film model.
     */
    DefaultListModel getActorsForFilmModel();

    /**
     * Return the indexes of the selected actors.
     *
     * @return An array containing the indexes of the selected actors.
     */
    int[] getSelectedActorsIndexes();

    /**
     * Return the indexes of the selected actors for film.
     *
     * @return An array containing the indexes of the selected actors for film.
     */
    int[] getSelectedActorsForFilmIndexes();

    /**
     * Validate the view.
     *
     * @param errors The errors list to fill.
     */
    void validate(Collection<JThequeError> errors);

    /**
     * Fill the form bean.
     *
     * @param fb The form bean to fill.
     */
    void fillFilm(IFilmFormBean fb);

    /**
     * Set if the view is enabled or disabled.
     *
     * @param enabled A boolean tag indicating if the view must be enabled or not.
     */
    void setEnabled(boolean enabled);

    /**
     * Return the component implementation.
     *
     * @return The real implementation of the view.
     */
    JComponent getImpl();
}
