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
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.impl.utils.VideoFile;
import org.jtheque.utils.collections.CollectionUtils;

import javax.annotation.Resource;
import javax.swing.DefaultListModel;
import java.util.List;

/**
 * A List model to display the modules.
 *
 * @author Baptiste Wicht
 */
public final class VideoListModel extends DefaultListModel {
    private static final long serialVersionUID = -4658778193485774835L;

    private List<VideoFile> files;

    @Resource
    private IFilmsService filmsService;

    /**
     * Construct a new ModuleListModel.
     */
    public VideoListModel() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);

        files = CollectionUtils.copyOf(filmsService.getVideoFiles());
    }

    @Override
    public Object getElementAt(int index) {
        return files.get(index);
    }

    @Override
    public Object get(int index) {
        return files.get(index);
    }

    @Override
    public int getSize() {
        return files.size();
    }

    /**
     * Refresh the model.
     */
    public void refresh() {
        files = CollectionUtils.copyOf(filmsService.getVideoFiles());

        fireContentsChanged(this, 0, files.size());
    }
}