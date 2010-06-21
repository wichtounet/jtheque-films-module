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