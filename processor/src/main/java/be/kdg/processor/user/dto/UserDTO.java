package be.kdg.processor.user.dto;


import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String userName;
    private String passWord;

    public UserDTO() {
    }


}
