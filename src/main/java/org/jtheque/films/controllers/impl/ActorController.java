package org.jtheque.films.controllers.impl;

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
