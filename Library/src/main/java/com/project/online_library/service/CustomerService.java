package com.project.online_library.service;

import com.project.online_library.model.Customer;
import com.project.online_library.model.VerificationToken;
import com.project.online_library.repository.CustomerRepository;
import org.glassfish.jersey.internal.inject.Custom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VerificationTokenService verificationTokenService;

    @Autowired
    EmailService emailService;

    public Customer  save (Customer customer){ return customerRepository.save(customer);}

    public Customer register (Customer customer){
        Customer newCustomer = new Customer();
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setPassword(customer.getPassword());
        newCustomer.setEnabled(false);

        Optional<Customer> saved = Optional.of(save(newCustomer));

        // Pravljenje i cuvanje tokena ako je korisnik sacuvan
        saved.ifPresent( u -> {
            try {
                String token = UUID.randomUUID().toString();
                verificationTokenService.save(saved.get(), token);

                // salanje  verifikacionog mejla
                emailService.sendEmail(u);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        return saved.get();
    }




}
