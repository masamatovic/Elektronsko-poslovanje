package com.project.online_library.service;

import com.project.online_library.dto.FormSubmissionDto;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RegistrationService implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDto> registration = (List<FormSubmissionDto>)delegateExecution.getVariable("registration");
        System.out.println(registration);
        User user = identityService.newUser("");
        for (FormSubmissionDto formField : registration) {
            if(formField.getId().equals("username")) {
                user.setId(formField.getFieldValue());
            }
            if(formField.getId().equals("password")) {
                user.setPassword(formField.getFieldValue());
            }
        }
        identityService.saveUser(user);

        saveUserToDb(registration);
    }
}