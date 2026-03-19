// package com.example.coaching.controller;

// import java.util.List;
// import java.util.Map;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.coaching.model.Payment;
// import com.example.coaching.model.User;
// import com.example.coaching.repository.PaymentRepository;
// import com.example.coaching.repository.UserRepository;
// import com.example.coaching.service.OtpService;
// import com.example.coaching.service.RazorpayService;
// import com.razorpay.Order;
// import com.razorpay.RazorpayException;

// import jakarta.validation.Valid;

// @RestController
// @RequestMapping("/api")
// public class PaymentController {

//     @Autowired
//     private UserRepository userRepository;

//     @Autowired
//     private PaymentRepository paymentRepository;

//     @Autowired
//     private OtpService otpService;

//     @Autowired
//     private RazorpayService razorpayService;

//     // Send OTP
//     @PostMapping("/send-otp")
//     public String sendOtp(@RequestParam String email) {

//         //System.out.println(email+" ");
//     	String otp = otpService.generateOtp(email);
//         //System.out.println(email+" "+otp);
//         // send email here if needed
//         return "OTP sent to " + email + ": " + otp;
//     }

//     // Verify OTP
//     @PostMapping("/verify-otp")
//     public String verifyOtp(@RequestParam String email, @RequestParam String otp) {
//         boolean valid = otpService.verifyOtp(email, otp);

//         if (valid) {
//             otpService.removeOtp(email);

//             // Get all users with the given email
//             List<User> users = userRepository.findByEmail(email);

//             // Mark emailVerified = true for all of them
//             for (User user : users) {
//                 user.setEmailVerified(true);
//             }

//             userRepository.saveAll(users);

//             return "OTP verified successfully";
//         }

//         return "Invalid OTP";
//     }
//     // Store user before payment
//     @PostMapping("/user")
//     public User createUser(@RequestBody @Valid User user) {
//         user.setEmailVerified(false);
//         return userRepository.save(user);
//     }

// //    @PostMapping("/create-order")
// //    public String createOrder(@RequestBody Map<String, Object> payload) throws RazorpayException {
// //        Long userId = Long.valueOf(payload.get("userId").toString());
// //        Double amount = Double.valueOf(payload.get("amount").toString());
// //
// //        Optional<User> userOpt = userRepository.findById(userId);
// //        if (userOpt.isEmpty()) {
// //            return "User not found";
// //        }
// //
// //        Order order = razorpayService.createOrder(amount);
// //
// //        Payment payment = Payment.builder()
// //                .razorpayOrderId(order.get("id"))
// //                .amount(amount)
// //                .user(userOpt.get())
// //                .build();
// //
// //        paymentRepository.save(payment);
// //
// //        return order.toString();
// //    }

//     // Verify Razorpay payment
// //    @PostMapping("/verify")
// //    public String verifyPayment(@RequestBody VerifyPaymentRequest request) {
// //        Optional<Payment> paymentOpt = paymentRepository.findAll().stream()
// //                .filter(p -> p.getRazorpayOrderId().equals(request.getRazorpayOrderId()))
// //                .findFirst();
// //
// //        if (paymentOpt.isEmpty()) return "Order not found";
// //
// //        Payment payment = paymentOpt.get();
// //        payment.setRazorpayPaymentId(request.getRazorpayPaymentId());
// //        payment.setRazorpaySignature(request.getRazorpaySignature());
// //        paymentRepository.save(payment);
// //
// //        return "Payment verified and stored successfully";
// //    }
//     // inside PaymentController

//     @PostMapping("/verify")
//     public String verifyPayment(@RequestBody Map<String, String> payload) {
//         String razorpayOrderId = payload.get("razorpayOrderId");
//         String razorpayPaymentId = payload.get("razorpayPaymentId");
//         String razorpaySignature = payload.get("razorpaySignature");
// //        
// //        System.out.println(razorpayOrderId);
// //        System.out.println(razorpayPaymentId);
// //        System.out.println(razorpaySignature);
// //        
//         Optional<Payment> paymentOpt = paymentRepository.findAll().stream()
//                 .filter(p -> p.getRazorpayOrderId().equals(razorpayOrderId))
//                 .findFirst();

//         if(paymentOpt.isEmpty()) return "Order not found";
// //        
// //        System.out.println(razorpayOrderId);
// //        System.out.println(razorpayPaymentId);
// //        System.out.println(razorpaySignature);
// //        


//         Payment payment = paymentOpt.get();
//         //System.out.println(payment);
//         payment.setRazorpayPaymentId(razorpayPaymentId);
//         payment.setRazorpaySignature(razorpaySignature);
//         paymentRepository.save(payment);

//         // ✅ Send acknowledgement email
//         String userEmail = payment.getUser().getEmail();
//         String subject = "Payment Successful - Coaching Platform";
//         String body = "Hello " + payment.getUser().getName() + ",\n\n" +
//         		"We have received your payment of ₹" + payment.getAmount() + " successfully.\n" +
//         		"Order ID: " + razorpayOrderId + "\n" +
//         		"Payment ID: " + razorpayPaymentId + "\n\n" +
//         		"Thank you for your trust in us.\n" +
//         		"If you have any questions, feel free to reach us at +91-9876543210.\n\n" +
//         		"Best regards,\n" +
//         		"Coaching Team";
        
//         otpService.sendEmail(userEmail, subject, body);

//         return "Payment verified and stored successfully";
//     }

// }
