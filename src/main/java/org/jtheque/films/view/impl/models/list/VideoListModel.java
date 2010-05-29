package org.jtheque.films.view.impl.models.list;

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
import org.jtheque.core.managers.view.impl.components.model.SimpleListModel;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.impl.utils.VideoFile;

import javax.annotation.Resource;

/**
 * A List model to display the modules.
 *
 * @author Baptiste Wicht
 */
public final class VideoListModel extends SimpleListModel<VideoFile> {
    @Resource
    private IFilmsService filmsService;

    /**
     * Construct a new ModuleListModel.
     */
    public VideoListModel() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        setElements(filmsService.getVideoFiles());
    }

    /**
     * Refresh the model.
     */
    public void refresh() {
        setElements(filmsService.getVideoFiles());
    }
}