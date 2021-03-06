package org.jtheque.films.controllers.impl.state.realizer;

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
import org.jtheque.films.controllers.able.IRealizerController;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.view.impl.models.able.IRealizersModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.controller.impl.AbstractControllerState;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.able.ViewMode;
import org.jtheque.primary.view.able.fb.IPersonFormBean;

import javax.annotation.Resource;

/**
 * A state of realizer view correspond with an auto add.
 *
 * @author Baptiste Wicht
 */
public final class AutoAddRealizerState extends AbstractControllerState {
    @Resource
    private IRealizerController controller;

    @Resource
    private IRealizersService realizersService;

    /**
     * Return the model of the view.
     *
     * @return The model of the view.
     */
    private IRealizersModel getViewModel() {
        return controller.getViewModel();
    }

    @Override
    public void apply() {
        controller.getView().setEnabled(true);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.AUTO);

        getViewModel().getCurrentRealizer().saveToMemento();
    }

    @Override
    public ControllerState save(FormBean bean) {
        IPersonFormBean infos = (IPersonFormBean) bean;

        Person realizer = getViewModel().getCurrentRealizer().isSaved() ? getViewModel().getCurrentRealizer() : realizersService.getEmptyRealizer();

        infos.fillPerson(realizer);

        if (getViewModel().getCurrentRealizer().isSaved()) {
            realizersService.save(realizer);
        } else {
            realizersService.create(realizer);
        }

        controller.getView().resort();
        controller.getView().select(realizer);

        return controller.getViewState();
    }

    @Override
    public ControllerState cancel() {
        if (getViewModel().getCurrentRealizer().isSaved()) {
            getViewModel().getCurrentRealizer().restoreMemento();
        }

        return controller.getViewState();
    }

    @Override
    public ControllerState view(Data data) {
        Person realizer = (Person) data;

        assert realizer.getType().equals(IRealizersService.PERSON_TYPE) : "The person must of type Actor";

        if (Managers.getManager(IViewManager.class).askUserForConfirmation(
                "actor.dialogs.confirmSave", "actor.dialogs.confirmSave.title")) {
            controller.save();
        } else {
            getViewModel().getCurrentRealizer().restoreMemento();
        }

        getViewModel().setCurrentRealizer(realizer);

        return controller.getViewState();
    }
}
