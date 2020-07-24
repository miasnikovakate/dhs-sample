package com.epam.sample.dhsserver.resource;

import com.epam.sample.dhsserver.dto.Facility;
import com.epam.sample.dhsserver.dto.Patient;
import com.epam.sample.dhsserver.dto.Practice;
import com.epam.sample.dhsserver.dto.Surgeon;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DataLakeResource {

    @GetMapping(path = "/facilities")
    public Collection<Facility> getAllFacilities() {
        int count = getCount();
        Collection<Facility> facilities = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            facilities.add(new Facility()
                    .setId(UUID.randomUUID().toString())
                    .setName(UUID.randomUUID().toString())
                    .setCity(UUID.randomUUID().toString())
                    .setPracticeId(UUID.randomUUID().toString())
                    .setLastUpdated(Instant.now().toEpochMilli()));
        }
        return facilities;
    }

    @GetMapping(path = "/surgeries")
    public Collection<Surgeon> getAllSurgeries() {
        int count = getCount();
        Collection<Surgeon> surgeries = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            surgeries.add(new Surgeon()
                    .setId(UUID.randomUUID().toString())
                    .setName(UUID.randomUUID().toString())
                    .setPracticeId(UUID.randomUUID().toString())
                    .setLastUpdated(Instant.now().toEpochMilli()));
        }
        return surgeries;
    }

    @GetMapping(path = "/practices")
    public Collection<Practice> getAllPractices() {
        int count = getCount();
        Collection<Practice> practices = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            practices.add(new Practice()
                    .setId(UUID.randomUUID().toString())
                    .setName(UUID.randomUUID().toString())
                    .setLastUpdated(Instant.now().toEpochMilli()));
        }
        return practices;
    }

    @GetMapping(path = "/patients")
    public Collection<Patient> getAllPatients() {
        int count = getCount();
        Collection<Patient> patients = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            patients.add(new Patient()
                    .setId(UUID.randomUUID().toString())
                    .setName(UUID.randomUUID().toString())
                    .setPracticeId(UUID.randomUUID().toString())
                    .setLastUpdated(Instant.now().toEpochMilli())
                    .setBirthDate(LocalDate.of(1990, 4, 12))
                    .setFacilityId(UUID.randomUUID().toString())
                    .setSurgeonId(UUID.randomUUID().toString()));
        }
        return patients;
    }

    private int getCount() {
        return (int) Math.max(1, Math.round(Math.random() * 10));
    }

}
