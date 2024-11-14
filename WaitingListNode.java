package com.cybernetic;

public class WaitingListNode {

        Patient patient; // The patient in the waiting list
        int priority; // The priority of the patient
        WaitingListNode next; // Reference to the next node

        // Constructor
        public WaitingListNode(Patient patient, int priority) {
            this.patient = patient;
            this.priority = priority;
            this.next = null;
        }
    }


