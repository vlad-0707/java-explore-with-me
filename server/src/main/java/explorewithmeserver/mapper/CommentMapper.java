package explorewithmeserver.mapper;

import explorewithmeserver.model.comments.Comment;
import explorewithmeserver.model.comments.CommentDto;
import explorewithmeserver.model.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final ModelMapper mapper;

    Converter<User, String> convertAuthor = src -> src
            .getSource() == null ? null : src.getSource().getName();

    public CommentDto mapToCommentDto(Comment comment){
        mapper.typeMap(Comment.class, CommentDto.class)
                .addMappings(m -> m.using(convertAuthor)
                        .map(Comment::getAuthor, CommentDto::setAuthor));
        return mapper.map(comment, CommentDto.class);
    }
}
