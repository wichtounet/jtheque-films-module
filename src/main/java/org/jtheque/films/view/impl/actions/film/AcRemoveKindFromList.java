package org.jtheque.films.view.impl.actions.film;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;
import org.jtheque.films.view.able.IInfosKindsView;
import org.jtheque.utils.collections.ArrayUtils;

import javax.swing.DefaultListModel;

import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * Action to remove an actor from the list.
 *
 * @author Baptiste Wicht
 */
public final class AcRemoveKindFromList extends JThequeSimpleAction {
    /**
     * Construct a new <code>AcRemoveKindFromList</code>.
     */
    public AcRemoveKindFromList() {
        super();

        setText("<<");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IInfosKindsView kindsView = Managers.getManager(IBeansManager.class).getBean("filmKindsView");

        DefaultListModel kindsModel = kindsView.getKindsModel();
        DefaultListModel kindsForFilmModel = kindsView.getKindsForFilmModel();

        int[] selectedKinds = kindsView.getSelectedKindsForFilmIndexes();

        Arrays.sort(selectedKinds);

        ArrayUtils.reverse(selectedKinds);

        for (int index : selectedKinds) {
            Object kind = kindsForFilmModel.remove(index);
            kindsModel.addElement(kind);
        }
    }
}
