package com.pulbatte.pulbatte.global;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
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
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;
    @Value("${cloud.aws.s3.bucket.url}")
    private String bucketUrl;
    @Value("${cloud.aws.cloud_front.file_url_format}")
    private String CLOUD_FRONT_DOMAIN_NAME;

    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("파일 전환 실패"));
        return upload(uploadFile, dirName);
    }

    // S3로 파일 업로드하기
    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String imagePath = CLOUD_FRONT_DOMAIN_NAME + "/" + fileName;
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
//        log.info(uploadImageUrl);
        removeNewFile(uploadFile);
        return imagePath;
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

    private Optional<File> convert(MultipartFile multipartFile) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());
        // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    //    public String delete(String fileName, String dirName) {
//        log.info(fileName);
//        String
//        log.info(dirName);
//        return amazonS3Client.getUrl(bucket, fileName).toString();
//    }
    public void delete(String key, String dirName) {
        String[] imageName = key.split(dirName);
        key = dirName + imageName[1];
//        log.info(String.valueOf(deleteObjectRequest));
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
//        try {
//             = new DeleteObjectRequest(this.bucket, key);
//            this.amazonS3Client.deleteObject(deleteObjectRequest);
//
//        } catch (SdkClientException e) {
//            throw new CustomException(ErrorCode.DELETE_IMAGE_FAILED);
//        }
    }

}