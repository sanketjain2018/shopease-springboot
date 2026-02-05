package in.sj.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import in.sj.entity.Product;
import in.sj.service.CloudinaryService;
import in.sj.service.ProductService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private static final Logger log =
            LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final CloudinaryService cloudinaryService;


    // ================= LIST + SEARCH + SORT + PAGINATION (ADMIN) =================
    @GetMapping("/products-ui")
    public String productsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String keyword,
            Model model) {

        log.info("ADMIN PRODUCTS PAGE | page={} | size={} | sortBy={} | keyword={}",
                page, size, sortBy, keyword);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Product> productPage;

        if (keyword != null && !keyword.isBlank()) {
            log.debug("SEARCH PRODUCTS | keyword={}", keyword);
            productPage = productService.searchProducts(keyword, pageable);
        } else {
            productPage = productService.getAllProducts(pageable);
        }

        log.debug("PRODUCTS FETCHED | count={}", productPage.getNumberOfElements());

        model.addAttribute("products", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("keyword", keyword);
        model.addAttribute("size", size);

        return "products";
    }

    // ================= ADD PRODUCT PAGE =================
    @GetMapping("/products-ui/add")
    public String addProduct(Model model) {

        log.info("ADD PRODUCT PAGE REQUESTED");

        model.addAttribute("product", new Product());
        return "add-product";
    }

    // ================= SAVE PRODUCT WITH IMAGE =================
	/*
	 * @PostMapping("/products-ui/save") public String saveProduct(
	 * 
	 * @ModelAttribute Product product,
	 * 
	 * @RequestParam("image") MultipartFile imageFile ) throws IOException {
	 * 
	 * log.info("SAVE PRODUCT REQUEST | name={} | price={}", product.getName(),
	 * product.getPrice());
	 * 
	 * if (imageFile == null || imageFile.isEmpty()) {
	 * log.warn("PRODUCT SAVE FAILED | image missing | name={}", product.getName());
	 * throw new RuntimeException("Product image is required"); }
	 * 
	 * String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
	 * Path uploadPath = Paths.get("uploads/products");
	 * 
	 * Files.createDirectories(uploadPath); Files.copy( imageFile.getInputStream(),
	 * uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING );
	 * 
	 * product.setImageUrl(fileName); productService.save(product);
	 * 
	 * log.info("PRODUCT SAVED SUCCESSFULLY | id={} | name={}", product.getId(),
	 * product.getName());
	 * 
	 * return "redirect:/products-ui"; }
	 */
    
 // ================= SAVE PRODUCT WITH IMAGE (CLOUDINARY) =================
    @PostMapping("/products-ui/save")
    public String saveProduct(
            @ModelAttribute Product product,
            @RequestParam("image") MultipartFile imageFile
    ) {

        log.info("SAVE PRODUCT REQUEST | name={} | price={}",
                product.getName(), product.getPrice());

        if (imageFile == null || imageFile.isEmpty()) {
            log.warn("PRODUCT SAVE FAILED | image missing | name={}", product.getName());
            throw new RuntimeException("Product image is required");
        }

        // Upload to Cloudinary
        String imageUrl = cloudinaryService.uploadFile(imageFile);

        // Save URL in DB
        product.setImageUrl(imageUrl);
        productService.save(product);

        log.info("PRODUCT SAVED SUCCESSFULLY | id={} | name={}",
                product.getId(), product.getName());

        return "redirect:/products-ui";
    }

    
    

    // ================= EDIT PRODUCT PAGE =================
    @GetMapping("/products-ui/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {

        log.info("EDIT PRODUCT PAGE | productId={}", id);

        model.addAttribute("product", productService.getProductById(id));
        return "edit-product";
    }

    // ================= UPDATE PRODUCT =================
    @PostMapping("/products-ui/update")
    public String updateProduct(@ModelAttribute Product product) {

        log.info("UPDATE PRODUCT REQUEST | productId={}", product.getId());

        Product existingProduct = productService.getProductById(product.getId());
        product.setImageUrl(existingProduct.getImageUrl());

        productService.save(product);

        log.info("PRODUCT UPDATED | productId={}", product.getId());

        return "redirect:/products-ui";
    }

    // ================= DELETE PRODUCT =================
    @PostMapping("/products-ui/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {

        log.warn("DELETE PRODUCT REQUEST | productId={}", id);

        productService.deleteProduct(id);

        log.info("PRODUCT DELETED | productId={}", id);

        return "redirect:/products-ui";
    }

    // ================= SHOP PAGE (USER) =================
    @GetMapping("/shop")
    public String shopPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            Model model) {

        if (!List.of("id", "name", "price").contains(sortBy)) {
            log.warn("INVALID SORT FIELD | sortBy={} | defaulting to id", sortBy);
            sortBy = "id";
        }

        log.info("SHOP PAGE | page={} | size={} | sortBy={}", page, size, sortBy);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Product> productPage = productService.getAllProducts(pageable);

        log.debug("SHOP PRODUCTS FETCHED | count={}",
                productPage.getNumberOfElements());

        model.addAttribute("products", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);

        return "shop";
    }
}
