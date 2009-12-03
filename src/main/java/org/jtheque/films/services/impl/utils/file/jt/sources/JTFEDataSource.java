package org.jtheque.films.services.impl.utils.file.jt.sources;

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

import org.jtheque.core.managers.file.able.BasicDataSource;
import org.jtheque.films.persistence.od.able.Film;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A data source for JTFE file.
 *
 * @author Baptiste Wicht
 */
public final class JTFEDataSource extends BasicDataSource {
    private Collection<Film> films;

    /**
     * Return the films in the datasource.
     *
     * @return A List containing all the films on the datasource.
     */
    public Collection<Film> getFilms() {
        return films;
    }

    /**
     * Set the films on the datasource.
     *
     * @param films The films of the datasource.
     */
    public void setFilms(Collection<Film> films) {
        this.films = new ArrayList<Film>(films);
    }
}