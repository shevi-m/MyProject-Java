package org.example.demo.Service;

import org.example.demo.Model.Thought;
import org.example.demo.dto.ThoughtDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ThoughtMapper {

    CategoryMapper INSTENCEcategory = Mappers.getMapper(CategoryMapper.class);
    UsersMapper INSTENCEuser = Mappers.getMapper(UsersMapper.class);


    List<ThoughtDTO> thoughtsToDTO(List<Thought> thoughts);

    default ThoughtDTO thoughtToDTO(Thought thought) throws IOException {
        ThoughtDTO thoughtDTO = new ThoughtDTO();
        thoughtDTO.setId(thought.getId());
        thoughtDTO.setTitle(thought.getTitle());
        thoughtDTO.setDesc(thought.getDesc());
        thoughtDTO.setDate(thought.getDate());
        thoughtDTO.setCategoryDTO(INSTENCEcategory.categoryToDTO(thought.getCategory()));
        thoughtDTO.setUserDTO(INSTENCEuser.userToDTO(thought.getUser()));
        thoughtDTO.setAge(thought.getAge());
        thoughtDTO.setResponseList(thought.getResponseList());
        thoughtDTO.setLikesList(thought.getLikesList());
        if (thought.getImagePath() != null)
            thoughtDTO.setImage(ImageUtils.getImage(thought.getImagePath()));

        return thoughtDTO;
    }
}
