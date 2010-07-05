package org.jtheque.films.view.impl.models.list;

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
