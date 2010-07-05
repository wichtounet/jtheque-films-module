package org.jtheque.films.controllers.impl;

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

import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.films.controllers.able.IAutoAddController;
import org.jtheque.films.controllers.able.IFilmController;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmAutoService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.impl.utils.EditArguments;
import org.jtheque.films.services.impl.utils.web.FilmResult;
import org.jtheque.films.view.able.IAutoAddView;

import javax.annotation.Resource;

/**
 * An auto add controller implementation.
 *
 * @author Baptiste Wicht
 */
public final class AutoAddController extends AbstractController implements IAutoAddController {
    private boolean edit;
    private EditArguments args;

    @Resource
    private IFilmAutoService filmAutoService;

    @Resource
    private IFilmsService filmsService;

    @Resource
    private IAutoAddView autoAddView;

    @Resource
    private IFilmController filmController;

    @Override
    public IAutoAddView getView() {
        return autoAddView;
    }

    @Override
    public void add() {
        autoAddView.display();
    }

    @Override
    public void edit() {
        displayView();
    }

    @Override
    public void setFields(EditArguments args) {
        edit = true;
        this.args = args;
    }

    @Override
    public void auto(FilmResult selectedFilm) {
        if (edit) {
            Film film = filmController.getViewModel().getCurrentFilm();

            filmAutoService.modifyFilm(selectedFilm, film, args);

            filmController.view(film);
            filmController.manualEdit();
        } else {
            Film film = filmAutoService.getFilm(selectedFilm);

            filmsService.create(film);

            filmController.view(film);
        }
    }

    @Override
    public void displayView() {
        super.displayView();

        if (edit) {
            autoAddView.sendMessage("title", filmController.getViewModel().getCurrentFilm().getTitle());
        }
    }
}
