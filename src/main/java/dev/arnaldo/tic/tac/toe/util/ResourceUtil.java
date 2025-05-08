package dev.arnaldo.tic.tac.toe.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.io.InputStreamReader;

@UtilityClass
public class ResourceUtil {

    public InputStream getInputStream(@NonNull String resourceName) {
        return ResourceUtil.class.getResourceAsStream(resourceName);
    }

    public InputStreamReader getInputStreamReader(@NonNull String resourceName) {
        return new InputStreamReader(getInputStream(resourceName));
    }

}
