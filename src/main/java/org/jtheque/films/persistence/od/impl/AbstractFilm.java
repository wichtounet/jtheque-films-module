package org.jtheque.films.persistence.od.impl;

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

import org.jtheque.core.managers.collection.Collection;
import org.jtheque.core.utils.db.Note;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.od.impl.abstraction.AbstractData;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the abstraction of a film.
 *
 * @author Baptiste Wicht
 */
public abstract class AbstractFilm extends AbstractData implements Film {
    private Collection collection;
    private String title;
    private SimpleData type;
    private Person realizer;
    private SimpleData language;
    private int year;
    private int duration;
    private Note note;
    private Lending lending;
    private String resume;
    private String comment;
    private String image;
    private String filePath;
    private final Set<Person> actors = new HashSet<Person>(10);
    private final Set<SimpleData> kinds = new HashSet<SimpleData>(5);
    private SimpleData theSaga;

    @Override
    public final void setTitle(String title) {
        this.title = title;
    }

    @Override
    public final String getTitle() {
        return title;
    }

    @Override
    public final void setTheCollection(Collection collection) {
        this.collection = collection;
    }

    @Override
    public final Collection getTheCollection() {
        return collection;
    }

    @Override
    public final void setTheType(SimpleData type) {
        this.type = type;
    }

    @Override
    public final SimpleData getTheType() {
        return type;
    }

    @Override
    public final void setYear(int year) {
        this.year = year;
    }

    @Override
    public final int getYear() {
        return year;
    }

    @Override
    public final void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public final int getDuration() {
        return duration;
    }

    @Override
    public final void setTheRealizer(Person realizer) {
        assert realizer.getType().equals(IActorService.PERSON_TYPE) : "The person must of type Actor";

        this.realizer = realizer;
    }

    @Override
    public final Person getTheRealizer() {
        return realizer;
    }

    @Override
    public final void setTheLanguage(SimpleData language) {
        this.language = language;
    }

    @Override
    public final SimpleData getTheLanguage() {
        return language;
    }

    @Override
    public final void setNote(Note note) {
        this.note = note;
    }

    @Override
    public final Note getNote() {
        return note;
    }

    @Override
    public final void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public final String getComment() {
        return comment;
    }

    @Override
    public final void setResume(String resume) {
        this.resume = resume;
    }

    @Override
    public final String getResume() {
        return resume;
    }

    @Override
    public final void setImage(String imagePath) {
        image = imagePath;
    }

    @Override
    public final String getImage() {
        return image;
    }

    @Override
    public final String getFilePath() {
        return filePath;
    }

    @Override
    public final void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public final Set<Person> getActors() {
        return actors;
    }

    @Override
    public final Set<SimpleData> getKinds() {
        return kinds;
    }

    @Override
    public final void setTheLending(Lending lending) {
        this.lending = lending;
    }

    @Override
    public final Lending getTheLending() {
        return lending;
    }

    @Override
    public final SimpleData getTheSaga() {
        return theSaga;
    }

    @Override
    public final void setTheSaga(SimpleData theSaga) {
        this.theSaga = theSaga;
    }

    @Override
    public abstract FilmTemporaryContext getTemporaryContext();
}
