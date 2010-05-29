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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.films.controllers.able.IActorController;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.view.impl.models.able.IActorsModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.controller.impl.AbstractControllerState;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.able.ViewMode;
import org.jtheque.primary.view.able.fb.IPersonFormBean;

import javax.annotation.Resource;

/**
 * A actor controller state for the state "modify".
 *
 * @author Baptiste Wicht
 */
public final class ModifyActorState extends AbstractControllerState {
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
        controller.getView().setEnabled(true);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.EDIT);

        getViewModel().getCurrentActor().saveToMemento();
    }

    @Override
    public ControllerState save(FormBean bean) {
        IPersonFormBean infos = (IPersonFormBean) bean;

        String oldName = getViewModel().getCurrentActor().getDisplayableText();

        actorService.edit(getViewModel().getCurrentActor(), infos);

        if (!oldName.equals(getViewModel().getCurrentActor().getDisplayableText())) {
            controller.getView().resort();
            controller.getView().select(getViewModel().getCurrentActor());
        }

        return controller.getViewState();
    }

    @Override
    public ControllerState cancel() {
        getViewModel().getCurrentActor().restoreMemento();

        return controller.getViewState();
    }

    @Override
    public ControllerState view(Data data) {
        Person actor = (Person) data;

        assert actor.getType().equals(IActorService.PERSON_TYPE) : "The person must of type Actor";

        if (Managers.getManager(IViewManager.class).askUserForConfirmation(
                "actor.dialogs.confirmSave", "actor.dialogs.confirmSave.title")) {
            controller.save();
        } else {
            getViewModel().getCurrentActor().restoreMemento();
        }

        getViewModel().setCurrentActor(actor);

        return controller.getViewState();
    }
}