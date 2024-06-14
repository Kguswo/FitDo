package com.fitdo.model.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3FileUploadServiceImpl {
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final AmazonS3 s3Client;
    
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public S3FileUploadServiceImpl(AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucket}") String bucketName) {
        this.s3Client = amazonS3;
        this.bucketName = bucketName;
    }

    private final String defaultUrl = "https://fit-do.s3.ap-southeast-2.amazonaws.com";

    public String uploadFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
//        String encodedFileName = encodeFileName(originalFileName);
        
        try {
            s3Client.putObject(bucketName, originalFileName, file.getInputStream(), getObjectMetadata(file));
            return defaultUrl  + "/" + originalFileName;
        } catch (SdkClientException e) {
            throw new IOException("Error uploading file to S3", e);
        }
    }

    private ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        return objectMetadata;
    }

    private String generateFileName(MultipartFile file) {
//        return UUID.randomUUID().toString() + "-" + file.getOriginalFilename(); // 중복안되게 랜덤하게 넣으려면 이렇게 그때그때 UUID붙여서
        return file.getOriginalFilename();
    }

    
    // 파일 이름을 UTF-8로 인코딩
    public static String encodeFileName(String fileName) {
        try {
        	System.out.println("인코딩 해볼게요!  " + URLEncoder.encode(fileName, "UTF-8"));
            return URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return fileName; // 인코딩 실패 시 원래 파일 이름 리턴
        }
    }
}
