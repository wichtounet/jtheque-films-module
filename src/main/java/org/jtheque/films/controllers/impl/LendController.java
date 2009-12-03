package org.jtheque.films.controllers.impl;

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

import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.films.controllers.able.ILendController;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.view.able.ILendFilmView;
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.able.ILendingsService;
import org.jtheque.utils.bean.IntDate;

import javax.annotation.Resource;

/**
 * A lend controller implementation.
 *
 * @author Baptiste Wicht
 */
public final class LendController extends AbstractController implements ILendController {
    @Resource
    private ILendingsService lendingsService;

    @Resource
    private ILendFilmView lendFilmView;

    @Override
    public ILendFilmView getView() {
        return lendFilmView;
    }

    @Override
    public void newLending(Person borrower, Film film) {
        Lending lending = lendingsService.getEmptyLending();

        lending.setTheOther(film.getId());
        lending.setThePerson(borrower);
        lending.setDate(IntDate.today());

        lendingsService.create(lending);
    }
}