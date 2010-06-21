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