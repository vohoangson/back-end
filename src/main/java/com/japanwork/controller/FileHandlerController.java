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
import com.japanwork.payload.response.BaseMessageResponse;
import com.japanwork.service.AmazonService;

@RestController
public class FileHandlerController {

	@Autowired
    private AmazonService amazonService;

    @PostMapping(UrlConstant.URL_AMW)
    public BaseDataResponse uploadFile(@RequestPart(value = "file") MultipartFile file, HttpServletResponse httpServletResponse){
        String url = amazonService.uploadFile(file, httpServletResponse);
        return new BaseDataResponse(url);
    }

    @DeleteMapping(UrlConstant.URL_AMW)
    public BaseDataResponse deleteFile(@RequestParam("url") String fileUrl, HttpServletResponse httpServletResponse) {
    	BaseMessageResponse baseMessageResponse = amazonService.deleteFileFromS3Bucket(fileUrl, httpServletResponse);
    	return new BaseDataResponse(baseMessageResponse);
    }
}
