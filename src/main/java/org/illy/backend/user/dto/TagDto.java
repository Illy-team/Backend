package org.illy.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.illy.backend.user.entity.Tag;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private Long tagId;
    private String name;

    public TagDto(Tag tag) {
        tagId = tag.getId();
        name = tag.getName();
    }
}
