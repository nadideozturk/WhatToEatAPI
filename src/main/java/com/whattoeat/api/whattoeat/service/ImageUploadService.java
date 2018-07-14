package com.whattoeat.api.whattoeat.service;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import com.cloudinary.Cloudinary;

import java.io.IOException;
import java.util.Map;


@Service
public class ImageUploadService {

    private Cloudinary cloudinary;

    public ImageUploadService() {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dv0qmj6vt",
                "api_key", "752346693282248",
                "api_secret", "6teLVFpaSPGGoY4-NZeqLjDSjsk"));
    }

    public String uploadImage(String imageId, String imageContent, String folderName){
        Map options = ObjectUtils.asMap(
                "folder", folderName,
                "public_id", imageId);
        Map result;

        try {
            result = cloudinary.uploader().upload(imageContent, options);
        } catch (IOException e) {
            // todo log to file
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result.get("secure_url").toString();
    }
}
