package me.reporte.notification.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

//    private final AmazonSNS amazonSNS;
//
//    public NotificationService(AmazonSNS amazonSNS) {
//        this.amazonSNS = amazonSNS;
//    }

    public void notify(String phoneNumber, String message) {
        System.out.println(phoneNumber);
        System.out.println(message);
        // PublishRequest publishRequest = new PublishRequest().withMessage(message).withPhoneNumber(phoneNumber);
        // amazonSNS.publish(publishRequest);
    }
}
