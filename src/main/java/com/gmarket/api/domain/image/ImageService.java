package com.gmarket.api.domain.image;

import com.gmarket.api.global.exception.EntityNotFoundException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class ImageService {

    public String create(MultipartFile file) {

        Date date = new Date();
        StringBuilder sb = new StringBuilder();

        // 이미지 이름과 시간을 합쳐서 중복 불가능한 이미지 리소스 주소 문자열 생성
        if(file.isEmpty()) {
            throw new EntityNotFoundException("이미지가 없습니다.");
        } else {
            sb.append(date.getTime());
            sb.append(file.getOriginalFilename());
        }

//        리눅스 클라우드용
        File dest = new File("/root/images/" + sb.toString());
//        윈도우 테스트
//        File dest = new File("C://images/" + sb.toString());

        // 해당 위치에 폴더가 없을 경우
        //if(!dest.exists()) {
        //    dest.mkdir();
        //}

        try {
            file.transferTo(dest);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public byte[] getImage(String imageName) throws Exception {

//        리눅스 클라우드용
        InputStream imageStream = new FileInputStream("/root/images/" + imageName);
//        윈도우 테스트
//        InputStream imageStream = new FileInputStream("C://images/" + imageName);
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();
        return imageByteArray;
    }
}
