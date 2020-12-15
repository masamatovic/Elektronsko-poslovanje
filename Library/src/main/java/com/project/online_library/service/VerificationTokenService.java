package com.project.online_library.service;

import com.project.online_library.model.Customer;
import com.project.online_library.model.VerificationToken;
import com.project.online_library.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class VerificationTokenService {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    public void  save (Customer customer, String token){
        VerificationToken verificationToken = new VerificationToken(customer, token);
        //podesavanje isticanja tokena za 24 sata u minutima
        verificationToken.setExpiryDate(calculateExpiryDate(24*60));
        verificationTokenRepository.save(verificationToken);
    }

    public Timestamp calculateExpiryDate (int expiryTimeInMinutes){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Timestamp(cal.getTime().getTime());
    }

    public VerificationToken findByToken (String token){
        return verificationTokenRepository.findByToken(token);
    }
}
