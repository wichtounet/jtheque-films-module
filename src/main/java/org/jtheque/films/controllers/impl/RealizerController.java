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

import org.jtheque.films.controllers.able.IRealizerController;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.view.able.IRealizerView;
import org.jtheque.films.view.impl.models.able.IRealizersModel;
import org.jtheque.primary.controller.able.ControllerState;
import org.jtheque.primary.controller.impl.PrincipalController;
import org.jtheque.primary.od.able.Person;

import javax.annotation.Resource;

/**
 * A realizer controller implementation.
 *
 * @author Baptiste Wicht
 */
public final class RealizerController extends PrincipalController<Person> implements IRealizerController {
    @Resource
    private IRealizerView realizerView;

    public RealizerController(ControllerState viewState, ControllerState modifyState,
                              ControllerState newObjectState, ControllerState autoAddState) {
        super(viewState, modifyState, newObjectState, autoAddState);
    }

    @Override
    public void save() {
        save(realizerView.fillPersonFormBean());
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
}
