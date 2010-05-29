package org.jtheque.films.persistence.od.impl;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.utils.PropertiesUtils;
import org.jtheque.core.utils.db.EntityUtils;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.utils.Constants;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.utils.bean.HashCodeUtils;

import javax.swing.Icon;
import java.util.Set;

/**
 * A Film implementation.
 *
 * @author Baptiste Wicht
 */
public final class FilmImpl extends AbstractFilm {
    private Film memento;
    private boolean mementoState;

    private final FilmTemporaryContext temporaryContext;

    /**
     * Construct a new FilmImpl.
     */
    public FilmImpl() {
        super();

        temporaryContext = new FilmTemporaryContext();
    }

    @Override
    public FilmTemporaryContext getTemporaryContext() {
        return temporaryContext;
    }

    @Override
    public String getDisplayableText() {
        return getTitle();
    }

    @Override
    public String toString() {
        return getDisplayableText();
    }

    @Override
    public int hashCode() {
        return HashCodeUtils.hashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return PropertiesUtils.areEquals(
                this, obj,
                "title", "actors", "year", "kinds", "comment", "duration", "theLending", "image", "theLanguage",
                "note", "theRealizer", "resume", "theType", "kinds");

    }

    @Override
    public Icon getIcon() {
        return Managers.getManager(IResourceManager.class).getIcon(Constants.IMAGE_BASE_NAME, Constants.FILM_ICON, ImageType.PNG);
    }

    @Override
    public void saveToMemento() {
        mementoState = true;

        memento = PropertiesUtils.createMemento(this);

        if (memento == null) {
            mementoState = false;
        }
    }

    @Override
    public void restoreMemento() {
        if (mementoState) {
            PropertiesUtils.restoreMemento(this, memento);
        }
    }

    /**
     * Indicate if the film is in lending or not.
     *
     * @return <code>true</code> if the film has a lending else <code>false</code>
     */
    @Override
    public boolean hasType() {
        return getTheType() != null;
    }

    /**
     * Indicate if the film has kinds or not.
     *
     * @return <code>true</code> if the film has kinds else <code>false</code>
     */
    @Override
    public boolean hasKinds() {
        return getKinds().isEmpty();
    }

    /**
     * Indicate if the film has actors or not.
     *
     * @return <code>true</code> if the film has actors else <code>false</code>
     */
    @Override
    public boolean hasActors() {
        return getActors().isEmpty();
    }

    /**
     * Indicate if the film has a language or not.
     *
     * @return <code>true</code> if the film has a language else <code>false</code>
     */
    @Override
    public boolean hasLanguage() {
        return getTheLanguage() != null;
    }

    /**
     * Indicate if the film has a realizer or not.
     *
     * @return <code>true</code> if the film has a realizer else <code>false</code>
     */
    @Override
    public boolean hasRealizer() {
        return getTheRealizer() != null;
    }

    @Override
    public void addActors(Set<Person> actors) {
        getActors().addAll(actors);
    }

    @Override
    public void addKinds(Set<SimpleData> kinds) {
        getKinds().addAll(kinds);
    }

    @Override
    public void addActor(Person actor) {
        assert actor.getType().equals(IActorService.PERSON_TYPE) : "The person must of type Actor";

        getActors().add(actor);
    }

    @Override
    public void addKind(SimpleData kind) {
        getKinds().add(kind);
    }

    @Override
    public boolean containsActor(Person actor) {
        return EntityUtils.containsID(getActors(), actor.getId());
    }

    @Override
    public boolean containsKind(SimpleData kind) {
        return getKinds().contains(kind);
    }
}