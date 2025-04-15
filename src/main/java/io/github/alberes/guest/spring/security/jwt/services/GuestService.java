package io.github.alberes.guest.spring.security.jwt.services;

import io.github.alberes.guest.spring.security.jwt.domains.Guest;
import io.github.alberes.guest.spring.security.jwt.repositories.GuestRepository;
import io.github.alberes.guest.spring.security.jwt.services.exception.DuplicateRecordException;
import io.github.alberes.guest.spring.security.jwt.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuestService {

    @Autowired
    private GuestRepository repository;

    public Guest save(Guest guest){
        Optional<Guest> guestDB = this.repository.findById(guest.getLegalEntityNumber());
        if(guestDB.isPresent()) {
            throw new DuplicateRecordException("Registration with " + guest.getLegalEntityNumber() + " has already been registered!");
        }
        return this.repository.save(guest);
    }

    public Guest find(String legalEntityNumber){
        Optional<Guest> optional = this.repository.findById(legalEntityNumber);
        return optional.orElseThrow(() -> new ObjectNotFoundException(
                "Object not found! Id: " + legalEntityNumber + ", Type: " + Guest.class.getName()
        ));
    }

    public void update(Guest guest){
        this.find(guest.getLegalEntityNumber());
        this.repository.save(guest);
    }

    public void delete(String legalEntityNumber){
        this.find(legalEntityNumber);
        this.repository.deleteById(legalEntityNumber);
    }
}