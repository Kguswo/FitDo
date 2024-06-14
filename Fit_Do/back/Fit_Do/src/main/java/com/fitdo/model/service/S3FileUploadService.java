//package com.fitdo.model.service;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.springframework.web.multipart.MultipartFile;
//
//import com.fitdo.model.dto.Post;
//import com.fitdo.model.dto.SearchCondition;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//public interface S3FileUploadService {
//	
//	/**
//     * S3에 파일을 업로드합니다.
//     *
//     * @param multipartFile 업로드할 파일
//     * @param dirName       S3 버킷 내의 디렉토리 이름
//     * @return 업로드된 파일의 S3 URL
//	 * @throws IOException 
//     * @throws IllegalArgumentException 파일 변환 중 오류가 발생한 경우
//     */
//    String upload(MultipartFile multipartFile, String dirName) throws IOException;
//
//    /**
//     * S3에서 파일을 삭제합니다.
//     *
//     * @param fileUrl 삭제할 파일의 S3 URL
//     */
//    void delete(String fileUrl);
//}
