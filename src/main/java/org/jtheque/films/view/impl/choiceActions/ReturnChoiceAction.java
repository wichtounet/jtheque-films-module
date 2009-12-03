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

import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.primary.services.able.ILendingsService;
import org.jtheque.primary.view.impl.choice.AbstractChoiceAction;

import javax.annotation.Resource;

/**
 * An action to return a film.
 *
 * @author Baptiste Wicht
 */
public final class ReturnChoiceAction extends AbstractChoiceAction {
    @Resource
    private ILendingsService lendingsService;

    @Override
    public boolean canDoAction(String action) {
        return "return".equals(action);
    }

    @Override
    public void execute() {
        Film film = (Film) getSelectedItem();

        if (film.getTheLending() != null) {
            lendingsService.delete(film.getTheLending());
        }
    }
}