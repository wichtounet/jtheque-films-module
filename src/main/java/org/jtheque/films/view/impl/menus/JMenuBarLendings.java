package org.jtheque.films.view.impl.menus;

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

import org.jtheque.core.managers.view.impl.components.menu.JThequeMenu;
import org.jtheque.core.managers.view.impl.components.menu.JThequeMenuItem;
import org.jtheque.films.view.impl.actions.lendings.AcReturnCurrentFilm;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * The menu bar for the lendings frame.
 *
 * @author Baptiste Wicht
 */
public final class JMenuBarLendings extends JMenuBar {
    private static final long serialVersionUID = -6264940478535994283L;

    /**
     * Construct a new <code>JMenuBarLendings</code>.
     */
    public JMenuBarLendings() {
        super();

        JMenu menu = new JThequeMenu("menu.lendings");

        addAction(menu, new DisplayBeanViewAction("lendings.view.actions.lend", "lendFilmView"));
        addAction(menu, new AcReturnCurrentFilm());

        menu.addSeparator();

        addAction(menu, new CloseBeanViewAction("generic.view.actions.close", "lendingsView"));

        add(menu);
    }

    /**
     * Add an action menu item to a menu.
     *
     * @param menu   The menu.
     * @param action The name of the action bean.
     */
    private static void addAction(JMenu menu, Action action) {
        menu.add(new JThequeMenuItem(action));
    }
}
