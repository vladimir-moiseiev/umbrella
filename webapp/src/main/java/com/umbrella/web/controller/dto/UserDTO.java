package com.umbrella.web.controller.dto;

import java.util.Date;

public class UserDTO {
    private final long id;
    private final String username;
    private final Date validUntil;
    private final boolean isAdmin;

    public UserDTO(long id, String username, Date validUntil, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.validUntil = validUntil;
        this.isAdmin = isAdmin;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (id != userDTO.id) return false;
        if (isAdmin != userDTO.isAdmin) return false;
        if (username != null ? !username.equals(userDTO.username) : userDTO.username != null) return false;
        if (validUntil != null ? !validUntil.equals(userDTO.validUntil) : userDTO.validUntil != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (validUntil != null ? validUntil.hashCode() : 0);
        result = 31 * result + (isAdmin ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", validUntil=" + validUntil +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
