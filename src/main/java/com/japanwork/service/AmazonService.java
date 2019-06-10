package com.japanwork.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.japanwork.constant.MessageConstant;
import com.japanwork.payload.response.BaseDataResponse;
import com.japanwork.payload.response.BaseMessageResponse;

@Component
public class AmazonService {

	private String awsS3AudioBucket;
    private AmazonS3 amazonS3;
	
    @Value("${amazonProperties.url}")
    private String endpointUrl;

    @Autowired
    public AmazonService(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3AudioBucket) 
    {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
        this.awsS3AudioBucket = awsS3AudioBucket;
    }
    
    public BaseDataResponse uploadFile(MultipartFile multipartFile) {
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + this.awsS3AudioBucket + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
           e.printStackTrace();
        }
        BaseDataResponse response = new BaseDataResponse(fileUrl);
        return response;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private PutObjectResult uploadFileTos3bucket(String fileName, File file) {
    	return amazonS3.putObject(new PutObjectRequest(this.awsS3AudioBucket, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public BaseDataResponse deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        amazonS3.deleteObject(new DeleteObjectRequest(this.awsS3AudioBucket, fileName));
        BaseMessageResponse baseMessageResponse = new BaseMessageResponse(MessageConstant.DELETE_FILE_AWS, MessageConstant.DEL_SUCCESS);
        BaseDataResponse response = new BaseDataResponse(baseMessageResponse);
        return response;
    }

}