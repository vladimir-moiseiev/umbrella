package com.umbrella.web.controller.dto;

public class UserDTO {
    private final String username;
    private final boolean isAdmin;

    public UserDTO(String username, boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (isAdmin != userDTO.isAdmin) return false;
        if (username != null ? !username.equals(userDTO.username) : userDTO.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (isAdmin ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
