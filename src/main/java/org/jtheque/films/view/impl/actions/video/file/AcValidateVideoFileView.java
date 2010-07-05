package org.jtheque.films.view.impl.actions.video.file;

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
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.view.able.IVideoFileView;
import org.jtheque.films.view.able.IVideoView;

import javax.annotation.Resource;

import java.awt.event.ActionEvent;

/**
 * An action to validate the video file view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateVideoFileView extends JThequeAction {
    @Resource
    private IFilmsService filmsService;

    @Resource
    private IVideoFileView videoFileView;

    @Resource
    private IVideoView videoView;

    /**
     * Construct a new AcValidateVideoFileView.
     */
    public AcValidateVideoFileView() {
        super("video.file.view.actions.validate");

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        videoFileView.getFilm().setFilePath(videoFileView.getFilePath());

        filmsService.save(videoFileView.getFilm());

        videoView.refreshList();

        videoFileView.closeDown();
    }
}
