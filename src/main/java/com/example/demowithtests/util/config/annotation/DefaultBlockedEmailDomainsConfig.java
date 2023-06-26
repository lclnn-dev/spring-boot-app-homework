package com.example.demowithtests.util.config.annotation;

import java.util.ArrayList;
import java.util.List;


public class DefaultBlockedEmailDomainsConfig {
    private static final List<String> defaultDomains = new ArrayList<>();

    private DefaultBlockedEmailDomainsConfig() {
    }

    static {
        defaultDomains.add("ru");
        defaultDomains.add("su");
        defaultDomains.add("yandex");
    }

    public static void addDefaultDomain(String domain) {
        defaultDomains.add(domain);
    }

    public static void removeDefaultDomain(String domain) {
        defaultDomains.remove(domain);
    }

    public static List<String> getDefaultDomains() {
        return defaultDomains;
    }
}
