package org.jtheque.films.persistence.od.able;

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

import org.jtheque.core.managers.collection.Collection;
import org.jtheque.films.persistence.od.impl.FilmTemporaryContext;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.od.able.Notable;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;

import java.util.Set;

/**
 * A Film specification.
 *
 * @author Baptiste Wicht
 */
public interface Film extends Data, Notable {
    /**
     * Set the title of the film.
     *
     * @param title The title of the film.
     */
    void setTitle(String title);

    /**
     * Return the title of the film.
     *
     * @return The title of the film.
     */
    String getTitle();

    /**
     * The collection of this film.
     *
     * @param collection The collection.
     */
    void setTheCollection(Collection collection);

    /**
     * Return the collection of the film.
     *
     * @return The collection of the film.
     */
    Collection getTheCollection();

    /**
     * Set the type of the film.
     *
     * @param type The type of the film.
     */
    void setTheType(SimpleData type);

    /**
     * Return the type of the film.
     *
     * @return The type of the film.
     */
    SimpleData getTheType();

    /**
     * Set the year of the film.
     *
     * @param year The year of the film.
     */
    void setYear(int year);

    /**
     * Return the year of the film.
     *
     * @return The year of the film.
     */
    int getYear();

    /**
     * Set the duration of the film.
     *
     * @param duration The duration of the film.
     */
    void setDuration(int duration);

    /**
     * Return the duration of the film.
     *
     * @return The duration of the film.
     */
    int getDuration();

    /**
     * Set the realizer of the film.
     *
     * @param realizer The realizer of the film.
     */
    void setTheRealizer(Person realizer);

    /**
     * Return the realizer of the film.
     *
     * @return The realizer of the film.
     */
    Person getTheRealizer();

    /**
     * Set the language of the film.
     *
     * @param language The language of the film.
     */
    void setTheLanguage(SimpleData language);

    /**
     * Return the language of the film.
     *
     * @return The language of the film.
     */
    SimpleData getTheLanguage();

    /**
     * Set the comment of the film.
     *
     * @param comment The comment of the film.
     */
    void setComment(String comment);

    /**
     * Return the comment of the film.
     *
     * @return The comment of the film.
     */
    String getComment();

    /**
     * Set the resume of the film.
     *
     * @param resume The resume of the film.
     */
    void setResume(String resume);

    /**
     * Return the resume of the film.
     *
     * @return The resume of the film.
     */
    String getResume();

    /**
     * Set the image of the film.
     *
     * @param imagePath The path to the image of the film.
     */
    void setImage(String imagePath);

    /**
     * Return the duration of the film.
     *
     * @return The duration of the film.
     */
    String getImage();

    /**
     * Return the path to the file.
     *
     * @return The path to the file.
     */
    String getFilePath();

    /**
     * Set file year of the film.
     *
     * @param filePath The path to the file of the film.
     */
    void setFilePath(String filePath);

    /**
     * Return all the actors of the film.
     *
     * @return A Set containing all the actors of the film.
     */
    Set<Person> getActors();

    /**
     * Return all the kinds of the film.
     *
     * @return A Set containing all the kinds of the film.
     */
    Set<SimpleData> getKinds();

    /**
     * Set the lending of the film.
     *
     * @param lending The lending of the film.
     */
    void setTheLending(Lending lending);

    /**
     * Return the lending of the film.
     *
     * @return The lending or <code>null</code> if this film is not lend.
     */
    Lending getTheLending();

    /**
     * Return the saga of the film.
     *
     * @return The saga of the film.
     */
    SimpleData getTheSaga();

    /**
     * Set the saga of the film.
     *
     * @param theSaga The saga of the film.
     */
    void setTheSaga(SimpleData theSaga);

    /**
     * Indicate if the film has kinds.
     *
     * @return <code>true</code> if the film has kinds else <code>false</code>.
     */
    boolean hasKinds();

    /**
     * Indicate if the film has a type.
     *
     * @return <code>true</code> if the film has a type else <code>false</code>.
     */
    boolean hasType();

    /**
     * Indicate if the film has a realizer.
     *
     * @return <code>true</code> if the film has a realizer else <code>false</code>.
     */
    boolean hasRealizer();

    /**
     * Indicate if the film has a language.
     *
     * @return <code>true</code> if the film has a language else <code>false</code>.
     */
    boolean hasLanguage();

    /**
     * Indicate if the film has actors.
     *
     * @return <code>true</code> if the film has actors else <code>false</code>.
     */
    boolean hasActors();

    /**
     * Return the temporary context of the film.
     *
     * @return The temporary context of the film.
     */
    @Override
    FilmTemporaryContext getTemporaryContext();

    /**
     * Add a set of actors to the film.
     *
     * @param actors The actors to add to the film.
     */
    void addActors(Set<Person> actors);

    /**
     * Add a set of kinds to the film.
     *
     * @param kinds The kinds to add to the film.
     */
    void addKinds(Set<SimpleData> kinds);

    /**
     * Add an actor to the film.
     *
     * @param actor The actor to add.
     */
    void addActor(Person actor);

    /**
     * Add a kind to the film.
     *
     * @param kind The kind to add.
     */
    void addKind(SimpleData kind);

    /**
     * Indicate if the film contains the specified actor or not.
     *
     * @param actor The actor to to test for contains.
     *
     * @return <code>true</code> if the film contains the actor else <code>false</code>.
     */
    boolean containsActor(Person actor);

    /**
     * Indicate if the film contains the specified kind or not.
     *
     * @param kind The kind to to test for contains.
     *
     * @return <code>true</code> if the film contains the kind else <code>false</code>.
     */
    boolean containsKind(SimpleData kind);
}
