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

import org.jtheque.primary.controller.able.IPrincipalController;
import org.jtheque.primary.od.able.Country;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.able.ICountriesService;
import org.jtheque.primary.view.impl.models.tree.Category;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.primary.view.impl.sort.Sorter;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * This class sort by country.
 *
 * @author Baptiste Wicht
 */
public final class ByCountrySorter implements Sorter {
    @Resource
    private ICountriesService countriesService;

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
        return content.equals(controller.getDataType()) && sortType.equals(ICountriesService.DATA_TYPE);
    }

    @Override
    public void sort(JThequeTreeModel model) {
        TreeElement root = model.getRoot();

        Map<Country, Category> groups = new HashMap<Country, Category>(controller.getDisplayList().size() / 4);

        for (Data data : controller.getDisplayList()) {
            Person person = (Person) data;

            Country country = person.getTheCountry();

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