//package com.fitdo.model.dto;
//
//import lombok.Builder;
//import lombok.Getter;
//
//@Getter
//@Builder
//public class UserResponseDto {
//
//    public static class ImageRet {
//        private String imageUrl;
//
//        public ImageRet() {}
//
//        public ImageRet(String imageUrl) {
//            this.imageUrl = imageUrl;
//        }
//
//        public String getImageUrl() {
//            return imageUrl;
//        }
//
//        public void setImageUrl(String imageUrl) {
//            this.imageUrl = imageUrl;
//        }
//
//        public static ImageRetBuilder builder() {
//            return new ImageRetBuilder();
//        }
//
//        public static class ImageRetBuilder {
//            private String imageUrl;
//
//            public ImageRetBuilder() {}
//
//            public ImageRetBuilder imageUrl(String imageUrl) {
//                this.imageUrl = imageUrl;
//                return this;
//            }
//
//            public ImageRet build() {
//                return new ImageRet(imageUrl);
//            }
//        }
//    }
//}
