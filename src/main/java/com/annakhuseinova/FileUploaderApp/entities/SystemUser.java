package com.annakhuseinova.FileUploaderApp.entities;

import com.annakhuseinova.FileUploaderApp.validation.FieldMatch;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch(first = "password", second = "repeatPassword", message = "Passwords must match")
public class SystemUser {

    private Long id;

    @NotNull(message = "Please, enter your login")
    @Size(min = 2, message = "Login must have at least 3 symbols")
    private String login;

    @NotNull(message = "Please, enter your first name")
    @Size(min = 2, message = "First name must have at least 2 symbols")
    private String firstName;

    @NotNull(message = "Please, enter your last name ")
    @Size(min = 2, message = "Last name must have at least 2 symbols ")
    private String lastName;

    @NotNull(message = "Please, enter your password")
    @Size(min = 5, message = "Password must have at least 5 symbols")
    private String password;

    @NotNull(message = "Please, repeat your password")
    @Size(min=5, message = "Password must have at least 5 symbols")
    private String repeatPassword;

    @NotNull(message = "Please, enter your email")
    @Email(message = "Email incorrect")
    private String email;

    private int IsActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsActive() {
        return IsActive;
    }

    public void setIsActive(int isActive) {
        IsActive = isActive;
    }
    public SystemUser(){

    }
}
