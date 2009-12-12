package org.jtheque.films.view.impl.actions.video;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.services.impl.utils.VideoFile;
import org.jtheque.films.view.able.IVideoView;
import org.jtheque.utils.DesktopUtils;

import java.awt.event.ActionEvent;
import java.io.File;

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

/**
 * An action to open a video file.
 *
 * @author Baptiste Wicht
 */
public final class AcOpenVideoFile extends JThequeAction {
    /**
     * Construct a new AcOpenVideoFile.
     */
    public AcOpenVideoFile() {
        super("video.view.actions.open");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        VideoFile file = Managers.getManager(IBeansManager.class).<IVideoView>getBean("videoView").getSelectedFile();

        DesktopUtils.open(new File(file.getFilm().getFilePath()));
    }
}
