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
import org.jtheque.primary.od.able.Person;

import javax.annotation.Resource;

/**
 * An actor controller implementation.
 *
 * @author Baptiste Wicht
 */
public final class ActorController extends PrincipalController<Person> implements IActorController {
    @Resource
    private IActorView actorView;

    public ActorController(ControllerState viewState, ControllerState modifyState,
                           ControllerState newObjectState, ControllerState autoAddState) {
        super(viewState, modifyState, newObjectState, autoAddState);
    }

    @Override
    public IActorView getView() {
        return actorView;
    }

    @Override
    public void save() {
        ControllerState newState = getState().save(actorView.fillActorFormBean());

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
}