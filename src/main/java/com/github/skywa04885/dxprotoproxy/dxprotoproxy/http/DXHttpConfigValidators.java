package com.github.skywa04885.dxprotoproxy.dxprotoproxy.http;

import java.util.regex.Pattern;

public class DXHttpConfigValidators {
    public static Pattern namePattern = Pattern.compile("^[a-zA-Z0-9_]{1,64}$");
    public static Pattern httpVersionPattern = Pattern.compile("^http/([0-9]+)(\\.[0-9]+)?$", Pattern.CASE_INSENSITIVE);
    public static Pattern protocolPattern = Pattern.compile("^(http|https)$", Pattern.CASE_INSENSITIVE);
    public static Pattern hostPattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
            "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");

    public static boolean isNameValid(String name) {
        return namePattern.matcher(name).matches();
    }

    public static boolean isHttpVersionValid(String httpVersion) {
        return httpVersionPattern.matcher(httpVersion).matches();
    }

    public static boolean isProtocolValid(String protocol) {
        return protocolPattern.matcher(protocol).matches();
    }

    public static boolean isValidHost(String host) {
        return hostPattern.matcher(host).matches();
    }
}
