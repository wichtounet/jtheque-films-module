package org.jtheque.films.view.impl.choiceActions;

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

import org.jtheque.core.utils.CoreUtils;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.primary.controller.impl.undo.GenericDataDeletedEdit;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.view.impl.choice.AbstractPrimaryDeleteChoiceAction;
import org.jtheque.primary.view.impl.choice.Deleter;

/**
 * An action to delete the selected item.
 *
 * @author Baptiste Wicht
 */
public final class DeleteChoiceAction extends AbstractPrimaryDeleteChoiceAction {
    /**
     * Construct a new DeleteChoiceAction.
     */
    public DeleteChoiceAction() {
        super();

        addDeleters(new FilmDeleter(), new ActorDeleter(), new RealizerDeleter());
        addPrimaryDeleters();
    }

    /**
     * A Deleter for Film object.
     *
     * @author Baptiste Wicht
     */
    private static final class FilmDeleter extends Deleter<Film> {
        /**
         * Construct a new FilmDeleter.
         */
        private FilmDeleter() {
            super(IFilmsService.DATA_TYPE);
        }

        @Override
        public void delete(Film o) {
            addEditIfDeleted(
                    CoreUtils.<IFilmsService>getBean("filmsService").delete(o),
                    new GenericDataDeletedEdit<Film>("filmsService", o));
        }
    }

    /**
     * A Deleter for Actor object.
     *
     * @author Baptiste Wicht
     */
    private static final class ActorDeleter extends Deleter<Person> {
        /**
         * Construct a new ActorDeleter.
         */
        private ActorDeleter() {
            super(IActorService.DATA_TYPE);
        }

        @Override
        public void delete(Person o) {
            addEditIfDeleted(
                    CoreUtils.<IActorService>getBean("actorService").delete(o),
                    new GenericDataDeletedEdit<Person>("actorService", o));
        }
    }

    /**
     * A Deleter for Realizer object.
     *
     * @author Baptiste Wicht
     */
    private static final class RealizerDeleter extends Deleter<Person> {
        /**
         * Construct a new RealizerDeleter.
         */
        private RealizerDeleter() {
            super(IRealizersService.DATA_TYPE);
        }

        @Override
        public void delete(Person o) {
            addEditIfDeleted(
                    CoreUtils.<IRealizersService>getBean("realizersService").delete(o),
                    new GenericDataDeletedEdit<Person>("realizersService", o));
        }
    }
}