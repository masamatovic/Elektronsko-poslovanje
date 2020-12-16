package com.project.online_library.service;

import com.project.online_library.dto.FormSubmissionDto;
//import com.project.online_library.model.BetaReader;
import com.project.online_library.model.BetaReader;
import com.project.online_library.model.Reader;
import com.project.online_library.model.Users;
import com.project.online_library.model.Writer;
//import com.project.online_library.repository.BetaReaderRepository;
import com.project.online_library.repository.BetaReaderRepository;
import com.project.online_library.repository.ReaderRepository;
import com.project.online_library.repository.UserRepository;
import com.project.online_library.repository.WriterRepository;
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

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    BetaReaderRepository betaReaderRepository;

    @Autowired
    UserRepository userRepository;

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
            if(formField.getId().equals("betaReader")) {
                saveUserToDb(registration, formField.getFieldValue());
            }
        }
        identityService.saveUser(user);
    }

    public void saveUserToDb(List<FormSubmissionDto> registration, String isBetaReader){

        if(isBetaReader.equals("true")){
            saveBetaReader(registration);
        }else{
            saveReader(registration);
        }
    }

    public void saveBetaReader(List<FormSubmissionDto> registration){

        BetaReader betaReader = new BetaReader(registration.get(0).getFieldValue(), registration.get(1).getFieldValue(), registration.get(2).getFieldValue(), registration.get(6).getFieldValue(), registration.get(5).getFieldValue(), registration.get(3).getFieldValue(), registration.get(4).getFieldValue(), false);
        betaReaderRepository.save(betaReader);
    }

    public void saveReader(List<FormSubmissionDto> registration){

        Reader reader = new Reader(registration.get(0).getFieldValue(), registration.get(1).getFieldValue(), registration.get(2).getFieldValue(), registration.get(6).getFieldValue(), registration.get(5).getFieldValue(), registration.get(3).getFieldValue(), registration.get(4).getFieldValue(), false);
        readerRepository.save(reader);

    }

    public void activateUser (Users user){
        user.setEnabled(true);
        userRepository.save(user);

    }
}