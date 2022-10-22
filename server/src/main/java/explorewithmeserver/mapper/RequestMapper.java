package explorewithmeserver.mapper;

import explorewithmeserver.model.event.Event;
import explorewithmeserver.model.request.Request;
import explorewithmeserver.model.request.RequestDto;
import explorewithmeserver.model.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestMapper {

    private final ModelMapper mapper;

    Converter<User, Long> convertUser = src -> src
            .getSource() == null ? null : src.getSource().getId();

    Converter<Event, Long> convertEvent = src -> src
            .getSource() == null ? null : src.getSource().getId();

    public RequestDto mapToRequestDto(Request request) {
        mapper.typeMap(Request.class, RequestDto.class)
                .addMappings(m -> m.using(convertUser)
                        .map(Request::getRequester, RequestDto::setRequester))
                .addMappings(m -> m.using(convertEvent)
                        .map(Request::getEvent, RequestDto::setEvent));
        return mapper.map(request, RequestDto.class);
    }
}
