package in.sj.globalexception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileUploadExceptionHandler {
	@ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(
            MaxUploadSizeExceededException ex,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(
                "error", "File size too large! Max allowed size is 5MB");

        return "redirect:/products/add"; // 
    }
}
