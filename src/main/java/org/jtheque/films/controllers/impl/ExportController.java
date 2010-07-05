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
import org.jtheque.films.controllers.able.IExportController;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.impl.utils.file.exports.Exporter;
import org.jtheque.films.services.impl.utils.file.exports.ExporterFactory;
import org.jtheque.films.services.impl.utils.file.jt.FileFilterFactory;
import org.jtheque.films.services.impl.utils.search.Searcher;
import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.films.view.able.IExportView;

import javax.annotation.Resource;

import java.util.Arrays;

/**
 * A controller for the export view.
 *
 * @author Baptiste Wicht
 */
public final class ExportController extends AbstractController implements IExportController {
    private FileType fileType;

    @Resource
    private IExportView exportView;

    @Resource
    private IFilmsService filmsService;

    @Override
    public void openExportView(FileType fileType) {
        this.fileType = fileType;

        exportView.sendMessage("filter", FileFilterFactory.getFileFilter(fileType));
        displayView();
    }

    @Override
    public void export(Searcher<Film> search, String filePath) {
        Exporter<Film> exporter = ExporterFactory.getExporter(fileType);

        exporter.setDatas(search.search(filmsService.getDatas()));

        exporter.export(filePath);
    }

    @Override
    public void exportFilm(String filePath, Film film) {
        Exporter<Film> exporter = ExporterFactory.getExporter(FileType.JTF);

        exporter.setDatas(Arrays.asList(film));

        exporter.export(filePath);
    }

    @Override
    public IExportView getView() {
        return exportView;
    }
}
