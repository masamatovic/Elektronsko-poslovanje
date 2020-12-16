package com.project.online_library.controller;

import com.project.online_library.dto.FormFieldsDto;
import com.project.online_library.dto.FormSubmissionDto;
import com.project.online_library.service.WriterRegistrationService;
import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/registration")
public class ResgistrationController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    FormService formService;

    @Autowired
    WriterRegistrationService writerRegistrationService;


    @GetMapping(path = "/writerRegistrationForm", produces = "application/json")
    public @ResponseBody FormFieldsDto getWriterRegistrationFields() {

        return writerRegistrationService.getWriterRegistrationFields();
    }

    @PostMapping(path = "/writer/{taskId}", produces = "application/json")
    public @ResponseBody
    ResponseEntity postWriterRegistrationData(@RequestBody List<FormSubmissionDto> formSubmissionDtos, @PathVariable String taskId) {

        writerRegistrationService.postWriterRegistrationData(formSubmissionDtos, taskId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
