package hyperdew.authservice.utils;

import java.util.Calendar;
import java.util.Date;

public class Constants {


    public static final Date GUEST_USER_EXPIRY = getFutureDate();

    private static Date getFutureDate() {
        Calendar futureDate = Calendar.getInstance();
        futureDate.set(2099, Calendar.DECEMBER, 31);
        return futureDate.getTime();
    }

    public static final String GUEST_USER_NAME = "guest";

    public static final String ADMIN_USER_NAME = "hyperdewAdmin";
    public static final String ADMIN_USER_PASSWORD = "hyperdewAdminPassword";
}
