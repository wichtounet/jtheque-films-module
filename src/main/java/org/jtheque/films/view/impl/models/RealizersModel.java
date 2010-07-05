package org.jtheque.films.view.impl.models;

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
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.view.impl.models.able.IRealizersModel;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.PrincipalDataModel;

import java.util.Collection;

/**
 * A model for the realizers view.
 *
 * @author Baptiste Wicht
 */
public final class RealizersModel extends PrincipalDataModel<Person> implements IRealizersModel {
    private Person currentRealizer;

    public RealizersModel() {
        super();

        Managers.getManager(IBeansManager.class).<IRealizersService>getBean("realizersService").addDataListener(this);
    }

    @Override
    public Collection<Person> getDatas() {
        return Managers.getManager(IBeansManager.class).<IRealizersService>getBean("realizersService").getDatas();
    }

    @Override
    public Person getCurrentRealizer() {
        return currentRealizer;
    }

    @Override
    public void setCurrentRealizer(Person realizer) {
        assert realizer.getType().equals(IRealizersService.PERSON_TYPE) : "The person must of type Actor";

        currentRealizer = realizer;

        fireCurrentObjectChanged(new ObjectChangedEvent(this, currentRealizer));
    }
}
