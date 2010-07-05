package org.jtheque.films.services.impl.utils.file;

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
 * A bean to contain the informations of a FTP Connection.
 *
 * @author Baptiste Wicht
 */
public final class FTPConnectionInfos {
    private String host;
    private String path;
    private String user;
    private String password;
    private int port;
    private boolean passive;

    /**
     * Return the host name.
     *
     * @return the server's host name.
     */
    public String getHost() {
        return host;
    }

    /**
     * Set the host name of the server.
     *
     * @param host The host name of the server.
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Return the path on the server.
     *
     * @return The directory to store the file in.
     */
    public String getPath() {
        return path;
    }

    /**
     * Set the path on the server to store in.
     *
     * @param path The path on the server to store in.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Return the user name.
     *
     * @return the user name.
     */
    public String getUser() {
        return user;
    }

    /**
     * Set the user name.
     *
     * @param user The user name.
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Return the password.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password.
     *
     * @param password The password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the port.
     *
     * @return The port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the port.
     *
     * @param port The port.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Indicate if we use passive mode or not.
     *
     * @return true if we use passive mode else false.
     */
    public boolean isPassive() {
        return passive;
    }

    /**
     * Set if we must use passive mode or not.
     *
     * @param passive A boolean indicating if we must use the passive mode or not.
     */
    public void setPassive(boolean passive) {
        this.passive = passive;
    }
}
