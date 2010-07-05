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

import org.jtheque.primary.controller.able.IPrincipalController;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.view.impl.models.tree.Category;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.primary.view.impl.sort.Sorter;

import java.util.HashMap;
import java.util.Map;

/**
 * This class sort by country.
 *
 * @author Baptiste Wicht
 */
public final class ByCountrySorter implements Sorter {
    private final IPrincipalController<? extends Person> controller;

    /**
     * Construct a new ByCountrySorter.
     *
     * @param controller The principal controller.
     */
    public ByCountrySorter(IPrincipalController<? extends Person> controller) {
        this.controller = controller;
    }

    @Override
    public boolean canSort(String content, String sortType) {
        return content.equals(controller.getDataType()) && sortType.equals(SimpleData.DataType.KIND.getDataType());
    }

    @Override
    public void sort(JThequeTreeModel model) {
        TreeElement root = model.getRoot();

        Map<SimpleData, Category> groups = new HashMap<SimpleData, Category>(controller.getDisplayList().size() / 4);

        for (Data data : controller.getDisplayList()) {
            Person person = (Person) data;

            SimpleData country = person.getTheCountry();

            if (!groups.containsKey(country)) {
                Category category = new Category(country.getDisplayableText());

                root.add(category);
                groups.put(country, category);
            }

            groups.get(country).add(person);
        }

        model.setRootElement(root);
    }
}
