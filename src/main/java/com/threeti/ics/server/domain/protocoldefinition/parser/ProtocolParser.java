package com.threeti.ics.server.domain.protocoldefinition.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.threeti.ics.server.common.ObjectJsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 12/09/12
 * Time: 11:26
 * To change this template use File | Settings | File Templates.
 */
public class ProtocolParser {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolParser.class);
    private static final String TYPE_KEY_NAME = "type";
    private static final String DATA_KEY_NAME = "data";
    private final Map<String, Object> jsonObject;

    public ProtocolParser(final String jsonString) {
        try {
            jsonObject = MAPPER.readValue(jsonString, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException(jsonString);
        }
    }

    public String getKey(final String keyName) {
        return jsonObject.get(keyName).toString();
    }

    public String getType() {
        return getKey(TYPE_KEY_NAME);
    }

    public String getData() {
      return  ObjectJsonMapper.getJsonStringBy(jsonObject.get(DATA_KEY_NAME));
    }
}
