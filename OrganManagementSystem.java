package com.cybernetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OrganManagementSystem {
    private List<Organ> organs;
    private List<Patient> patients;
    private OrganCompatibilityAnalyzer analyzer;

    public OrganManagementSystem(List<Organ> organs, List<Patient> patients) {
        this.organs = organs;
        this.patients = patients;
        this.analyzer = new OrganCompatibilityAnalyzer();
    }
    public Set<String> getUniqueBloodTypes() {
        Set<String> bloodTypes = organs.stream()
                .map(Organ::getBloodType)
                .collect(Collectors.toSet());
        bloodTypes.addAll(patients.stream()
                .map(Patient::getBloodType)
                .collect(Collectors.toSet()));
        return bloodTypes;
    }

    public Map<String, List<Patient>> groupPatientsByBloodType() {
        return patients.stream().collect(Collectors.groupingBy(Patient::getBloodType));
    }

    public List<Organ> sortOrgansByWeight() {
        return organs.stream()
                .sorted(Comparator.comparingInt(Organ::getWeight))
                .collect(Collectors.toList());
    }


    public List<Organ> getTopCompatibleOrgans(Patient patient, int n) {
        return organs.stream()
                .sorted((o1, o2) -> Double.compare(
                        analyzer.calculateCompatibilityScore(o2, patient),
                        analyzer.calculateCompatibilityScore(o1, patient)
                ))
                .limit(n)
                .collect(Collectors.toList());
    }

    }