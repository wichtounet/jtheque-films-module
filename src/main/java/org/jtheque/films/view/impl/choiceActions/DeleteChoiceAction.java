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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.films.controllers.impl.undo.DeletedCountryEdit;
import org.jtheque.films.controllers.impl.undo.DeletedFilmEdit;
import org.jtheque.films.controllers.impl.undo.DeletedKindEdit;
import org.jtheque.films.controllers.impl.undo.DeletedLanguageEdit;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.utils.Constants;
import org.jtheque.primary.controller.impl.undo.DeletedPersonEdit;
import org.jtheque.primary.controller.impl.undo.DeletedSagaEdit;
import org.jtheque.primary.controller.impl.undo.DeletedTypeEdit;
import org.jtheque.primary.od.able.Country;
import org.jtheque.primary.od.able.Kind;
import org.jtheque.primary.od.able.Language;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.Saga;
import org.jtheque.primary.od.able.Type;
import org.jtheque.primary.services.able.IBorrowersService;
import org.jtheque.primary.services.able.ICountriesService;
import org.jtheque.primary.services.able.IKindsService;
import org.jtheque.primary.services.able.ILanguagesService;
import org.jtheque.primary.services.able.ISagasService;
import org.jtheque.primary.services.able.ITypesService;
import org.jtheque.primary.view.impl.choice.AbstractDeleteChoiceAction;
import org.jtheque.primary.view.impl.choice.Deleter;

import javax.annotation.Resource;

/**
 * An action to delete the selected item.
 *
 * @author Baptiste Wicht
 */
public final class DeleteChoiceAction extends AbstractDeleteChoiceAction {
    @Resource
    private ISagasService sagasService;

    @Resource
    private IBorrowersService borrowersService;

    @Resource
    private ICountriesService countriesService;

    @Resource
    private IRealizersService realizersService;

    @Resource
    private ILanguagesService languagesService;

    @Resource
    private ITypesService typesService;

    @Resource
    private IKindsService kindsService;

    @Resource
    private IActorService actorService;

    @Resource
    private IFilmsService filmsService;

    /**
     * Construct a new DeleteChoiceAction.
     */
    public DeleteChoiceAction() {
        super();

        setDeleters(new FilmDeleter(), new ActorDeleter(), new KindDeleter(), new TypeDeleter(), new LanguageDeleter(), new RealizerDeleter(),
                new CountryDeleter(), new BorrowerDeleter(), new SagaDeleter());
    }

    @Override
    public boolean canDoAction(String action) {
        return "delete".equals(action);
    }

    @Override
    public void execute() {
        final boolean yes = Managers.getManager(IViewManager.class).askUserForConfirmation(
                Managers.getManager(ILanguageManager.class).getMessage("choice.dialogs.delete") + ' ' + getSelectedItem().toString(),
                Managers.getManager(ILanguageManager.class).getMessage("choice.dialogs.delete.title"));

        if (yes) {
            delete();
        }
    }

    /**
     * A Deleter for Film object.
     *
     * @author Baptiste Wicht
     */
    private final class FilmDeleter extends Deleter {
        /**
         * Construct a new FilmDeleter.
         */
        private FilmDeleter() {
            super(IFilmsService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = filmsService.delete((Film) o);

            addEditIfDeleted(deleted, new DeletedFilmEdit((Film) o));
        }
    }

    /**
     * A Deleter for Actor object.
     *
     * @author Baptiste Wicht
     */
    private final class ActorDeleter extends Deleter {
        /**
         * Construct a new ActorDeleter.
         */
        private ActorDeleter() {
            super(IActorService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = actorService.delete((Person) o);

            addEditIfDeleted(deleted, new DeletedPersonEdit((Person) o));
        }
    }

    /**
     * A Deleter for Kind object.
     *
     * @author Baptiste Wicht
     */
    private final class KindDeleter extends Deleter {
        /**
         * Construct a new KindDeleter.
         */
        private KindDeleter() {
            super(IKindsService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = kindsService.delete((Kind) o);

            addEditIfDeleted(deleted, new DeletedKindEdit((Kind) o));
        }
    }

    /**
     * A Deleter for Type object.
     *
     * @author Baptiste Wicht
     */
    private final class TypeDeleter extends Deleter {
        /**
         * Construct a new TypeDeleter.
         */
        private TypeDeleter() {
            super(ITypesService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = typesService.delete((Type) o);

            addEditIfDeleted(deleted, new DeletedTypeEdit((Type) o));
        }
    }

    /**
     * A Deleter for Language object.
     *
     * @author Baptiste Wicht
     */
    private final class LanguageDeleter extends Deleter {
        /**
         * Construct a new LanguageDeleter.
         */
        private LanguageDeleter() {
            super(ILanguagesService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = languagesService.delete((Language) o);

            addEditIfDeleted(deleted, new DeletedLanguageEdit((Language) o));
        }
    }

    /**
     * A Deleter for Realizer object.
     *
     * @author Baptiste Wicht
     */
    private final class RealizerDeleter extends Deleter {
        /**
         * Construct a new RealizerDeleter.
         */
        private RealizerDeleter() {
            super(IRealizersService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = realizersService.delete((Person) o);

            addEditIfDeleted(deleted, new DeletedPersonEdit((Person) o));
        }
    }

    /**
     * A Deleter for Country object.
     *
     * @author Baptiste Wicht
     */
    private final class CountryDeleter extends Deleter {
        /**
         * Construct a new CountryDeleter.
         */
        private CountryDeleter() {
            super(ICountriesService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = countriesService.delete((Country) o);

            addEditIfDeleted(deleted, new DeletedCountryEdit((Country) o));
        }
    }

    /**
     * A Deleter for Borrower object.
     *
     * @author Baptiste Wicht
     */
    private final class BorrowerDeleter extends Deleter {
        /**
         * Construct a new BorrowerDeleter.
         */
        private BorrowerDeleter() {
            super(Constants.BORROWERS);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = borrowersService.delete((Person) o);

            addEditIfDeleted(deleted, new DeletedPersonEdit((Person) o));
        }
    }

    /**
     * A Deleter for Saga object.
     *
     * @author Baptiste Wicht
     */
    private final class SagaDeleter extends Deleter {
        /**
         * Construct a new SagaDeleter.
         */
        private SagaDeleter() {
            super(ISagasService.DATA_TYPE);
        }

        @Override
        public void delete(Object o) {
            boolean deleted = sagasService.delete((Saga) o);

            addEditIfDeleted(deleted, new DeletedSagaEdit((Saga) o));
        }
    }
}