package org.jtheque.films.view.impl.fb;

import org.jtheque.core.utils.db.Note;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.primary.controller.able.FormBean;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;

import java.util.Set;

/**
 * @author Baptiste Wicht
 */
public interface IFilmFormBean extends FormBean {
    /**
     * Set the title of the film.
     *
     * @param title The title to set.
     */
    void setTitle(String title);

    /**
     * Set the type
     *
     * @param type The new type.
     */
    void setType(SimpleData type);

    /**
     * Set the year of the film.
     *
     * @param year The year of the film.
     */
    void setYear(int year);

    /**
     * Set the duration of the film.
     *
     * @param duration The duration of the film.
     */
    void setDuration(int duration);

    /**
     * Set the realizer of the film.
     *
     * @param realizer The realizer of the film.
     */
    void setRealizer(Person realizer);

    /**
     * Set the language of the film.
     *
     * @param language The language of the film.
     */
    void setLanguage(SimpleData language);

    /**
     * Set the note of the realizer.
     *
     * @param note The note to set.
     */
    void setNote(Note note);

    /**
     * Set the comment of the film.
     *
     * @param comment The comment of the film.
     */
    void setComment(String comment);

    /**
     * Set the resume of the film.
     *
     * @param resume The resume of the film.
     */
    void setResume(String resume);

    /**
     * Set the actors of the film.
     *
     * @param actors The actors of the film.
     */
    void setActors(Set<Person> actors);

    /**
     * Set the saga of the film.
     *
     * @param saga The saga of the film.
     */
    void setSaga(SimpleData saga);

    /**
     * Set the file path.
     *
     * @param filePath The path to the file.
     */
    void setFilePath(String filePath);

    /**
     * Set the kinds of the film.
     *
     * @param kinds The kinds of the film.
     */
    void setKinds(Set<SimpleData> kinds);

    /**
     * Fill a film with the info of the form bean.
     *
     * @param film The film to fill.
     */
    void fillFilm(Film film);
}
