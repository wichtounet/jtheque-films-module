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
import org.jtheque.primary.od.able.Person;

import java.util.Collection;

/**
 * A service for the realizers functions.
 *
 * @author Baptiste Wicht
 */
public interface IRealizersService extends DataContainer<Person> {
    String DATA_TYPE = "Realizers";
    String PERSON_TYPE = "Realizer";

    /**
     * Return an empty realizer.
     *
     * @return An empty realizer.
     */
    Person getEmptyRealizer();

    /**
     * Return the default realizer.
     *
     * @return The default realizer.
     */
    Person getDefaultRealizer();

    /**
     * Return all the realizers.
     *
     * @return All the realizers.
     */
    Collection<Person> getRealizers();

    /**
     * Delete the realizer.
     *
     * @param realizer The realizer to delete.
     *
     * @return true if the realizer has been deleted else false.
     */
    boolean delete(Person realizer);

    /**
     * Indicate if a realizer exists with the first name and name.
     *
     * @param firstName The first name.
     * @param name      The name.
     *
     * @return true if the realizer exists else false.
     */
    boolean exists(String firstName, String name);

    /**
     * Return the realizer with this name and first name.
     *
     * @param firstName The first name.
     * @param name      The name.
     *
     * @return The realizer.
     */
    Person getRealizer(String firstName, String name);

    /**
     * Create the realizer.
     *
     * @param realizer The realizer to create.
     */
    void create(Person realizer);

    /**
     * Indicate if the realizer exists or not.
     *
     * @param realizer The realizer to test.
     *
     * @return true if the realizer exist else false.
     */
    boolean exists(Person realizer);

    /**
     * Indicate if there is no realizers.
     *
     * @return true if there is no realizers else false.
     */
    boolean hasNoRealizers();

    /**
     * Save the realizer.
     *
     * @param realizer The realizer to save.
     */
    void save(Person realizer);
}