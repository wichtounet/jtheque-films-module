package org.jtheque.films.view.able;

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.primary.view.impl.listeners.CurrentObjectListener;

import javax.swing.JComponent;

import java.util.Collection;

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

/**
 * @author Baptiste Wicht
 */
public interface IInfosOthersView extends CurrentObjectListener {
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
