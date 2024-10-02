package com.Loop.Erands.Controller;

import com.Loop.Erands.Service.PaymentService.PayPalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
public class PaymentController {

    @Autowired
    PayPalService payPalService;

    String URL ="http://localhost:8080";

    @GetMapping("/")
    public String home(){
        return null;
    }
    @PostMapping("/payment/create")
    public ResponseEntity<?> createPayment() {
        Payment payment = new Payment();
        try {
            String cancelUrl = URL + "/payment/cancel";
            String successUrl = URL + "/payment/success";
            payment = payPalService.createPayment(
                    100.0,
                    "USD",
                    "paypal",
                    "sale",
                    "Payment Description",
                    cancelUrl,
                    successUrl
            );

            log.info("Payment created successfully: " + payment);

            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    log.info("Approval URL: " + links.getHref());

                    return new ResponseEntity<>(links.getHref(),HttpStatus.OK) ;
                }
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred: ", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        log.error("Payment failed: " + payment.getFailureReason());
        return new ResponseEntity<>("Payment failed",HttpStatus.PAYMENT_REQUIRED);
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(@RequestParam("paymentId")String paymentId,@RequestParam("payerID")String payerId ){
        try{
            Payment payment = payPalService.executePayment(paymentId,payerId);
            if (payment.getState().equals("approved")){
                return "payment Success";
            }
        }catch(PayPalRESTException e){
            log.error("error occurred: ",e);
        }
        return "Payment Success";
    }
    @GetMapping("/payment/cancel")
    public String paymentCancel(){
        return "Payment Cancel";
    }
    @GetMapping("/payment/error")
    public String paymentError(){
        return "Payment error";
    }





}
