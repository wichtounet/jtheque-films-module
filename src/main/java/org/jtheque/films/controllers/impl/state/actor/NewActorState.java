package org.jtheque.films.controllers.impl.state.actor;

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
import org.jtheque.films.controllers.able.IActorController;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.view.impl.models.able.IActorsModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.controller.impl.AbstractControllerState;
import org.jtheque.primary.controller.impl.undo.GenericDataCreatedEdit;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.able.ViewMode;
import org.jtheque.primary.view.able.fb.IPersonFormBean;

import javax.annotation.Resource;

/**
 * A actor controller state for the state "new".
 *
 * @author Baptiste Wicht
 */
public final class NewActorState extends AbstractControllerState {
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
        getViewModel().setCurrentActor(actorService.getDefaultPerson());
        controller.getView().setEnabled(true);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.NEW);
    }

    @Override
    public ControllerState save(FormBean bean) {
        IPersonFormBean infos = (IPersonFormBean) bean;

        Person actor = actorService.getEmptyPerson();

        infos.fillPerson(actor);

        actorService.create(actor);

        Managers.getManager(IUndoRedoManager.class).addEdit(
                new GenericDataCreatedEdit<Person>("actorService", actor));

        controller.getView().resort();

        return controller.getViewState();
    }

    @Override
    public ControllerState cancel() {
        ControllerState nextState = null;

        controller.getView().selectFirst();

        if (actorService.hasNoPerson()) {
            nextState = controller.getViewState();
        }

        return nextState;
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
