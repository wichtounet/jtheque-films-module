package org.jtheque.films.controllers.impl.state.actor;

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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.films.controllers.able.IActorController;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.view.impl.fb.IPersonFormBean;
import org.jtheque.films.view.impl.models.able.IActorsModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.controller.impl.undo.CreatedPersonEdit;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * A actor controller state for the state "new".
 *
 * @author Baptiste Wicht
 */
public final class NewActorState implements ControllerState {
    @Resource
    private IActorController controller;

    @Resource
    private IActorService actorService;

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    private IActorsModel getViewModel() {
        return controller.getViewModel();
    }

    @Override
    public void apply() {
        getViewModel().setCurrentActor(actorService.getDefaultActor());
        controller.getView().setEnabled(true);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.NEW);
    }

    @Override
    public ControllerState save(FormBean bean) {
        IPersonFormBean infos = (IPersonFormBean) bean;

        Person actor = actorService.getEmptyActor();

        actor.setName(infos.getName());
        actor.setFirstName(infos.getFirstName());
        actor.setNote(infos.getNote());
        actor.setTheCountry(infos.getCountry());

        actorService.create(actor);

        Managers.getManager(IUndoRedoManager.class).addEdit(new CreatedPersonEdit(actor));

        controller.getView().resort();

        return controller.getViewState();
    }

    @Override
    public ControllerState cancel() {
        ControllerState nextState = null;

        controller.getView().selectFirst();

        if (actorService.hasNoActor()) {
            nextState = controller.getViewState();
        }

        return nextState;
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
        Person actor = (Person) data;

        if (Managers.getManager(IViewManager.class).askI18nUserForConfirmation(
                "actor.dialogs.confirmSave", "actor.dialogs.confirmSave.title")) {
            controller.save();
        }

        getViewModel().setCurrentActor(actor);

        return controller.getAutoAddState();
    }

    @Override
    public ControllerState view(Data data) {
        Person actor = (Person) data;

        if (Managers.getManager(IViewManager.class).askI18nUserForConfirmation(
                "actor.dialogs.confirmSave", "actor.dialogs.confirmSave.title")) {
            controller.save();
        }

        getViewModel().setCurrentActor(actor);

        return controller.getViewState();
    }
}