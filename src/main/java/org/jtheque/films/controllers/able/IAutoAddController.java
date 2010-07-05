package org.jtheque.films.controllers.able;

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

import org.jtheque.core.managers.view.able.controller.Controller;
import org.jtheque.films.services.impl.utils.EditArguments;
import org.jtheque.films.services.impl.utils.web.FilmResult;
import org.jtheque.films.view.able.IAutoAddView;

/**
 * An auto add controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IAutoAddController extends Controller {
    /**
     * Add a new film with the auto add view.
     */
    void add();

    /**
     * Edit a film with the auto add view.
     */
    void edit();

    /**
     * Set the arguments of the edit, it seems the fields we edit or not.
     *
     * @param args The arguments of the edit.
     */
    void setFields(EditArguments args);

    /**
     * Auto edit the current film.
     *
     * @param selectedFilm The film on the site to get informations
     */
    void auto(FilmResult selectedFilm);

    @Override
    IAutoAddView getView();
}
