package io.github.alberes.guest.spring.security.jwt.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "guest")
@EntityListeners(AuditingEntityListener.class)
public class Guest implements Serializable {

    @Id
    @Column(name = "legal_entity_number")
    private String legalEntityNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Column(name = "last_update_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;

    @Column(name = "creation_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreatedDate
    private LocalDateTime createdDate;

    public Guest() {
    }

    public Guest(String legalEntityNumber, String name, LocalDate birthday) {
        this.legalEntityNumber = legalEntityNumber;
        this.name = name;
        this.birthday = birthday;
    }

    public String getLegalEntityNumber() {
        return legalEntityNumber;
    }

    public void setLegalEntityNumber(String legalEntityNumber) {
        this.legalEntityNumber = legalEntityNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Guest guest = (Guest) object;
        return Objects.equals(legalEntityNumber, guest.legalEntityNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(legalEntityNumber);
    }
}