package org.jtheque.films.services.impl;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.utils.Response;
import org.jtheque.core.utils.SystemProperty;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.IPublicationService;
import org.jtheque.films.services.impl.utils.file.FTPConnectionInfos;
import org.jtheque.films.services.impl.utils.file.FTPManager;
import org.jtheque.films.services.impl.utils.file.exports.Exporter;
import org.jtheque.films.services.impl.utils.file.exports.ExporterFactory;
import org.jtheque.films.utils.Constants;

import javax.annotation.Resource;

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
 * @author Baptiste Wicht
 */
public final class PublicationService implements IPublicationService {
    @Resource
    private IFilmsService filmsService;

    @Override
    public void uploadListOfFilms(FTPConnectionInfos infos) {
        String local = SystemProperty.JAVA_IO_TMP_DIR.get() + "/index.html";

        Exporter<Film> exporter = ExporterFactory.getExporter(Constants.Files.FileType.HTML);

        exporter.setDatas(filmsService.getFilms());

        exporter.export(local);

        String remote = infos.getPath() + "index.html";

        FTPManager ftp = new FTPManager(infos);

        Response connection = ftp.connect();

        if (connection.isOk()) {
            Response store = ftp.upload(local, remote);

            Managers.getManager(IViewManager.class).displayText(store.getMessage());
        } else {
            Managers.getManager(IViewManager.class).displayText(connection.getMessage());
        }

        ftp.disconnect();
    }
}
