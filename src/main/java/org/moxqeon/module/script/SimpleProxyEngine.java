package org.moxqeon.module.script;

import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public final class SimpleProxyEngine extends ProxyEngine {
    public void importClass(@NotNull File yamlFile) throws ClassNotFoundException {
        if (yamlFile.exists()) {
            try (InputStream input = new FileInputStream(yamlFile)) {
                Map<String, String> map = new Yaml().load(input);
                importClass(map);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
