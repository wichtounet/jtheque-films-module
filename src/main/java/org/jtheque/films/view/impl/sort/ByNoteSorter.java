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