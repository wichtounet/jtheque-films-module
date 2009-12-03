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
import org.jtheque.core.managers.undo.IUndoRedoManager;
import org.jtheque.films.controllers.able.IRealizerController;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.view.impl.models.able.IRealizersModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.controller.impl.undo.DeletedPersonEdit;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.able.ViewMode;

import javax.annotation.Resource;

/**
 * A realizer controller state for the state "view".
 *
 * @author Baptiste Wicht
 */
public final class ViewRealizerState implements ControllerState {
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
        controller.getView().setEnabled(false);
        controller.getView().getToolbarView().setDisplayMode(ViewMode.VIEW);

        if (getViewModel().getCurrentRealizer() != null) {
            controller.getView().select(getViewModel().getCurrentRealizer());
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
        boolean deleted = realizersService.delete(getViewModel().getCurrentRealizer());

        if (deleted) {
            Managers.getManager(IUndoRedoManager.class).addEdit(new DeletedPersonEdit(getViewModel().getCurrentRealizer()));

            controller.getView().selectFirst();
        }

        return null;
    }

    @Override
    public ControllerState autoEdit(Data data) {
        Person realizer = (Person) data;

        assert realizer.getType().equals(IRealizersService.PERSON_TYPE) : "The person must of type Actor";

        getViewModel().setCurrentRealizer(realizer);

        return controller.getAutoAddState();
    }

    @Override
    public ControllerState view(Data data) {
        Person realizer = (Person) data;

        assert realizer.getType().equals(IRealizersService.PERSON_TYPE) : "The person must of type Actor";

        getViewModel().setCurrentRealizer(realizer);

        return null;
    }
}