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
	public Collection<Person> getDatas(){
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