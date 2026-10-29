package com.example.demo.model;


    public class Assistant extends Staff {
        private String role;

        public Assistant() {
            super();
        }

        public Assistant(String id, String name, String role) {
            super(id, name);
            this.role = role;
        }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }

