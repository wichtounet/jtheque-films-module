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
