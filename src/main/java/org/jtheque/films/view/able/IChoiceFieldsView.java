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
