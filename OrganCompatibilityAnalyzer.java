package com.cybernetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrganCompatibilityAnalyzer {
    private List<Organ> organs;
    private List<Patient> patients;

    public OrganCompatibilityAnalyzer() {
        organs = new ArrayList<>();
        patients = new ArrayList<>();
    }

    public void addOrgan(Organ organ) {
        organs.add(organ);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public int[][] createCompatibilityMatrix() {
        int[][] matrix = new int[organs.size()][patients.size() * 3]; // 3 factors: blood type, weight, HLA

        for (int i = 0; i < organs.size(); i++) {
            Organ organ = organs.get(i);
            for (int j = 0; j < patients.size(); j++) {
                Patient patient = patients.get(j);

                // Calculate individual compatibility scores for each factor
                matrix[i][j * 3] = calculateBloodTypeCompatibility(organ.getBloodType(), patient.getBloodType());
                matrix[i][j * 3 + 1] = calculateWeightCompatibility(organ.getWeight(), patient.getWeight());
                matrix[i][j * 3 + 2] = calculateHlaCompatibility(organ.getHlaType(), patient.getHlaType());
            }
        }
        return matrix;
    }

    private int calculateBloodTypeCompatibility(String donorType, String recipientType) {
        if (donorType.equals(recipientType)) {
            return 100;
        } else if ((donorType.equals("O") && (recipientType.equals("A") || recipientType.equals("B") || recipientType.equals("AB")))
                || (donorType.equals("A") && recipientType.equals("AB"))
                || (donorType.equals("B") && recipientType.equals("AB"))) {
            return 80;
        } else {
            return 0;
        }
    }

    public List<Organ> getCompatibleOrgans(Patient patient) {
        return organs.stream()
                .filter(organ -> calculateCompatibilityScore(organ, patient) > 50)
                .collect(Collectors.toList());
    }

    public Map<Patient, List<Double>> calculateCompatibilityScores() {
        return patients.stream().collect(Collectors.toMap(
                patient -> patient,
                patient -> organs.stream()
                        .map(organ -> calculateCompatibilityScore(organ, patient))
                        .collect(Collectors.toList())
        ));
    }

    public double calculateCompatibilityScore(Organ organ, Patient patient) {
        double bloodTypeScore = calculateBloodTypeCompatibility(organ.getBloodType(), patient.getBloodType());
        double weightScore = calculateWeightCompatibility(organ.getWeight(), patient.getWeight());
        double hlaScore = calculateHlaCompatibility(organ.getHlaType(), patient.getHlaType());
        return (bloodTypeScore * 0.4) + (weightScore * 0.3) + (hlaScore * 0.3);
    }

    private int calculateWeightCompatibility(int organWeight, int patientWeight) {
        double weightRatio = (double) organWeight / (patientWeight * 1000.0);
        if (weightRatio >= 0.8 && weightRatio <= 1.2) {
            return 100;
        } else if (weightRatio >= 0.6 && weightRatio <= 1.4) {
            return 50;
        } else {
            return 0;
        }
    }

    private int calculateHlaCompatibility(String organHla, String patientHla) {
        String[] donorAntigens = organHla.split("-");
        String[] recipientAntigens = patientHla.split("-");
        int matches = 0;
        int totalAntigens = Math.min(donorAntigens.length, recipientAntigens.length);

        for (int i = 0; i < totalAntigens; i++) {
            if (donorAntigens[i].equals(recipientAntigens[i])) {
                matches++;
            }
        }
        return (matches * 100) / totalAntigens;
    }

    public double[][] calculateWeightedCompatibility(double[] weights) {
        int[][] compatibilityMatrix = createCompatibilityMatrix();
        double[][] resultMatrix = new double[organs.size()][patients.size()];

        for (int i = 0; i < organs.size(); i++) {
            for (int j = 0; j < patients.size(); j++) {
                int bloodScore = compatibilityMatrix[i][j * 3];
                int weightScore = compatibilityMatrix[i][j * 3 + 1];
                int hlaScore = compatibilityMatrix[i][j * 3 + 2];

                resultMatrix[i][j] = (bloodScore * weights[0]) + (weightScore * weights[1]) + (hlaScore * weights[2]);
            }
        }
        return resultMatrix;
    }

    public void displayMatrix(int[][] matrix) {
        System.out.println("Initial Compatibility Matrix:");
        System.out.print("      ");
        for (int i = 0; i < patients.size(); i++) {
            System.out.printf("P%d    P%d    P%d    ", i + 1, i + 1, i + 1);
        }
        System.out.println();
        for (int i = 0; i < organs.size(); i++) {
            System.out.printf("O%d   ", i + 1);
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%-6d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public void displayWeightedMatrix(double[][] matrix) {
        System.out.println("\nFinal Weighted Compatibility Matrix:");
        System.out.print("     ");
        for (int j = 0; j < patients.size(); j++) {
            System.out.printf("P%d   ", j + 1);
        }
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("O%d   ", i + 1);
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%-6.1f", matrix[i][j]);
            }
            System.out.println();
        }
    }


    public void displayWeightMatrix(double[] weights) {
        System.out.println("\nWeight Matrix:");
        for (double weight : weights) {
            System.out.printf("%.2f\t", weight);
        }
        System.out.println();
    }


}
