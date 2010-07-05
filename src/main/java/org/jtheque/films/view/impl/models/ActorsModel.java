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
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.view.impl.models.able.IActorsModel;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.PrincipalDataModel;

import java.util.Collection;

/**
 * A model for the actors view.
 *
 * @author Baptiste Wicht
 */
public final class ActorsModel extends PrincipalDataModel<Person> implements IActorsModel {
    private Collection<Person> displayList;

    private Person currentActor;

    private final IActorService actorService;

    public ActorsModel() {
        super();

        actorService = Managers.getManager(IBeansManager.class).getBean("actorService");

        actorService.addDataListener(this);
    }

    @Override
    public Collection<Person> getDatas() {
        return actorService.getDatas();
    }

    @Override
    public Person getCurrentActor() {
        return currentActor;
    }

    @Override
    public void setCurrentActor(Person actor) {
        assert actor.getType().equals(IActorService.PERSON_TYPE) : "The person must of type Actor";

        currentActor = actor;

        fireCurrentObjectChanged(new ObjectChangedEvent(this, currentActor));
    }
}
