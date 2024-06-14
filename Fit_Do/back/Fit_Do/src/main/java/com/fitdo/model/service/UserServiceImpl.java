package com.fitdo.model.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fitdo.model.dao.UserDao;
import com.fitdo.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final UserDao userDao;
    private final S3FileUploadServiceImpl s3FileUploadService;
    
	@Autowired
	ResourceLoader resLoader;

	@Autowired
	public UserServiceImpl(UserDao userDao, S3FileUploadServiceImpl s3FileUploadService) {
		super();
		this.userDao = userDao;
		this.s3FileUploadService = s3FileUploadService;
	}

	@Override
	public int createUser(User user) throws IOException {
//		fileHandling(user, file);
		return userDao.createUser(user);
	}

	@Override
	public User login(String userid, String password) {
		return userDao.login(userid, password);
	}

	@Override
	public List<User> searchById(String userid) {
		List<User> user = userDao.searchById(userid);
		if(user != null) return user;
		else {
			System.out.println("일치하는 회원이 없습니다.");
		}
		return user;
	}
	
	@Override
	public List<User> searchUsersByIdToFollow(String id) {
		return userDao.searchUsersByIdToFollow(id);
	}
	
	@Override
	public List<User> findUsersWithSameMbtiNotFollowing(String fromUserId, String mbti) {
		Map<String, String> recommendationKey = Map.of("fromUserId", fromUserId, "mbti", mbti);
		return userDao.findUsersWithSameMbtiNotFollowing(recommendationKey);
	}
	
	@Override
	public int updateProfileImageUrl(String userid, String userProfileImg) {
		User user = userDao.searchById(userid).get(0);
		if(user != null) {
			user.setProfileImg(userProfileImg);
			userDao.updateUser(user);
		}
		return 0;
	}

	@Override
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public int deleteUser(String userid) {
		return userDao.deleteUser(userid);
	}

	// 개수 세고 회원 이미 존재하면 true반환하게 만듬
	@Override
	public boolean isExistingUser(String userid) {
	    int count = userDao.isExistingUser(userid);
	    if (count > 0) {
	        System.out.println("중복회원 있음");
	        return true;
	    } else {
	        System.out.println("중복회원 없음");
	        return false;
	    }
	}

//	// 회원가입 + 프사
//	@Override
//	public int createUserWithProfileImg(User user, MultipartFile file) throws IOException {
//	    if (file != null && !file.isEmpty()) {
//	        Optional<File> convertedFile = s3FileUploadService.convert(file);
//	        if (convertedFile.isPresent()) {
//	            String imageUrl = s3FileUploadService.upload(convertedFile.get(), "UserProfileImage");
//	            user.setProfileImg(imageUrl);
//	            user.setOrgprofileImg(file.getOriginalFilename());
//
//	            // 업로드 후에 임시 파일 삭제
//	            convertedFile.get().delete();
//	        } else {
//	            throw new IOException("MultipartFile을 File로 변환하는데 실패했습니다.");
//	        }
//	    }
//	    return userDao.createUser(user);
//	}
	
	//파일관련
	private void fileHandling(User user, MultipartFile file) throws IOException {
		Resource res = resLoader.getResource("resources/upload");
		logger.debug("res: {}", res.getFile().getCanonicalPath());
		if(file != null && file.getSize() > 0) {
			user.setProfileImg(System.currentTimeMillis() + "_" + file.getOriginalFilename());
			user.setOrgprofileImg(file.getOriginalFilename());
			
			file.transferTo(new File(res.getFile().getCanonicalPath() + "/" + user.getProfileImg()));
			System.out.println("OrgprofileImg : " + user.getOrgprofileImg());
		}
	}

//	@Override
//	public UserResponseDto.ImageRet updateImage(String userid, MultipartFile image) throws IOException {
//	    List<User> users = userDao.searchById(userid);
//	    if (users.isEmpty()) {
//	        throw new IllegalArgumentException("해당 사용자 없음");
//	    }
//	    User user = users.get(0);
//	    String imageUrl = s3FileUploadService.upload(image, "UserProfileImage");
//	    user.setProfileImg(imageUrl);
//	    userDao.updateUser(user);
//
//	    return UserResponseDto.ImageRet.builder().imageUrl(imageUrl).build();
//	}

}
