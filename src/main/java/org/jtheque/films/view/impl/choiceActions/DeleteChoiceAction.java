package org.jtheque.films.view.impl.choiceActions;

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
