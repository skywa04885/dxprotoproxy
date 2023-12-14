package com.github.skywa04885.dxprotoproxy.server.http.client;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpClientPairParser {
    public static final Pattern KEY_VALUE_PAIR_PATTERN =
            Pattern.compile("^(?<Key>[a-zA-Z0-9_\\-]+): (?<Value>.+)$");


    public static HashMap<String, String> parseFromLines(final String[] lines) {
        final var result = new HashMap<String, String>();

        for (final var line : lines) {
            // Matches the line against the key/ value pair regular expression, if it does not match
            //  throw an exception.
            final Matcher matcher = KEY_VALUE_PAIR_PATTERN.matcher(line);
            if (!matcher.matches())
                throw new RuntimeException("Invalid key/ value pair");

            // Gets the key and the value from the matcher.
            final String key = matcher.group("Key");
            final String value = matcher.group("Value");

            // Inserts the key/ value pair into the result map.
            result.put(key, value);
        }

        return result;
    }
}
