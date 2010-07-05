package org.jtheque.films.services.impl.utils;

import org.jtheque.films.persistence.od.able.Film;

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

/**
 * A video file.
 *
 * @author Baptiste Wicht
 */
public final class VideoFile {
    private final String file;
    private final Film film;

    /**
     * Create a new Video File.
     *
     * @param file The file path.
     * @param film The film.
     */
    public VideoFile(String file, Film film) {
        super();

        this.file = file;
        this.film = film;
    }

    /**
     * Return the path to the file.
     *
     * @return The path to the file.
     */
    public String getFile() {
        return file;
    }

    /**
     * Return the film.
     *
     * @return The film.
     */
    public Film getFilm() {
        return film;
    }
}
