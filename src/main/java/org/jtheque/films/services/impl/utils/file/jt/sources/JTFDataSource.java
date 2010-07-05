package org.jtheque.films.services.impl.utils.file.jt.sources;

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
