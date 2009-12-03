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

/**
 * A data source for JTF file.
 *
 * @author Baptiste Wicht
 */
public final class JTFDataSource extends BasicDataSource {
    private Film film;

    /**
     * Construct a new JTFDataSource.
     */
    public JTFDataSource() {
        super();
    }

    /**
     * Construct a new JTFDataSource.
     *
     * @param film The film for the datasource.
     */
    public JTFDataSource(Film film) {
        super();

        this.film = film;
    }

    /**
     * Return the film.
     *
     * @return The film.
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Set the film.
     *
     * @param film The film on the data source.
     */
    public void setFilm(Film film) {
        this.film = film;
    }
}