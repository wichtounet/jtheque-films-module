package org.jtheque.films.controllers.impl;

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
