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
