package Entities;




import java.sql.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author shayma
 */
public class User {
    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String image;
    private Role role;
    private String email;
    private String password;


    
    

    public User(int id, String nom, String prenom, String image, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.image = image;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public User(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
    }

    public User(String image) {
        this.image = image;
    }
    
   
    
    
    public User(String nom, String prenom, String password, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
    }

  
    public User(String nom, String prenom, String email, String telephone, String image) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.image = image;

    }
    

    


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
   

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom +", prenom=" + prenom + ", adresse=" + email + ", photoDeProfil=" + image + '}';
    }
    

    

}
