package org.jtheque.films.controllers.impl.state.realizer;

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