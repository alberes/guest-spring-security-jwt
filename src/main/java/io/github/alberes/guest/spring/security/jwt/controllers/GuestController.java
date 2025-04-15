package io.github.alberes.guest.spring.security.jwt.controllers;

import io.github.alberes.guest.spring.security.jwt.controllers.dto.GuestDto;
import io.github.alberes.guest.spring.security.jwt.domains.Guest;
import io.github.alberes.guest.spring.security.jwt.services.GuestService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guests")
public class GuestController implements GenericController{

    @Autowired
    private GuestService service;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid GuestDto dto){
        Guest guest = new Guest();
        BeanUtils.copyProperties(dto, guest);
        this.service.save(guest);
        return ResponseEntity
                .created(this.createURI("/{legalEntityNumber}", dto.legalEntityNumber()))
                .build();
    }

    @GetMapping("/{legalEntityNumber}")
    public ResponseEntity<GuestDto> find(@PathVariable String legalEntityNumber){
        Guest guest = this.service.find(legalEntityNumber);
        GuestDto dto = new GuestDto(guest.getLegalEntityNumber(), guest.getName(), guest.getBirthday());
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{legalEntityNumber}")
    public ResponseEntity<Void> update(@PathVariable String legalEntityNumber, @RequestBody GuestDto dto){
        Guest guest = new Guest();
        BeanUtils.copyProperties(dto, guest);
        guest.setLegalEntityNumber(legalEntityNumber);
        this.service.update(guest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{legalEntityNumber}")
    public ResponseEntity<Void> delete(@PathVariable String legalEntityNumber){
        this.service.delete(legalEntityNumber);
        return ResponseEntity.noContent().build();
    }

}