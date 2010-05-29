package org.jtheque.films.view.impl.models;

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
	public Collection<Person> getDatas(){
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