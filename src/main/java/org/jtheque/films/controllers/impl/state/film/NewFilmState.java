package org.jtheque.films.controllers.impl.state.film;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.films.controllers.able.IFilmController;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.films.view.impl.models.able.IFilmsModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.controller.impl.AbstractControllerState;
import org.jtheque.primary.controller.impl.undo.GenericDataCreatedEdit;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * A film controller state for the state "new".
 *
 * @author Baptiste Wicht
 */
public final class NewFilmState extends AbstractControllerState {
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
        getViewModel().setCurrentFilm(filmsService.getDefaultFilm());
        controller.getView().setEnabled(true);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.NEW);
    }

    @Override
    public ControllerState save(FormBean bean) {
        IFilmFormBean infos = (IFilmFormBean) bean;

        Film film = filmsService.getEmptyFilm();

        infos.fillFilm(film);

        filmsService.create(film);

        Managers.getManager(IUndoRedoManager.class).addEdit(new GenericDataCreatedEdit<Film>("filmsService", film));

        controller.getView().resort();

        return controller.getViewState();
    }

    @Override
    public ControllerState cancel() {
        ControllerState nextState = null;

        controller.getView().selectFirst();

        if (filmsService.isNoFilms()) {
            nextState = controller.getViewState();
        }

        return nextState;
    }

    @Override
    public ControllerState view(Data data) {
        Film film = (Film) data;

        if (Managers.getManager(IViewManager.class).askI18nUserForConfirmation(
                "film.dialogs.confirmSave", "film.dialogs.confirmSave.title")) {
            controller.save();
        }

        getViewModel().setCurrentFilm(film);

        return controller.getViewState();
    }
}
