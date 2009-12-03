package org.jtheque.films.view.impl.fb;

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

import org.jtheque.core.utils.db.Note;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Country;

/**
 * @author Baptiste Wicht
 */
public interface IPersonFormBean extends FormBean {
    /**
     * Set the name of the person.
     *
     * @param name The name of the person
     */
    void setName(String name);

    /**
     * Set the first name of the person.
     *
     * @param firstName The first name of the person
     */
    void setFirstName(String firstName);

    /**
     * Set the note of the person.
     *
     * @param note The note to set.
     */
    void setNote(Note note);

    /**
     * Set the country of the person.
     *
     * @param country The country to set.
     */
    void setCountry(Country country);

    /**
     * Return the name of the person.
     *
     * @return The name of the person.
     */
    String getName();

    /**
     * Return the first name of the person.
     *
     * @return The first name of the person.
     */
    String getFirstName();

    /**
     * Return the note of the person.
     *
     * @return the note.
     */
    Note getNote();

    /**
     * Return the country of the person.
     *
     * @return the country.
     */
    Country getCountry();
}
