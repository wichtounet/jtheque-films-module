package org.jtheque.films.view.impl.actions.video;

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

import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.view.able.IVideoView;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to open a video file.
 *
 * @author Baptiste Wicht
 */
public final class AcRefreshVideoView extends JThequeAction {
    @Resource
    private IVideoView videoView;

    /**
     * Construct a new AcOpenVideoFile.
     */
    public AcRefreshVideoView() {
        super("video.view.actions.refresh");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        videoView.refreshList();
    }
}