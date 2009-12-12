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
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.films.controllers.able.IFilmController;
import org.jtheque.films.controllers.impl.undo.DeletedFilmEdit;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.view.impl.models.able.IFilmsModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * A film controller state for the state "view".
 *
 * @author Baptiste Wicht
 */
public final class ViewFilmState implements ControllerState {
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
        controller.getView().setEnabled(false);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.VIEW);

        if (getViewModel().getCurrentFilm() != null) {
            controller.getView().select(getViewModel().getCurrentFilm());
        }
    }

    @Override
    public ControllerState save(FormBean bean) {
        //Do nothing

        return null;
    }

    @Override
    public ControllerState cancel() {
        //Do nothing

        return null;
    }

    @Override
    public ControllerState create() {
        return controller.getNewObjectState();
    }

    @Override
    public ControllerState manualEdit() {
        return controller.getModifyState();
    }

    @Override
    public ControllerState delete() {
        boolean deleted = filmsService.delete(getViewModel().getCurrentFilm());

        if (deleted) {
            Managers.getManager(IUndoRedoManager.class).addEdit(new DeletedFilmEdit(getViewModel().getCurrentFilm()));

            controller.getView().selectFirst();
        }

        return null;
    }

    @Override
    public ControllerState autoEdit(Data data) {
        Film film = (Film) data;

        getViewModel().setCurrentFilm(film);

        return controller.getAutoAddState();
    }

    @Override
    public ControllerState view(Data data) {
        Film film = (Film) data;

        getViewModel().setCurrentFilm(film);

        return null;
    }
}