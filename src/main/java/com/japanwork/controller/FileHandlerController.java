package com.japanwork.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.japanwork.constant.UrlConstant;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.service.AmazonService;

@RestController
public class FileHandlerController {

	@Autowired
    private AmazonService amazonService;

    @PostMapping(UrlConstant.URL_AMW_UPLOAD_FILE)
    public BaseDataResponse uploadFile(@RequestPart(value = "file") MultipartFile file, HttpServletResponse httpServletResponse){
        return this.amazonService.uploadFile(file, httpServletResponse);
    }

    @DeleteMapping(UrlConstant.URL_AMW_DELETE_FILE)
    public BaseDataResponse deleteFile(@RequestParam("url") String fileUrl, HttpServletResponse httpServletResponse) {
        return this.amazonService.deleteFileFromS3Bucket(fileUrl, httpServletResponse);
    }
}
