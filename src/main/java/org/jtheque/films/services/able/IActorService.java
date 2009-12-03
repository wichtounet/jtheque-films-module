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

import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.films.services.impl.utils.Filmography;
import org.jtheque.films.view.impl.fb.IPersonFormBean;
import org.jtheque.primary.od.able.Person;

import java.util.Collection;

/**
 * A service for the actor functions.
 *
 * @author Baptiste Wicht
 */
public interface IActorService extends DataContainer<Person> {
    String DATA_TYPE = "Actors";
    String PERSON_TYPE = "Actor";

    /**
     * Return an empty actor.
     *
     * @return An empty actor.
     */
    Person getDefaultActor();

    /**
     * Return the filmography of the actor.
     *
     * @param actor The actor.
     * @return The filmography of the actor.
     */
    Filmography getFilmography(Person actor);

    /**
     * Create the actor.
     *
     * @param actor The actor to create.
     */
    void create(Person actor);

    /**
     * Save the actor.
     *
     * @param actor The actor to save.
     */
    void save(Person actor);

    /**
     * Edit the actor.
     *
     * @param actor The actor to edit.
     * @param infos The informations of the view.
     */
    void edit(Person actor, IPersonFormBean infos);

    /**
     * Indicate if the service has nos actors.
     *
     * @return true if there is no actors else false.
     */
    boolean hasNoActor();

    /**
     * Delete the actor.
     *
     * @param actor The actor to delete.
     * @return true if the actor has been deleted else false.
     */
    boolean delete(Person actor);

    /**
     * Return all the actors.
     *
     * @return A List containing all the actors.
     */
    Collection<Person> getActors();

    /**
     * Indicate if an actor exists or not.
     *
     * @param actor The actor to test.
     * @return true if the actor exists else false.
     */
    boolean exist(Person actor);

    /**
     * Return the actor denoted by this first name and name.
     *
     * @param firstName The first name of the actor.
     * @param name      The name of the actor.
     * @return The actor.
     */
    Person getActor(String firstName, String name);


    /**
     * Indicate if an actor with this first name and name exist or not.
     *
     * @param firstName The first name of the actor.
     * @param name      The name of the actor.
     * @return true if the actor exists else false.
     */
    boolean exist(String firstName, String name);

    /**
     * Return an empty actor.
     *
     * @return An empty actor.
     */
    Person getEmptyActor();
}