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
