package org.jtheque.films.services.impl.utils.file.exports.datasources;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
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