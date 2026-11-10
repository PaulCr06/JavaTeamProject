package com.example.demo.model;


public class Assistant extends Staff  {
        private Roles role;

        public Assistant() {
            super();
        }

        public Assistant(String id, String name, Roles role) {
            super(id, name);
            this.role = role;
        }

        public Roles getRole() { return role; }
        public void setRole(Roles role) { this.role = role; }
    }

