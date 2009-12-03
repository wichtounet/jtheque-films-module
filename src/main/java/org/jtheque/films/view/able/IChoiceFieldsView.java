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

/**
 * @author Baptiste Wicht
 */
public interface IChoiceFieldsView extends IWindowView {
    /**
     * Indicate if the box for the kind is selected.
     *
     * @return true if the box for the kind is selected else false.
     */
    boolean isBoxKindSelected();

    /**
     * Indicate if the box for the realizer is selected.
     *
     * @return true if the box for the realizer is selected else false.
     */
    boolean isBoxRealizerSelected();

    /**
     * Indicate if the box for the year is selected.
     *
     * @return true if the box for the year is selected else false.
     */
    boolean isBoxYearSelected();

    /**
     * Indicate if the box for the duration is selected.
     *
     * @return true if the box for the duration is selected else false.
     */
    boolean isBoxDurationSelected();

    /**
     * Indicate if the box for the actors is selected.
     *
     * @return true if the box for the actors is selected else false.
     */
    boolean isBoxActorsSelected();

    /**
     * Indicate if the box for the image is selected.
     *
     * @return true if the box for the image is selected else false.
     */
    boolean isBoxImageSelected();

    /**
     * Indicate if the box for the resume is selected.
     *
     * @return true if the box for the resume is selected else false.
     */
    boolean isBoxResumeSelected();
}
