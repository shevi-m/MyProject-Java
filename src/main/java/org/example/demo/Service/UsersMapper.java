package org.example.demo.Service;

import org.example.demo.Model.Users;
import org.example.demo.dto.UsersDTO;
import org.mapstruct.Mapper;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {



    List<UsersDTO> usersToDTO(List<Users> users);

    default UsersDTO userToDTO(Users user) throws IOException {
        UsersDTO userDTO = new UsersDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setThoughtsList(user.getThoughtsList());
        userDTO.setResponsesList(user.getResponsesList());
        userDTO.setLikesList(user.getLikesList());
        if(user.getImagePath()!=null){
            userDTO.setImage(ImageUtils.getImage(user.getImagePath()));
        }
        return userDTO;
    }
}
