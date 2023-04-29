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

    private Integer id;
    private String nom;
    private String prenom;
    private String telephone;
    private String image;
    private String roles;
    private String email;
    private String password;
    private int isbanned;
    private String reset_token;

    private Date created_at;

    public User(Integer id, String nom, String prenom, String image, String email, String password) {
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

    public User(Integer id, String nom, String prenom, String email, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    public User(String image) {
        this.image = image;
    }

    public User(String nom, String prenom, String telephone, String email, String roles, int isbanned) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.roles = roles;
        this.isbanned = isbanned;
    }

    /**
     *
     * @param nom
     * @param prenom
     * @param email
     * @param telephone
     * @param image
     * @param roles
     * @param isbanned
     */
    public User(String nom, String prenom, String email, String telephone, String image, String roles, int isbanned) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.image = image;
        this.roles = roles;
        this.isbanned = isbanned;

    }

    public User(String nom, String prenom, String email, String telephone, String password, boolean dummy) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.password = password;

    }

    public User(Integer id) {
        this.id = id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public int getIsbanned() {
        return isbanned;
    }

    public void setIsbanned(int isbanned) {
        this.isbanned = isbanned;
    }

    public String getReset_token() {
        return reset_token;
    }

    public void setReset_token(String reset_token) {
        this.reset_token = reset_token;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", telephone=" + telephone + ", image=" + image + ", roles=" + roles + ", email=" + email + ", password=" + password + ", isbanned=" + isbanned + '}';
    }

}
