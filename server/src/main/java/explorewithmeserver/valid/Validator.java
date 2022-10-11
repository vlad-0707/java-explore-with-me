package explorewithmeserver.valid;

import explorewithmeserver.exception.NotFoundException;
import explorewithmeserver.model.category.Category;
import explorewithmeserver.model.compilation.Compilation;
import explorewithmeserver.model.event.Event;
import explorewithmeserver.model.user.User;
import explorewithmeserver.repository.CategoryRepository;
import explorewithmeserver.repository.CompilationRepository;
import explorewithmeserver.repository.EventRepository;
import explorewithmeserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    public Compilation validCompilation(Long id) throws NotFoundException {
        return compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Compilation with id=%d was not found.", id)));
    }

    public Event validEvent(Long id) throws NotFoundException {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Event with id=%d was not found.", id)));
    }

    public Category validCategory(Long id) throws NotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Category with id=%d was not found.", id)));
    }

    public User validUser(Long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with id=%d was not found.", id)));
    }
}
