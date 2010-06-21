package org.jtheque.films.view.impl.fb;

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

import org.jtheque.core.utils.db.Note;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;

import java.util.HashSet;
import java.util.Set;

/**
 * A film form bean. This form bean contains all the informations of a film. It is used by the interface to pass data to
 * the controller.
 *
 * @author Baptiste Wicht
 */
public final class FilmFormBean implements IFilmFormBean {
    private String title;
    private SimpleData type;
    private Person realizer;
    private SimpleData language;
    private SimpleData saga;
    private int year;
    private int duration;
    private Note note;
    private String resume;
    private String comment;
    private String filePath;
    private Set<Person> actors = new HashSet<Person>(15);
    private Set<SimpleData> kinds = new HashSet<SimpleData>(5);

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setType(SimpleData type) {
        this.type = type;
    }

    @Override
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void setRealizer(Person realizer) {
        assert realizer.getType().equals(IRealizersService.PERSON_TYPE) : "The person must be of type Realizer";

        this.realizer = realizer;
    }

    @Override
    public void setLanguage(SimpleData language) {
        this.language = language;
    }

    @Override
    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public void setResume(String resume) {
        this.resume = resume;
    }

    @Override
    public void setActors(Set<Person> actors) {
        this.actors = new HashSet<Person>(actors);
    }

    @Override
    public void setSaga(SimpleData saga) {
        this.saga = saga;
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void setKinds(Set<SimpleData> kinds) {
        this.kinds = new HashSet<SimpleData>(kinds);
    }

    @Override
    public void fillFilm(Film film) {
        film.setTitle(title);
        film.addKinds(kinds);
        film.setTheType(type);
        film.setComment(comment);
        film.setNote(note);
        film.addActors(actors);
        film.setTheRealizer(realizer);
        film.setTheLanguage(language);
        film.setYear(year);
        film.setDuration(duration);
        film.setResume(resume);
        film.setFilePath(filePath);
        film.setTheSaga(saga);
    }
}