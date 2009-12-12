package org.jtheque.films.controllers.impl.state.film;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.films.controllers.able.IFilmController;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.films.view.impl.models.able.IFilmsModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * A film controller state for the state "modify".
 *
 * @author Baptiste Wicht
 */
public final class ModifyFilmState implements ControllerState {
    @Resource
    private IFilmController controller;

    @Resource
    private IFilmsService filmsService;

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    private IFilmsModel getViewModel() {
        return controller.getViewModel();
    }

    @Override
    public void apply() {
        controller.getView().setEnabled(true);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.EDIT);

        getViewModel().getCurrentFilm().saveToMemento();
    }

    @Override
    public ControllerState save(FormBean bean) {
        IFilmFormBean infos = (IFilmFormBean) bean;

        Film film = getViewModel().getCurrentFilm();

        String oldTitle = film.getTitle();

        infos.fillFilm(film);

        filmsService.save(film);

        if (!oldTitle.equals(film.getTitle())) {
            controller.getView().resort();
        }

        return controller.getViewState();
    }

    @Override
    public ControllerState cancel() {
        getViewModel().getCurrentFilm().restoreMemento();

        return controller.getViewState();
    }

    @Override
    public ControllerState create() {
        //Do nothing

        return null;
    }

    @Override
    public ControllerState manualEdit() {
        //Do nothing

        return null;
    }

    @Override
    public ControllerState delete() {
        //Do nothing

        return null;
    }

    @Override
    public ControllerState autoEdit(Data data) {
        Film film = (Film) data;

        if (Managers.getManager(IViewManager.class).askI18nUserForConfirmation(
                "film.dialogs.confirmSave", "film.dialogs.confirmSave.title")) {
            controller.save();
        } else {
            getViewModel().getCurrentFilm().restoreMemento();
        }

        getViewModel().setCurrentFilm(film);

        return controller.getAutoAddState();
    }

    @Override
    public ControllerState view(Data data) {
        Film film = (Film) data;

        if (Managers.getManager(IViewManager.class).askI18nUserForConfirmation(
                "film.dialogs.confirmSave", "film.dialogs.confirmSave.title")) {
            controller.save();
        } else {
            getViewModel().getCurrentFilm().restoreMemento();
        }

        getViewModel().setCurrentFilm(film);

        return controller.getViewState();
    }
}