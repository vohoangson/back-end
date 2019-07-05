package com.japanwork.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

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
import com.japanwork.exception.BadRequestException;
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
    
    public String uploadFile(MultipartFile multipartFile,HttpServletResponse httpServletResponse) 
    		throws BadRequestException{
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            
            String mimeType = Files.probeContentType(file.toPath());
            
            if(mimeType.startsWith("image", 0)) {
            	String fileName = generateFileName(multipartFile);
                fileUrl = endpointUrl + "/" + this.awsS3AudioBucket + "/" + fileName;
                uploadFileTos3bucket(fileName, file);
            } else {
            	file.delete();
            	throw new BadRequestException("The file is not properly formatted!");
            }
            file.delete();
        } catch(BadRequestException ex) {
        	throw ex;
        } catch (Exception e) {
           e.printStackTrace();
        }
        
        return fileUrl;
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

    public BaseMessageResponse deleteFileFromS3Bucket(String fileUrl, HttpServletResponse httpServletResponse) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        amazonS3.deleteObject(new DeleteObjectRequest(this.awsS3AudioBucket, fileName));
        BaseMessageResponse baseMessageResponse = new BaseMessageResponse(MessageConstant.DELETE_FILE_AWS, MessageConstant.DEL_SUCCESS);
        return baseMessageResponse;
    }

}