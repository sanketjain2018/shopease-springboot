package in.sj.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
	 private final Cloudinary cloudinary;

	    public String uploadFile(MultipartFile file) {
	        try {
	            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of());
	            return uploadResult.get("secure_url").toString(); // HTTPS URL
	        } catch (Exception e) {
	            throw new RuntimeException("Image upload failed", e);
	        }
	    }
}
