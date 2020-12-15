package com.project.online_library.controller;


import com.project.online_library.model.Customer;
import com.project.online_library.model.VerificationToken;
import com.project.online_library.service.CustomerService;
import com.project.online_library.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@CrossOrigin
@RestController
public class AccountController {

    @Autowired
    CustomerService customerService;
    @Autowired
    VerificationTokenService verificationTokenService;

    @PostMapping(path = "/registerTest" )
    public ResponseEntity save (@RequestBody Customer customer){
      //  System.out.print("Usaooo sam ovdee staaa see desavaaaaa");
        Customer saved = customerService.register(customer);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @PostMapping(path="/activation/{token}")
    public ResponseEntity activation (@PathVariable String token){

        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if (verificationToken == null){
            return new ResponseEntity<>("Your verification token is invalid", HttpStatus.NOT_FOUND);
        } else {
            Customer customer = verificationToken.getCustomer();

            if(!customer.isEnabled()){
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

                if (verificationToken.getExpiryDate().before(currentTimestamp)){
                    return new ResponseEntity<>("Your verification token has expired", HttpStatus.NOT_FOUND);
                } else{
                    customer.setEnabled(true);
                    customerService.save(customer);
                    return new ResponseEntity<>(customer, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("Your account is already activated", HttpStatus.ALREADY_REPORTED);
            }
        }
        //return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}
