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