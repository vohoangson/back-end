package com.japanwork.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
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
import com.japanwork.exception.BadRequestException;

@Component
public class FileHandlerService {

	private String awsS3AudioBucket;
    private AmazonS3 amazonS3;
	
    @Value("${amazonProperties.url}")
    private String endpointUrl;

    @Autowired
    public FileHandlerService(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider, String awsS3AudioBucket) 
    {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion.getName()).build();
        this.awsS3AudioBucket = awsS3AudioBucket;
    }
    
    public String uploadFile(MultipartFile multipartFile) 
    		throws BadRequestException{
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            this.checkSizeFile(file);
            
            String mimeType = Files.probeContentType(file.toPath());
            if(mimeType.startsWith("image", 0)) {
            	String fileName = generateFileName(multipartFile);
                fileUrl = endpointUrl + "/" + this.awsS3AudioBucket + "/" + fileName;
                uploadFileTos3bucket(fileName, file);
            } else {
            	file.delete();
            	throw new BadRequestException(MessageConstant.FILE_NOT_FORMATED);
            }
        } catch(BadRequestException ex) {
        	throw ex;
        } catch (Exception e) {
           e.printStackTrace();
        }
        
        return fileUrl;
    }

    public String uploadFileInMessage(MultipartFile multipartFile) 
    		throws BadRequestException{
        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            this.checkSizeFile(file);
            
            String mimeType = Files.probeContentType(file.toPath()); 
            if(mimeType.equals("application/pdf") || mimeType.equals("application/msword") 
            		|| mimeType.startsWith("image", 0) || mimeType.equals("text/plain")) {
            	String fileName = generateFileName(multipartFile);
                fileUrl = endpointUrl + "/" + this.awsS3AudioBucket + "/" + fileName;
                uploadFileTos3bucket(fileName, file);
            } else {
            	file.delete();
            	throw new BadRequestException(MessageConstant.FILE_NOT_FORMATED);
            }
        } catch(BadRequestException ex) {
        	throw ex;
        } catch (Exception e) {
           e.printStackTrace();
        }
        
        return fileUrl;
    }
    
    private void checkSizeFile(File file) {
    	long size;
		try {
			size = Files.size(file.toPath());
			if(size > 5242880) {
	        	file.delete();
	        	throw new BadRequestException(MessageConstant.MAXIMUM_UPLOAD_SIZE_EXCEEDED);
	        }
		} catch(BadRequestException ex) {
        	throw ex;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

    public void deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        amazonS3.deleteObject(new DeleteObjectRequest(this.awsS3AudioBucket, fileName));
    }
}