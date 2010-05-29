package org.jtheque.films.services.impl.utils.file.jt;

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

import org.jtheque.core.utils.file.jt.AbstractJTFileHeader;
import org.jtheque.core.utils.file.jt.able.JTNotZippedFile;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.file.jt.header.JTFFileHeader;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A JTF File. It's a file to store all the informations about a file.
 *
 * @author Baptiste Wicht
 */
public final class JTFFile implements JTNotZippedFile {
    private Film film;
    private Set<Person> actors;
    private Set<SimpleData> kinds;
    private Person realizer;
    private SimpleData language;
    private SimpleData type;
    private Collection<SimpleData> countries;

    private boolean correctSeparators = true;

    private final AbstractJTFileHeader header = new JTFFileHeader();

    @Override
    public AbstractJTFileHeader getHeader() {
        return header;
    }

    @Override
    public boolean isValid() {
        return film != null && header.isComplete() && correctSeparators;
    }

    @Override
    public boolean isCorrectSeparators() {
        return correctSeparators;
    }

    @Override
    public void setCorrectSeparators(boolean correctSeparators) {
        this.correctSeparators = correctSeparators;
    }

    /**
     * Return the actors of the file.
     *
     * @return The actors of the file.
     */
    public Iterable<Person> getActors() {
        return actors;
    }

    /**
     * Set the actors of the file.
     *
     * @param actors The actors of the file.
     */
    public void setActors(Set<Person> actors) {
        this.actors = new HashSet<Person>(actors);
    }

    /**
     * Return the kinds of the file.
     *
     * @return A list containing all the kinds of the file.
     */
    public Iterable<SimpleData> getKinds() {
        return kinds;
    }

    /**
     * Set the kinds of the file.
     *
     * @param kinds The kinds of the file.
     */
    public void setKinds(Set<SimpleData> kinds) {
        this.kinds = new HashSet<SimpleData>(kinds);
    }

    /**
     * Return the film.
     *
     * @return The film of the file.
     */
    public Film getFilm() {
        return film;
    }

    /**
     * Set the film.
     *
     * @param film The film.
     */
    public void setFilm(Film film) {
        this.film = film;
    }

    /**
     * Return the language.
     *
     * @return The language.
     */
    public SimpleData getLanguage() {
        return language;
    }

    /**
     * Set the language.
     *
     * @param language The language.
     */
    public void setLanguage(SimpleData language) {
        this.language = language;
    }

    /**
     * Return the countries.
     *
     * @return The countries.
     */
    public Iterable<SimpleData> getCountries() {
        return countries;
    }

    /**
     * Set the countries.
     *
     * @param countries The countries.
     */
    public void setCountries(Collection<SimpleData> countries) {
        this.countries = new ArrayList<SimpleData>(countries);
    }

    /**
     * Return the realizer.
     *
     * @return The realizer.
     */
    public Person getRealizer() {
        return realizer;
    }

    /**
     * Set the realizer.
     *
     * @param realizer The realizer.
     */
    public void setRealizer(Person realizer) {
        this.realizer = realizer;
    }

    /**
     * Return the type.
     *
     * @return The type.
     */
    public SimpleData getType() {
        return type;
    }

    /**
     * Set the type.
     *
     * @param type The type.
     */
    public void setType(SimpleData type) {
        this.type = type;
    }
}