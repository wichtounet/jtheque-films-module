package org.jtheque.films.view.able;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.primary.view.impl.listeners.CurrentObjectListener;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;

import java.util.Collection;

/**
 * A view for the kinds of the film specification.
 *
 * @author Baptiste Wicht
 */
public interface IInfosKindsView extends CurrentObjectListener {

    /**
     * Return the model of the list of kinds.
     *
     * @return The <code>DefaultListModel</code> associated to the list of kinds.
     */
    DefaultListModel getKindsModel();

    /**
     * Return the kinds for film model.
     *
     * @return The kinds for film model.
     */
    DefaultListModel getKindsForFilmModel();

    /**
     * Return the indexes of the selected kinds.
     *
     * @return An array containing the indexes of the selected kinds.
     */
    int[] getSelectedKindsIndexes();

    /**
     * Return the indexes of the selected kinds for film.
     *
     * @return An array containing the indexes of the selected kinds for film.
     */
    int[] getSelectedKindsForFilmIndexes();

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
