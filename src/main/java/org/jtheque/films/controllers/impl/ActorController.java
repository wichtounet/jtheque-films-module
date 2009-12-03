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

import org.jtheque.films.controllers.able.IActorController;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.view.able.IActorView;
import org.jtheque.films.view.impl.models.able.IActorsModel;
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
 * An actor controller implementation.
 *
 * @author Baptiste Wicht
 */
public final class ActorController extends PrincipalController<Person> implements IActorController {
    @Resource
    private IActorView actorView;

    @Override
    public IActorView getView() {
        return actorView;
    }

    /**
     * Init the controller.
     */
    @PostConstruct
    public void init() {
        setState(getViewState());
    }

    @Override
    public void valueChanged(TreeSelectionEvent event) {
        TreePath current = ((JTree) event.getSource()).getSelectionPath();

        if (current != null && current.getLastPathComponent() instanceof TreeElement) {
            Data actor = (Data) current.getLastPathComponent();

            if (actor != null) {
                ControllerState newState = getState().view(actor);

                if (newState != null) {
                    setAndApplyState(newState);
                }
            }
        }
    }

    @Override
    public void save() {
        ControllerState newState = getState().save(actorView.fillActorFormBean());

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
    public void createActor() {
        ControllerState newState = getState().create();

        if (newState != null) {
            setAndApplyState(newState);
        }
    }

    @Override
    public void deleteCurrentActor() {
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
    public IActorsModel getViewModel() {
        return actorView.getModel();
    }

    @Override
    public String getDataType() {
        return IActorService.DATA_TYPE;
    }

    @Override
    public Collection<Person> getDisplayList() {
        return getViewModel().getDisplayList();
    }
}