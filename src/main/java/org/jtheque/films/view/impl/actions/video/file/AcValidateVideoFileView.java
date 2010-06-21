package org.jtheque.films.view.impl.actions.video.file;

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