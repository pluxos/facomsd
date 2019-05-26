package client.commons.domain;

import java.io.Serializable;

public class User implements Serializable {
  
	private static final long serialVersionUID = 1L;
	
	private String email;
    private String name;
    private String password;

    public User(){}

    public User(String email, String password, String name) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String  getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "email: " + this.getEmail() + " pass: " + this.getPassword() + " name: " + this.getName();
    }
}
