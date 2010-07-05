package org.jtheque.films.services.able;

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
