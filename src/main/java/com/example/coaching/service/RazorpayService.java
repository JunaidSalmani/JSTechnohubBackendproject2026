//package com.example.coaching.service;
//
//import com.razorpay.Order;
//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;
//import org.json.JSONObject;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RazorpayService {
//
//    private final RazorpayClient client;
//
//    public RazorpayService() throws RazorpayException {
//        this.client = new RazorpayClient("rzp_test_REaS1BheNXdkjC", "PNeoO4ZLulrlj6Bc4Wuykv1C");
//    }
//
//    public Order createOrder(Double amount) throws RazorpayException {
//        JSONObject options = new JSONObject();
//        options.put("amount", (int)(amount * 100)); // in paise
//        options.put("currency", "INR");
//        options.put("payment_capture", 1);
//
//        return client.Orders.create(options);
//    }
//}


package com.example.coaching.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {

    private final RazorpayClient client;

    public RazorpayService(
            @Value("${razorpay.key}") String key,
            @Value("${razorpay.secret}") String secret) throws RazorpayException {
        this.client = new RazorpayClient(key, secret);
    }

    public Order createOrder(Double amount) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", (int)(amount * 100)); // in paise
        options.put("currency", "INR");
        options.put("payment_capture", 1);

        return client.orders.create(options);
    }
}
