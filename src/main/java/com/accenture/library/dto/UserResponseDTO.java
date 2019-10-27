package com.accenture.library.dto;

public class UserResponseDTO {

    private Long id;

    private String userName;

    private boolean enabled;

    public UserResponseDTO(Long id) {
        this.id = id;
    }

    public UserResponseDTO(Long id, String userName, boolean enabled) {
        this.id = id;
        this.userName = userName;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
