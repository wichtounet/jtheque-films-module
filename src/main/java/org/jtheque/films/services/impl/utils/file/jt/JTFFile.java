package org.jtheque.films.services.impl.utils.file.jt;

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
