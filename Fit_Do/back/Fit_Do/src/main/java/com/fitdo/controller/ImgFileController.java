package com.fitdo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/goal")
@Tag(name = "ImgFileController", description = "이미지 파일 관리")
@CrossOrigin("*")
public class ImgFileController {
//
//	@PostMapping(value = "/{id}/uploadImage", consumes = "multipart/form-data")
//    @Operation(summary = "프로필 이미지 변경 api")
//    public ResponseEntity<UserResponseDto.ImageRet> uploadImage(@PathVariable Long id,@RequestPart MultipartFile image) throws IOException {
//       UserResponseDto.ImageRet ret = userService.updateImage(id, image);
//       return new ResponseEntity<>(ret,HttpStatus.OK);
//    }
}
