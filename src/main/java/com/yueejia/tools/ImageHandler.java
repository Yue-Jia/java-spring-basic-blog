package com.yueejia.tools;

import org.springframework.security.core.Authentication;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

public class ImageHandler {
    public static void removeOldImage(String oldFileUrl) {
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            String oldFilePathString = path.getAbsolutePath()+ "\\static\\" + oldFileUrl.replace("/","\\");
            File oldFile = new File(oldFilePathString);
            oldFile.delete();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static String imageUploader(MultipartFile file, Authentication auth) throws IOException {

                File path = new File(ResourceUtils.getURL("classpath:").getPath());
                if(!path.exists()) {
                    path = new File("");
                }
                System.out.println(path.getAbsolutePath());
                File upload = new File(path.getAbsolutePath(),"static/images/upload/" + auth.getName() + "/");
                if(!upload.exists()){
                    upload.mkdirs();
                }
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String filename = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + "_" +new Date().getTime() + suffix;
                File serverFile = new File(upload.getAbsolutePath()+ "/" + filename);
                file.transferTo(serverFile);
                return "images/upload/"+auth.getName()+"/"+filename;
    }

    public static boolean isImageSupported(String type) {
        String[] typeStrings = {"image/apng", "image/bmp", "image/gif", "image/x-icon", "image/jpeg", "image/png", "image/svg+xml", "image/tiff", "image/webp"};
        for(String t:typeStrings) {
            if(t.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
}
