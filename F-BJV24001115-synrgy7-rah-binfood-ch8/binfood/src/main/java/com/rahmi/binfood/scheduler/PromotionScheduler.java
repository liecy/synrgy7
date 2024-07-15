package com.rahmi.binfood.scheduler;

import com.rahmi.binfood.fcm.dto.NotificationRequest;
import com.rahmi.binfood.fcm.service.FCMService;
import com.rahmi.binfood.model.Product;
import com.rahmi.binfood.service.ProductService;
import com.rahmi.binfood.service.PromotionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class PromotionScheduler {
    private final ProductService productService;
    private final PromotionService promotionService;
    private final FCMService fcmService;

    public PromotionScheduler(ProductService productService, PromotionService promotionService, FCMService fcmService) {
        this.productService = productService;
        this.promotionService = promotionService;
        this.fcmService = fcmService;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void cronJob() throws ExecutionException, InterruptedException {
        List<Product> productPromoList = productService.getAll();
        NotificationRequest request = new NotificationRequest();
        request.setTitle("Promo Siang Binarfud");
        request.setBody("Silakan order pada Pukul 12.00 - 13.00 untuk mendapatakan diskon 20%");
        request.setToken("dsuwPnxNpHZewfc8w-_Heo:APA91bHS92pwASe9FSdaUi3SHlJqiFOeAnjPUUKjSRCAOtRXQTUCHDOy7JpJFOU9Cn3hSmtMpPOxhxcUtVcGWp_1AjT73YMe6ktfISQO7gqI89270Gi3hacTKRzg99ozngP7fL7ZtiSj");
        fcmService.sendMessageToToken(request);
        promotionService.sendFCM(productPromoList);
    }
}