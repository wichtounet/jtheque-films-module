package org.jtheque.films.view.impl.actions.file;

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
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.IImportController;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.utils.StringUtils;

import javax.annotation.Resource;

import java.awt.event.ActionEvent;

/**
 * Action to import something.
 *
 * @author Baptiste Wicht
 */
public final class AcImport extends JThequeAction {
    private final FileType fileType;

    @Resource
    private IImportController importController;

    /**
     * Construct a new <code>AcImport</code> for a specific type of file.
     *
     * @param key      The internationalization key.
     * @param fileType The type of file.
     * @param icon     The icon of the action.
     */
    public AcImport(String key, String fileType, String icon) {
        super(key);

        this.fileType = FileType.fromString(fileType);

        if (StringUtils.isNotEmpty(icon)) {
            setIcon(Managers.getManager(IResourceManager.class).getIcon(Constants.IMAGE_BASE_NAME, icon, ImageType.PNG));
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        importController.openImportView(fileType);
    }
}
