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

import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.view.able.IFilmView;
import org.jtheque.films.view.impl.models.able.IFilmsModel;
import org.jtheque.primary.controller.able.IPrincipalController;

import javax.swing.event.TreeSelectionListener;

import java.awt.event.MouseListener;

/**
 * A film controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IFilmController extends IPrincipalController<Film>, MouseListener, TreeSelectionListener {
    /**
     * Save the current film.
     */
    void save();

    /**
     * Send the current film by email.
     */
    void sendCurrentFilmByMail();

    /**
     * Print the current film.
     */
    void printCurrentFilm();

    @Override
    IFilmsModel getViewModel();

    @Override
    IFilmView getView();
}
