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

import org.jtheque.core.utils.db.Note;
import org.jtheque.films.services.able.INotesService;
import org.jtheque.primary.controller.able.IPrincipalController;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.od.able.Notable;
import org.jtheque.primary.view.impl.models.tree.Category;
import org.jtheque.primary.view.impl.models.tree.JThequeTreeModel;
import org.jtheque.primary.view.impl.models.tree.TreeElement;
import org.jtheque.primary.view.impl.sort.Sorter;

import java.util.ArrayList;
import java.util.List;

/**
 * A sorter by note.
 *
 * @author Baptiste Wicht
 */
public final class ByNoteSorter implements Sorter {
    private final IPrincipalController<? extends Data> controller;

    /**
     * Construct a new ByNoteSorter.
     *
     * @param controller The principal controller.
     */
    public ByNoteSorter(IPrincipalController<? extends Data> controller) {
        this.controller = controller;
    }

    @Override
    public boolean canSort(String content, String sortType) {
        return content.equals(controller.getDataType()) && sortType.equals(INotesService.DATA_TYPE);
    }

    @Override
    public void sort(JThequeTreeModel model) {
        TreeElement root = model.getRoot();

        List<Note> groups = new ArrayList<Note>(7);

        for (Data data : controller.getDisplayList()) {
            Note note = ((Notable) data).getNote();

            if (!groups.contains(note)) {
                groups.add(note);
                root.add(new Category(note.getElementName()));
            }

            root.getChild(groups.indexOf(note)).add(data);
        }

        model.setRootElement(root);
    }
}
