/*
 * Copyright 2015-2018 Jeeva Kandasamy (jkandasa@gmail.com)
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mycontroller.standalone.externalserver.config;

import java.util.HashMap;

import org.mycontroller.restclient.core.TRUST_HOST_TYPE;
import org.mycontroller.standalone.db.tables.ExternalServerTable;
import org.mycontroller.standalone.utils.McUtils;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Jeeva Kandasamy (jkandasa)
 * @since 0.0.3
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = { "password" })
@NoArgsConstructor
public class ExternalServerConfigInfluxDB extends ExternalServerConfig {

    public static final String KEY_URL = "url";
    public static final String KEY_TRUST_HOST_TYPE = "trustHostType";
    public static final String KEY_DATABASE = "database";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_TAGS = "tags";

    private String url;
    private TRUST_HOST_TYPE trustHostType;
    private String database;
    private String username;
    private String password;
    private String tags;

    public ExternalServerConfigInfluxDB(ExternalServerTable externalServerTable) {
        this.update(externalServerTable);
    }

    @Override
    public void update(ExternalServerTable externalServerTable) {
        super.update(externalServerTable);
        url = (String) externalServerTable.getProperties().get(KEY_URL);
        trustHostType = TRUST_HOST_TYPE.fromString((String) externalServerTable.getProperties().get(
                KEY_TRUST_HOST_TYPE));
        database = (String) externalServerTable.getProperties().get(KEY_DATABASE);
        username = (String) externalServerTable.getProperties().get(KEY_USERNAME);
        password = (String) externalServerTable.getProperties().get(KEY_PASSWORD);
        tags = (String) externalServerTable.getProperties().get(KEY_TAGS);

    }

    @Override
    @JsonIgnore
    public ExternalServerTable getExternalServerTable() {
        ExternalServerTable externalServerTable = super.getExternalServerTable();
        HashMap<String, Object> properties = getProperties();
        properties.put(KEY_URL, url);
        properties.put(KEY_TRUST_HOST_TYPE, trustHostType.getText());
        properties.put(KEY_DATABASE, database);
        properties.put(KEY_USERNAME, username);
        properties.put(KEY_PASSWORD, password);
        properties.put(KEY_TAGS, tags);
        externalServerTable.setProperties(properties);
        return externalServerTable;
    }

    @Override
    public String getServerDetail() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("URL: ").append(getUrl())
                .append(", Database: ").append(getDatabase())
                .append(", Username: ").append(McUtils.getString(getUsername()))
                .append(", Tags: ").append(McUtils.getString(getTags()))
                .append(", TrustHost: ").append(getTrustHostType().getText());
        return stringBuilder.toString();
    }

    @JsonGetter("trustHostType")
    private String getTrustHost() {
        return getTrustHostType().getText();
    }

}
