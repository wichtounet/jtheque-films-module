package org.jtheque.films.view.impl.sort;

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

import org.jtheque.films.controllers.able.IFilmController;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.view.impl.models.tree.Category;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.primary.view.impl.sort.Sorter;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * A sorter to sort by type.
 *
 * @author Baptiste Wicht
 */
public final class ByTypeSorter implements Sorter {
    @Resource
    private IFilmController filmController;

    @Override
    public boolean canSort(String content, String sortType) {
        return content.equals(IFilmsService.DATA_TYPE) && sortType.equals(SimpleData.DataType.TYPE.getDataType());
    }

    @Override
    public void sort(JThequeTreeModel model) {
        TreeElement root = model.getRoot();

        Map<SimpleData, Category> groups = new HashMap<SimpleData, Category>(10);

        for (Film film : filmController.getDisplayList()) {
            SimpleData type = film.getTheType();

            if (!groups.containsKey(type)) {
                Category category = new Category(type.getDisplayableText());

                root.add(category);
                groups.put(type, category);
            }

            groups.get(type).add(film);
        }

        model.setRootElement(root);
    }
}
