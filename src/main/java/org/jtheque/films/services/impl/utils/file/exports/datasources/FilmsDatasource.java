package org.jtheque.films.services.impl.utils.file.exports.datasources;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.properties.IPropertiesManager;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

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
 * A films jasper data source.
 *
 * @author Baptiste Wicht
 */
public final class FilmsDatasource implements JRDataSource {
    private final List<Film> films;

    private int index = -1;
    private static final int SECONDS_IN_A_MINUTE = 60;

    private static final Collection<String> REFLECTION_FIELD = Arrays.asList("title", "year", "note", "resume", "image");
    private static final Collection<String> REFLECTION_FOREIGN_FIELD = Arrays.asList("type", "realizer", "language");

    /**
     * Construct a new FilmsDatasource for a list of films.
     *
     * @param films The films to provide in the data source.
     */
    public FilmsDatasource(Collection<Film> films) {
        super();

        this.films = new ArrayList<Film>(films);
    }

    /**
     * Construct a new FilmsDatasource for a film.
     *
     * @param film The film to provide in the data source.
     */
    public FilmsDatasource(Film film) {
        super();

        films = new ArrayList<Film>(1);
        films.add(film);
    }

    @Override
    public boolean next() throws JRException {
        index++;

        return index < films.size();
    }

    @Override
    public Object getFieldValue(JRField field) throws JRException {
        Film film = films.get(index);

        String fieldName = field.getName();

        Object value = null;

        if ("kinds".equals(fieldName)) {
            value = getKindsDescription(film);
        } else if ("duration".equals(fieldName)) {
            int hours = film.getDuration() / SECONDS_IN_A_MINUTE;
            int minutes = film.getDuration() % SECONDS_IN_A_MINUTE;

            value = hours + "h" + minutes;
        } else if ("actors".equals(fieldName)) {
            value = getActorsDescription(film);
        } else if (REFLECTION_FIELD.contains(fieldName)) {
            Object property = Managers.getManager(IPropertiesManager.class).getPropertyQuickly(film, fieldName);

            value = property == null ? "" : property.toString();
        } else if (REFLECTION_FOREIGN_FIELD.contains(fieldName)) {
            Object property = Managers.getManager(IPropertiesManager.class).getPropertyQuickly(film, "the" + StringUtils.setFirstLetterOnlyUpper(fieldName));

            value = property == null ? "" : property.toString();
        }

        return value;
    }

    /**
     * Return the actors description of the film.
     *
     * @param film The film to get the descriptions from.
     *
     * @return The String description of the actors of the film.
     */
    private static Object getActorsDescription(Film film) {
        StringBuilder builder = new StringBuilder(200);

        boolean firstActor = true;
        for (Person actor : film.getActors()) {
            if (firstActor) {
                builder.append(actor.getDisplayableText());
                firstActor = false;
            } else {
                builder.append(", ").append(actor.getDisplayableText());
            }
        }

        return builder.toString();
    }

    /**
     * Return the kinds description of the film.
     *
     * @param film The film to get the descriptions from.
     *
     * @return The String description of the kinds of the film.
     */
    private static Object getKindsDescription(Film film) {
        StringBuilder builder = new StringBuilder(50);

        boolean firstKind = true;
        for (SimpleData kind : film.getKinds()) {
            if (firstKind) {
                builder.append(kind.getDisplayableText());
                firstKind = false;
            } else {
                builder.append(", ").append(kind.getDisplayableText());
            }
        }

        return builder.toString();
    }
}
