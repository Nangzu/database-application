package com.example.database.service;

import com.example.database.entity.Product;
import com.example.database.entity.User;
import com.example.database.entity.Wishlist;
import com.example.database.repository.ProductRepository;
import com.example.database.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRepository productRepository;

    private final EmailService emailService;

    public WishlistService(WishlistRepository wishlistRepository, ProductRepository productRepository,EmailService emailService) {
        this.wishlistRepository = wishlistRepository;
        this.productRepository = productRepository;
        this.emailService = emailService;

    }

    public List<Wishlist> getWishlist(User user) {
        // 위시리스트 목록을 가져온 후 상품 정보를 채워줌
        List<Wishlist> wishlist = wishlistRepository.findByUser(user);

        // 각 Wishlist 항목에 상품 정보 (title, lprice 등) 추가
        for (Wishlist item : wishlist) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            item.setProduct(product);  // 상품 정보를 Wishlist 객체에 설정
        }

        return wishlist;
    }

    public void addToWishlist(User user, Long productId) {
        // 상품과 사용자를 먼저 찾기
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        // 중복 검사
        boolean exists = wishlistRepository.existsByUserAndProductId(user, productId);
        if (exists) {
            throw new RuntimeException("Product is already in the wishlist");
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        wishlistRepository.save(wishlist);
    }

    public void removeFromWishlist(User user, Long productId) {
        wishlistRepository.deleteByUserAndProductId(user, productId);
    }


    public void updateDesiredPrice(User user, Long productId, Integer desiredPrice) {
        Wishlist wishlistItem = wishlistRepository.findByUserAndProductId(user, productId)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));
        wishlistItem.setDesiredPrice(desiredPrice);
        wishlistRepository.save(wishlistItem);
    }
    @Transactional
    public void checkAndSendEmailAlerts(Wishlist wishlist, int currentPrice) {
        if (wishlist.getDesiredPrice() != null && currentPrice <= wishlist.getDesiredPrice()) {
            String userEmail = wishlist.getUser().getEmail();
            String subject = "가격 알림: " + wishlist.getProduct().getTitle();
            String body = "안녕하세요, " + wishlist.getUser().getUsername() + "님.\n" +
                    "상품 '" + wishlist.getProduct().getTitle() +
                    "'의 가격이 희망 가격 이하로 내려갔습니다.\n현재 가격: " + currentPrice + "원";
            emailService.sendEmail(userEmail, subject, body);
        }
    }

}
