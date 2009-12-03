package org.jtheque.films.view.impl.actions.sort;

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

import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;
import org.jtheque.films.view.able.IRealizerView;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * Action to sort the realizers.
 *
 * @author Baptiste Wicht
 */
public final class AcSortRealizer extends JThequeSimpleAction {
    private static final long serialVersionUID = 1651417172586677829L;

    private final String sortType;

    @Resource
    private IRealizerView realizerView;

    /**
     * Construct a new AcSortRealizer.
     *
     * @param key      The internationalization key.
     * @param sortType The type of sort.
     */
    public AcSortRealizer(String key, String sortType) {
        super();

        setTextKey(key);
        this.sortType = sortType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        realizerView.sort(sortType);
    }
}