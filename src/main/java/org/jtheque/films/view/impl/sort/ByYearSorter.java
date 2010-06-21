package org.jtheque.films.view.impl.sort;

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

import org.jtheque.films.controllers.able.IFilmController;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.utils.Constants;
import org.jtheque.primary.view.impl.models.tree.Category;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.primary.view.impl.sort.Sorter;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * A sorter to sort by year.
 *
 * @author Baptiste Wicht
 */
public final class ByYearSorter implements Sorter {
    @Resource
    private IFilmController filmController;

    @Override
    public boolean canSort(String content, String sortType) {
        return content.equals(IFilmsService.DATA_TYPE) && sortType.equals(Constants.YEAR);
    }

    @Override
    public void sort(JThequeTreeModel model) {
        TreeElement root = model.getRoot();

        Map<Integer, Category> groups = new HashMap<Integer, Category>(10);

        for (Film film : filmController.getDisplayList()) {
            int year = film.getYear();

            if (!groups.containsKey(year)) {
                Category category = new Category(Integer.toString(year));

                root.add(category);
                groups.put(year, category);
            }

            groups.get(year).add(film);
        }

        model.setRootElement(root);
    }
}