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
import org.jtheque.primary.od.able.Country;

/**
 * An actor form bean. This form bean contains all the informations of an actor. It is used by the interface
 * to pass data to the controller.
 *
 * @author Baptiste Wicht
 */
public final class PersonFormBean implements IPersonFormBean {
    private String name;
    private String firstName;
    private Country country;
    private Note note;

    @Override
    public void setName(String nom) {
        name = nom;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public Note getNote() {
        return note;
    }

    @Override
    public Country getCountry() {
        return country;
    }
}