package org.jtheque.films.controllers.impl;

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

import org.jtheque.films.controllers.able.IRealizerController;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.view.able.IRealizerView;
import org.jtheque.films.view.impl.models.able.IRealizersModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.impl.PrincipalController;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.impl.models.tree.TreeElement;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreePath;
import java.util.Collection;

/**
 * A realizer controller implementation.
 *
 * @author Baptiste Wicht
 */
public final class RealizerController extends PrincipalController<Person> implements IRealizerController {
    @Resource
    private IRealizerView realizerView;

    /**
     * Init the view.
     */
    @PostConstruct
    public void init() {
        setState(getViewState());
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath current = ((JTree) e.getSource()).getSelectionPath();

        if (current != null && current.getLastPathComponent() instanceof TreeElement) {
            Data realizer = (Data) current.getLastPathComponent();

            if (realizer != null) {
                ControllerState newState = getState().view(realizer);

                if (newState != null) {
                    setAndApplyState(newState);
                }
            }
        }
    }

    @Override
    public void save() {
        ControllerState newState = getState().save(realizerView.fillPersonFormBean());

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void manualEdit() {
        ControllerState newState = getState().manualEdit();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void createRealizer() {
        ControllerState newState = getState().create();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void deleteCurrentRealizer() {
        ControllerState newState = getState().delete();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void cancel() {
        ControllerState newState = getState().cancel();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public IRealizersModel getViewModel() {
        return (IRealizersModel) realizerView.getModel();
    }

    @Override
    public IRealizerView getView() {
        return realizerView;
    }

    @Override
    public String getDataType() {
        return IRealizersService.DATA_TYPE;
    }

    @Override
    public Collection<Person> getDisplayList() {
        return getViewModel().getDisplayList();
    }
}