package org.jtheque.films.controllers.impl;

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

import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.films.controllers.able.IImportController;
import org.jtheque.films.services.impl.utils.file.imports.ImportManager;
import org.jtheque.films.services.impl.utils.file.jt.FileFilterFactory;
import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.films.view.able.IImportView;
import org.jtheque.utils.io.FileException;

import javax.annotation.Resource;

/**
 * Controller of the import view.
 *
 * @author Baptiste Wicht
 */
public final class ImportController extends AbstractController implements IImportController {
    private FileType fileType;

    @Resource
    private IImportView importView;

    @Override
    public void openImportView(FileType fileType) {
        this.fileType = fileType;

        getView().sendMessage("filter", FileFilterFactory.getFileFilter(fileType));
        displayView();
    }

    @Override
    public void importData(String filePath) throws FileException {
        ImportManager.importFrom(fileType, filePath);
    }

    @Override
    public IImportView getView() {
        return importView;
    }
}
