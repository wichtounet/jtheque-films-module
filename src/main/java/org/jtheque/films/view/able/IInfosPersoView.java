package org.jtheque.films.view.able;

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.primary.view.impl.listeners.CurrentObjectListener;

import javax.swing.JComponent;

import java.util.Collection;

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

/**
 * A view for the personal informations specification.
 *
 * @author Baptiste Wicht
 */
public interface IInfosPersoView extends CurrentObjectListener {
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
     * Return the view implementation.
     *
     * @return The real implementation.
     */
    JComponent getImpl();
}