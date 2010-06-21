package org.jtheque.films.view.impl.renderers;

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
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.films.services.impl.utils.VideoFile;
import org.jtheque.films.utils.Constants;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

/**
 * A renderer to display a module in a list.
 *
 * @author Baptiste Wicht
 */
public final class VideoListRenderer extends JPanel implements ListCellRenderer {
    private static final long serialVersionUID = -4146856433184922449L;

    private final JLabel labelIcon;
    private final JLabel labelFilm;
    private final JLabel labelFile;

    /**
     * Construct a new ModuleListRenderer.
     */
    public VideoListRenderer() {
        super();

        PanelBuilder builder = new JThequePanelBuilder(this);

        labelIcon = builder.add(
                new JLabel(Managers.getManager(IResourceManager.class).getIcon(Constants.IMAGE_BASE_NAME, "videofile", ImageType.PNG)),
                builder.gbcSet(0, 0, GridBagUtils.NONE, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 2, 0.0, 0.0));

        labelFilm = builder.add(new JLabel(),
                builder.gbcSet(1, 0, GridBagUtils.NONE, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 1, 1.0, 0.0));
        labelFilm.setFont(labelFilm.getFont().deriveFont(Font.BOLD, 16));

        labelFile = builder.add(new JLabel(),
                builder.gbcSet(1, 1, GridBagUtils.NONE, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 1, 1.0, 0.0));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        if (isSelected) {
            setBackground(Color.blue);
            setChildsForeground(Color.white);
        } else {
            setBackground(Color.white);
            setChildsForeground(Color.black);
        }

        VideoFile video = (VideoFile) value;

        labelFilm.setText(video.getFilm().getTitle());
        labelFile.setText(video.getFile());

        return this;
    }

    /**
     * Set the foregound of the childs.
     *
     * @param color The foreground color.
     */
    private void setChildsForeground(Color color) {
        labelIcon.setForeground(color);
        labelFilm.setForeground(color);
        labelFile.setForeground(color);
    }
}