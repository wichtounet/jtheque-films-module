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
