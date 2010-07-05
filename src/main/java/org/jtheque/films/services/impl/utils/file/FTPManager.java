package org.jtheque.films.services.impl.utils.file;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.utils.Response;
import org.jtheque.utils.io.FileUtils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
 * A manager to connect, store file in, disconnect to a FTP server.
 *
 * @author Baptiste Wicht
 */
public final class FTPManager {
    private final FTPClient ftp;
    private final FTPConnectionInfos infos;

    /**
     * Construct a new FTP Manager.
     *
     * @param infos The connection infos.
     */
    public FTPManager(FTPConnectionInfos infos) {
        super();

        this.infos = infos;

        ftp = new FTPClient();
    }

    /**
     * Connect to the server.
     *
     * @return The result of the connection.
     */
    public Response connect() {
        Response response;

        try {
            ftp.connect(infos.getHost());

            int reply = ftp.getReplyCode();

            if (FTPReply.isPositiveCompletion(reply)) {
                if (ftp.login(infos.getUser(), infos.getPassword())) {
                    response = new Response(true, "");

                    ftp.setFileType(FTP.ASCII_FILE_TYPE);

                    if (infos.isPassive()) {
                        ftp.enterLocalPassiveMode();
                    }
                } else {
                    ftp.logout();

                    response = new Response(false, "ftp.errors.login.impossible");
                }
            } else {
                ftp.disconnect();

                response = new Response(false, "ftp.errors.connection.refused");
            }
        } catch (IOException e) {
            disconnectSimply();

            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);

            response = new Response(false, "ftp.errors.connection.impossible");
        }

        return response;
    }

    /**
     * Simply disconnect without logout.
     */
    private void disconnectSimply() {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (IOException f) {
                Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(f);
            }
        }
    }

    /**
     * Upload a file to the server.
     *
     * @param local  The local file.
     * @param remote The remote file.
     *
     * @return The response of the store.
     */
    public Response upload(String local, String remote) {
        Response response = null;

        InputStream input = null;
        try {
            input = new FileInputStream(local);

            boolean stored = ftp.storeFile(remote, input);

            response = stored ? new Response(true, "ftp.messages.write.stored") : new Response(false, "ftp.errors.write.notstored");
        } catch (FTPConnectionClosedException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);

            response = new Response(false, "ftp.errors.write.close");
        } catch (IOException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);

            response = new Response(false, "ftp.errors.write.errors");
        } finally {
            FileUtils.close(input);
        }

        return response;
    }

    /**
     * Disconnect from the server.
     */
    public void disconnect() {
        try {
            ftp.logout();

            if (ftp.isConnected()) {
                ftp.disconnect();
            }
        } catch (IOException f) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(f);
        }
    }
}
