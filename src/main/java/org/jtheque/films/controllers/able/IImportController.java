package org.jtheque.films.controllers.able;

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

import org.jtheque.core.managers.view.able.controller.Controller;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.view.able.IImportView;
import org.jtheque.utils.io.FileException;

/**
 * An import controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IImportController extends Controller {
    /**
     * Open the export view.
     *
     * @param fileType The type of the file we want to export to.
     */
    void openImportView(Constants.Files.FileType fileType);

    /**
     * Import Data from a file.
     *
     * @param filePath The path to the file.
     *
     * @throws FileException If there is an I/O problem during the import.
     */
    void importData(String filePath) throws FileException;

    @Override
    IImportView getView();
}
