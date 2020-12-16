package com.project.online_library.camunda;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.Variables.SerializationDataFormats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.online_library.model.Genre;
import com.project.online_library.repository.GenreRepository;

@Service
public class GenreListener implements TaskListener {
	@Autowired
    GenreRepository genreRepository;
	
	@Autowired
    FormService formService;
	@Autowired
	TaskService taskService;
	@Override
	public void notify(DelegateTask delegateTask) {
		// TODO Auto-generated method stub
		TaskFormData tfd = delegateTask.getExecution().getProcessEngineServices().getFormService().
				getTaskFormData(delegateTask.getId());
		
		List<FormField> formFieldList = tfd.getFormFields();
		if(formFieldList!=null){
            for(FormField field : formFieldList){
                if( field.getId().equals("genre")){
                    Map<Long, String> items = ((StringMultiSelect)field.getType()).getValues();
                    for(Genre genre : genreRepository.findAll()){
                        items.put(genre.getId(), genre.getName());
                    }
                }
            }
        }
	}
}
