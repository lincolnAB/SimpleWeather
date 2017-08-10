package com.ormanin.simpleweather.simpleweather.Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by patrykormanin on 13/07/2017.
 */

public class EpochConverter {

    public static String toHH(int epoch) {
        return new SimpleDateFormat("H'h'").format(new Date((epoch + GlobalInfo.REFERENCE_TIMESTAMP) * 1000));
    }

}
