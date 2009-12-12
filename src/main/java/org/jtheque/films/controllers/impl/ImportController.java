package org.jtheque.films.controllers.impl;

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