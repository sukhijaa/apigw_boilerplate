package hyperdew.apigw.utilities;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public enum KnownMSNames {
    AUTHORIZATION_MS("auth-service"),
    DUMMY("dummy");

    private static Map<String, KnownMSNames> mapping = new HashMap<>();

    static {
        Arrays.stream(KnownMSNames.values()).forEach(msName -> mapping.put(msName.getValue(), msName));
    }

    @Getter
    private String value;

    KnownMSNames(String value) {
        this.value = value;
    }

    public static KnownMSNames getEnumForValue(String val) {
        return Optional.ofNullable(mapping.get(val)).orElse(KnownMSNames.DUMMY);
    }
}
