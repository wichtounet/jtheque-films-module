package org.jtheque.films.services.able;

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

import org.jtheque.films.services.impl.utils.Filmography;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.able.IPersonService;
import org.jtheque.primary.view.able.fb.IPersonFormBean;

/**
 * A service for the actor functions.
 *
 * @author Baptiste Wicht
 */
public interface IActorService extends IPersonService {
    String DATA_TYPE = "Actors";
    String PERSON_TYPE = "Actor";

    /**
     * Return the filmography of the actor.
     *
     * @param actor The actor.
     *
     * @return The filmography of the actor.
     */
    Filmography getFilmography(Person actor);

    /**
     * Edit the actor.
     *
     * @param actor The actor to edit.
     * @param infos The informations of the view.
     */
    void edit(Person actor, IPersonFormBean infos);
}