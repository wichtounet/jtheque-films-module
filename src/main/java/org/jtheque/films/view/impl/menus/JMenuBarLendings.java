package org.jtheque.films.view.impl.menus;

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
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.view.impl.components.menu.JThequeMenu;
import org.jtheque.core.managers.view.impl.components.menu.JThequeMenuItem;

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

        addAction(menu, "lendFilmAction");
        addAction(menu, "returnCurrentFilmAction");

        menu.addSeparator();

        addAction(menu, "closeLendingsViewAction");

        add(menu);
    }

    /**
     * Add an action menu item to a menu.
     *
     * @param menu   The menu.
     * @param action The name of the action bean.
     */
    private static void addAction(JMenu menu, String action) {
        menu.add(new JThequeMenuItem(Managers.getManager(IResourceManager.class).getAction(action)));
    }
}