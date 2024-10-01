package com.prc.vsis9.controllers;

import com.prc.vsis9.service.PaymentRequest;
import com.prc.vsis9.service.StandardResponse;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final String sharedKey = "SHARED_KEY";
    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";
    private static final int CODE_SUCCESS = 100;
    private static final int AUTH_FAILURE = 102;
    @GetMapping
    public StandardResponse showStatus() {
        return new StandardResponse(SUCCESS_STATUS, 1);
    }
    @PostMapping("/pay")
    public StandardResponse pay(@RequestParam(value = "key") String key, @RequestBody PaymentRequest
            request) {
        final StandardResponse response;
        if (sharedKey.equalsIgnoreCase(key)) {
            int userId = request.getUserId();
            String itemId = request.getItemId();
            double discount = request.getDiscount();
            // Process the request
            // ....
            // Return success response to the client.
            response = new StandardResponse(SUCCESS_STATUS, CODE_SUCCESS);
        } else {
            response = new StandardResponse(ERROR_STATUS, AUTH_FAILURE);
        }
        return response;
    }
}
