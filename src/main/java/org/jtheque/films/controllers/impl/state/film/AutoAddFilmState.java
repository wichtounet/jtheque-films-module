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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.films.controllers.able.IFilmController;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.films.view.impl.models.able.IFilmsModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.controller.impl.AbstractControllerState;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * A state of film view correspond with an auto add.
 *
 * @author Baptiste Wicht
 */
public final class AutoAddFilmState extends AbstractControllerState {
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
        controller.getView().getToolbarView().setDisplayMode(ViewMode.AUTO);

        getViewModel().getCurrentFilm().saveToMemento();
    }

    @Override
    public ControllerState save(FormBean bean) {
        IFilmFormBean infos = (IFilmFormBean) bean;

        Film film = getViewModel().getCurrentFilm().isSaved() ? getViewModel().getCurrentFilm() : filmsService.getEmptyFilm();

        infos.fillFilm(film);

        if (getViewModel().getCurrentFilm().isSaved()) {
            filmsService.save(film);
        } else {
            filmsService.create(film);
        }

        controller.getView().resort();
        controller.getView().select(film);

        return controller.getViewState();
    }

    @Override
    public ControllerState cancel() {
        if (getViewModel().getCurrentFilm().isSaved()) {
            getViewModel().getCurrentFilm().restoreMemento();
        }

        return controller.getViewState();
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
