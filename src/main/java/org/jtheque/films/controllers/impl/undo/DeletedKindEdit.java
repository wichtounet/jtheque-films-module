package org.jtheque.films.controllers.impl.undo;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.primary.od.able.Kind;
import org.jtheque.primary.services.able.IKindsService;

import javax.annotation.Resource;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

/**
 * An edit for a deletion of kind.
 *
 * @author Baptiste Wicht
 */
public final class DeletedKindEdit extends AbstractUndoableEdit {
    private static final long serialVersionUID = -3037357344759861059L;

    private final Kind kind;

    @Resource
    private IKindsService kindsService;

    /**
     * Construct a new DeletedKindEdit.
     *
     * @param kind The deleted kind.
     */
    public DeletedKindEdit(Kind kind) {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        this.kind = kind;
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();

        kindsService.create(kind);
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();

        kindsService.delete(kind);
    }

    @Override
    public String getPresentationName() {
        return Managers.getManager(ILanguageManager.class).getMessage("undo.edits.delete");
    }
}