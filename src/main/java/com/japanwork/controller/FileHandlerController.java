package com.japanwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.AmazonService;

@RestController
public class FileHandlerController {

	@Autowired
    private AmazonService amazonService;

    @PostMapping(UrlConstant.URL_AMW_UPLOAD_FILE)
    public BaseDataResponse uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonService.uploadFile(file);
    }

    @DeleteMapping(UrlConstant.URL_AMW_DELETE_FILE)
    public BaseDataResponse deleteFile(@RequestParam("url") String fileUrl) {
        return this.amazonService.deleteFileFromS3Bucket(fileUrl);
    }
}
