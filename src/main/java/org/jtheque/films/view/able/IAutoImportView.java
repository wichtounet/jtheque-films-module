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
import org.jtheque.films.utils.Constants;
import org.jtheque.films.view.impl.models.able.IAutoImportFilm;

import javax.swing.DefaultListModel;

/**
 * @author Baptiste Wicht
 */
public interface IAutoImportView extends IWindowView {
    int PHASE_1 = 1;
    int PHASE_2 = 2;

    /**
     * Return the path to the folder.
     *
     * @return The path to the choose folder.
     */
    String getFolderPath();

    /**
     * Return the selected title.
     *
     * @return The selected title.
     */
    String getSelectedTitle();

    /**
     * Return the model of the list titles.
     *
     * @return The title's list model.
     */
    DefaultListModel getModelTitles();

    /**
     * Indicate if the mode is file.
     *
     * @return true if the mode is file else false if the mode is folder.
     */
    boolean isFileMode();

    /**
     * Indicate if the mode is web.
     *
     * @return true if the mode is web else false if the mode is default.
     */
    boolean isWebMode();

    /**
     * Return the selected site enum value.
     *
     * @return The selected site enum value.
     */
    Constants.Site getSelectedSite();

    /**
     * Validate the content of the view.
     *
     * @param phase The current process phase.
     *
     * @return The result of the validation.
     */
    boolean validateContent(int phase);

    @Override
    IAutoImportFilm getModel();

    /**
     * Stop the wait animation.
     */
    void stopWait();

    /**
     * Start the wait animation.
     */
    void startWait();
}
