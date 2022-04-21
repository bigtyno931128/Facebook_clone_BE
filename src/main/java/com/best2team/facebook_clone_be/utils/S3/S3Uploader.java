package com.best2team.facebook_clone_be.utils.S3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.best2team.facebook_clone_be.dto.ImageDto;
import com.best2team.facebook_clone_be.repository.PostImageRepository;
import com.best2team.facebook_clone_be.repository.UserImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {
    private final AmazonS3Client amazonS3Client;
    private final PostImageRepository postImageRepository;
    private final UserImageRepository userImageRepository;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket; // S3 버킷 이름

    public ImageDto upload(MultipartFile multipartFile, String dirName) throws IOException {
        System.out.println("=========");
        System.out.println(multipartFile);

        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        System.out.println(uploadFile);
        return upload(uploadFile, dirName);
    }

    //S3 삭제
    public void deleteUserImage(Long imageId){
        String fileName = userImageRepository.findById(imageId).orElseThrow(IllegalArgumentException::new).getFileName();

        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
        amazonS3Client.deleteObject(request);
    }

    public void deletePostImage(Long imageId){
        System.out.println("++++");
        String fileName = postImageRepository.findById(imageId).orElseThrow(IllegalArgumentException::new).getFileName();

        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
        amazonS3Client.deleteObject(request);
    }

    // S3로 파일 업로드하기
    private ImageDto upload(File uploadFile, String dirName) {
        System.out.println(dirName);
        System.out.println(uploadFile.getName());
        System.out.println("--------파일업로드");
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        System.out.println("==========s3파일 업로드하기======");
        System.out.println(fileName);
        System.out.println(uploadImageUrl);
        removeNewFile(uploadFile);
        return new ImageDto(uploadImageUrl, fileName);
    }
    // S3로 업로드

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
    // 로컬에 저장된 이미지 지우기

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }
    // 로컬에 파일 업로드 하기

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        System.out.println(convertFile);
        System.out.println("****************");
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }
    
}
