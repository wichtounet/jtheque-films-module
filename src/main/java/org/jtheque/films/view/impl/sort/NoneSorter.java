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
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.primary.view.impl.sort.Sorter;

/**
 * A sorter who get directly the informations from the display list.
 *
 * @author Baptiste Wicht
 */
public final class NoneSorter implements Sorter {
    private final IPrincipalController<? extends Data> controller;

    /**
     * Construct a new <code>NoneSorter</code> for the specified principal controller.
     *
     * @param controller The principal controller.
     */
    public NoneSorter(IPrincipalController<? extends Data> controller) {
        this.controller = controller;
    }

    @Override
    public boolean canSort(String content, String sortType) {
        return content.equals(controller.getDataType()) && "None".equals(sortType);
    }

    @Override
    public void sort(JThequeTreeModel model) {
        TreeElement root = model.getRoot();

        root.addAll(controller.getDisplayList());

        model.setRootElement(root);
    }
}
