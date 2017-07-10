package com.ormanin.simpleweather.simpleweather.Model.Networking;

import com.ormanin.simpleweather.simpleweather.Exceptions.NotImplementedException;

import java.util.List;
import java.util.Random;

/**
 * Created by patrykormanin on 07/07/2017.
 */

public class PhotoService {

    private String apiUrl;

    public PhotoService(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getRandomPhotoUrl() {
        Random rand = new Random();
        return apiUrl+Integer.toString(rand.nextInt(16));
    }

    public List<String> getAllPhotosUrls() throws NotImplementedException {
        throw new NotImplementedException();
    }
}
