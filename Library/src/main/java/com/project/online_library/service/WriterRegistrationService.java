package com.project.online_library.service;

import com.project.online_library.dto.FormFieldsDto;
import com.project.online_library.dto.FormSubmissionDto;
import com.project.online_library.model.BetaReader;
import com.project.online_library.model.Reader;
import com.project.online_library.model.Writer;
import com.project.online_library.repository.BetaReaderRepository;
import com.project.online_library.repository.ReaderRepository;
import com.project.online_library.repository.WriterRepository;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class WriterRegistrationService implements JavaDelegate {

    @Autowired
    IdentityService identityService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    WriterRepository writerRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        List<FormSubmissionDto> writerRegistration = (List<FormSubmissionDto>)delegateExecution.getVariable("writerRegistration");
        System.out.println(writerRegistration);
        User user = identityService.newUser("");
        for (FormSubmissionDto formField : writerRegistration) {
            if(formField.getId().equals("username")) {
                user.setId(formField.getFieldValue());
            }
            if(formField.getId().equals("password")) {
                user.setPassword(formField.getFieldValue());
            }

        }

        saveUserToDb(writerRegistration);
        identityService.saveUser(user);

    }

    public void saveUserToDb(List<FormSubmissionDto> registration){

        Writer writer = new Writer(registration.get(0).getFieldValue(), registration.get(1).getFieldValue(), registration.get(2).getFieldValue(), registration.get(6).getFieldValue(), registration.get(5).getFieldValue(), registration.get(3).getFieldValue(), registration.get(4).getFieldValue());
        writerRepository.save(writer);
    }

    public FormFieldsDto getWriterRegistrationFields(){

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("WriterRegistrationProcess");

        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

        TaskFormData tfd = formService.getTaskFormData(task.getId());
        List<FormField> properties = tfd.getFormFields();
        for(FormField fp : properties) {
            System.out.println(fp.getId() + fp.getType());
        }

        return new FormFieldsDto(task.getId(), pi.getId(), properties);
    }

    public void postWriterRegistrationData(List<FormSubmissionDto> formSubmissionDtos, String taskId){

        HashMap<String, Object> map = this.mapListToDto(formSubmissionDtos);

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String processInstanceId = task.getProcessInstanceId();
        runtimeService.setVariable(processInstanceId, "writerRegistration", formSubmissionDtos);
        formService.submitTaskForm(taskId, map);
    }

    private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
    {
        HashMap<String, Object> map = new HashMap<String, Object>();
        for(FormSubmissionDto temp : list){
            System.out.println(temp.getId());
            System.out.println(temp.getFieldValue());
            map.put(temp.getId(), temp.getFieldValue());
        }

        return map;
    }


}
