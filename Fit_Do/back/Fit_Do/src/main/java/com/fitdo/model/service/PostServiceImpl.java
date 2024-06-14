package com.fitdo.model.service;

import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import com.fitdo.model.dao.PostDao;
import com.fitdo.model.dto.Post;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class PostServiceImpl implements PostService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final PostDao postDao;

	@Autowired
	ResourceLoader resLoader;

	@Autowired
	public PostServiceImpl(PostDao postDao) {
		super();
		this.postDao = postDao;
	}

	@Override
	public List<Post> getPost() {
		return postDao.getPost();
	}
	
	@Override
	public List<Post> getPostWithUserProfileImg() {
		return postDao.getPostWithUserProfileImg();
	}

	@Override
	public Post selectOnePost(int postId) {
		return postDao.selectOnePost(postId);
	}
	
	@Override
	public List<Post> selectPostsByUserId(String followeeId) {
		return postDao.selectPostsByUserId(followeeId);
	}

	@Override
	public int createPost(Post post, HttpServletRequest request) throws IOException {
//		fileHandling(post, file);
		HttpSession session = request.getSession();
//		User user = (User) session.getAttribute("user");
//		post.setPostUserId(user.getUserid());
		return postDao.createPost(post);
	}

	@Override
	public int updatePost(Post post) {
		return postDao.updatePost(post);
	}

	@Override
	public int updatePostLike(int postId, int postLike) {
        Post post = postDao.selectOnePost(postId);
        if (post != null) {
            post.setPostLike(postLike);
            return postDao.updatePost(post);
        }
        return 0;
	}
	
	@Override
	public int updatePostImageUrl(int postId, String postImg) {
		Post post = postDao.selectOnePost(postId);
		if (post != null) {
			post.setPostImg(postImg);
			postDao.updatePost(post);
		}
		return 0;
	}

	@Override
	public int deletePost(int postId) {
		return postDao.deletePost(postId);
	}

	@Override
	public List<Post> selectPostlistByUserId(String postUserId) {
		return postDao.selectPostlistByUserId(postUserId);
	}

//	private void fileHandling(Post post, MultipartFile file) throws IOException {
//		
//		if (post.getPostId() == -1) {
//	        throw new IllegalArgumentException("유효한 게시물을 설정해야 합니다.");
//	    }		
//		
//		// 파일을 저장할 폴더 지정
//		Resource res = resLoader.getResource("classpath:/upload");
//		logger.debug("res: {}", res.getFile().getCanonicalPath());
//		if (file != null && file.getSize() > 0) {
//			// prefix를 포함한 전체 이름
//			post.setPostImg(System.currentTimeMillis() + "_" + file.getOriginalFilename());
//			post.setOrgpostImg(file.getOriginalFilename());
//
//			// 변경된 파일 이름이 적용된 Movie MovieService를 통해 DB에 저장한다.
//
//			file.transferTo(new File(res.getFile().getCanonicalPath() + "/" + post.getPostImg()));
//			System.out.println(post.getOrgpostImg());
//		}
//
//	}

}
