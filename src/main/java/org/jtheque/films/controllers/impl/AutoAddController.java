package org.jtheque.films.controllers.impl;

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