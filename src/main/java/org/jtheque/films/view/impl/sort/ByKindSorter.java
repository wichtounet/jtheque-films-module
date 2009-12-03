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
import org.jtheque.primary.od.able.Kind;
import org.jtheque.primary.services.able.IKindsService;
import org.jtheque.primary.view.impl.models.tree.Category;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.primary.view.impl.sort.Sorter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * a sorter to sort by kind.
 *
 * @author Baptiste Wicht
 */
public final class ByKindSorter implements Sorter {
    @Resource
    private IFilmController filmController;

    @Override
    public boolean canSort(String content, String sortType) {
        return IFilmsService.DATA_TYPE.equals(content) && sortType.equals(IKindsService.DATA_TYPE);
    }

    @Override
    public void sort(JThequeTreeModel model) {
        TreeElement root = model.getRoot();

        List<Kind> groups = new ArrayList<Kind>(10);

        for (Film film : filmController.getDisplayList()) {
            for (Kind kind : film.getKinds()) {
                if (!groups.contains(kind)) {
                    root.add(new Category(kind.getElementName()));
                    groups.add(kind);
                }

                int index = groups.indexOf(kind);

                root.getChild(index).add(film);
            }
        }

        model.setRootElement(root);
    }
}