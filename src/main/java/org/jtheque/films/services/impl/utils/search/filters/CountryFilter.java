package org.jtheque.films.services.impl.utils.search.filters;

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

import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.utils.collections.Filter;

/**
 * A filter for countries.
 *
 * @author Baptiste Wicht
 * @param <T> The type of object to filters.
 */
public final class CountryFilter<T extends Person> implements Filter<T> {
    private final SimpleData country;

    /**
     * Construct a new <code>CountryFilter</code>.
     *
     * @param country The country to filter for.
     */
    public CountryFilter(SimpleData country) {
        super();

        this.country = country;
    }

    @Override
    public boolean accept(T person) {
        return person.getTheCountry().equals(country);
    }
}
