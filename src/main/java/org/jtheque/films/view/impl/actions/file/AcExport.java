package org.jtheque.films.view.impl.actions.file;

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
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.IExportController;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.utils.StringUtils;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to export datas to a file.
 *
 * @author Baptiste Wicht
 */
public final class AcExport extends JThequeAction {
    private static final long serialVersionUID = -2448963462025446778L;

    private final FileType fileType;

    @Resource
    private IExportController exportController;

    /**
     * Construct a new <code>AcExport</code> for a specific type of file.
     *
     * @param key      The internationalization key.
     * @param fileType The type of file.
     * @param icon     The icon of the action.
     */
    public AcExport(String key, String fileType, String icon) {
        super(key);

        this.fileType = FileType.fromString(fileType);

        if (StringUtils.isNotEmpty(icon)) {
            setIcon(Managers.getManager(IResourceManager.class).getIcon(Constants.IMAGE_BASE_NAME, icon, ImageType.PNG));
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        exportController.openExportView(fileType);
    }
}