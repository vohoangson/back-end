package com.japanwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.japanwork.constant.CommonConstant;
import com.japanwork.constant.UrlConstant;
import com.japanwork.service.FileHandlerService;

@RestController
public class FileHandlerController {

	@Autowired
    private FileHandlerService fileHandlerService;

    @PostMapping(UrlConstant.URL_AMW)
    public ResponseDataAPI create(@RequestPart(value = "file") MultipartFile file){
        String url = fileHandlerService.uploadFile(file);
        return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, url, null, null);
    }

    @DeleteMapping(UrlConstant.URL_AMW)
    public ResponseDataAPI destroy(@RequestParam("url") String fileUrl) {
    	fileHandlerService.deleteFileFromS3Bucket(fileUrl);
    	return new ResponseDataAPI(CommonConstant.ResponseDataAPIStatus.SUCCESS, null, null, null);
    }
}
