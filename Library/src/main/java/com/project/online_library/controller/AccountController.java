package com.project.online_library.controller;


import com.project.online_library.camundaHendlers.TokenTaskHendler;
import com.project.online_library.dto.FormFieldsDto;
import com.project.online_library.model.Customer;
import com.project.online_library.model.Users;
import com.project.online_library.model.VerificationToken;
import com.project.online_library.service.CustomerService;
import com.project.online_library.service.VerificationTokenService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@CrossOrigin
@RestController
public class AccountController {

    @Autowired
    CustomerService customerService;
    @Autowired
    VerificationTokenService verificationTokenService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    TaskService taskService;
    @Autowired
    FormService formService;
    @Autowired
    TokenTaskHendler tokenTaskHendler;

    @GetMapping(path = "/activationForm", produces = "application/json")
    public @ResponseBody
    ResponseEntity getCamundasForm() {

        tokenTaskHendler.notify();

        return new ResponseEntity<>(TokenTaskHendler.formFieldsDto, HttpStatus.OK);
    }

    @PostMapping(path="/activation/{token}")
    public ResponseEntity activation (@PathVariable String token){

        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        if (verificationToken == null){
            return new ResponseEntity<>("Your verification token is invalid", HttpStatus.NOT_FOUND);
        } else {
            Users user = verificationToken.getUser();

            if(!user.isEnabled()){
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

                if (verificationToken.getExpiryDate().before(currentTimestamp)){
                    return new ResponseEntity<>("Your verification token has expired", HttpStatus.NOT_FOUND);
                } else{
                    user.setEnabled(true);
                //    customerService.save(user);
                    return new ResponseEntity<>(user, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>("Your account is already activated", HttpStatus.ALREADY_REPORTED);
            }
        }
        //return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}
