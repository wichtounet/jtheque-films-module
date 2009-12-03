package org.jtheque.films.view.impl.actions.jtheque;

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
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.utils.Constants;
import org.jtheque.primary.view.able.PrincipalDataView;

import java.awt.event.ActionEvent;

/**
 * Action to refresh the list.
 *
 * @author Baptiste Wicht
 */
public final class AcRefreshList extends JThequeAction {
    private static final long serialVersionUID = -6059011169838015671L;

    /**
     * Construct a new AcRefreshList.
     */
    public AcRefreshList() {
        super("jtheque.actions.refresh");

        setIcon(Managers.getManager(IResourceManager.class).getIcon(Constants.IMAGE_BASE_NAME, "refresh", ImageType.PNG));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PrincipalDataView view = (PrincipalDataView) Managers.getManager(IViewManager.class).getViews().getSelectedView().getComponent();

        view.resort();
    }
}