package explorewithmeserver.repository;

import explorewithmeserver.model.compilation.Compilation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    Page<Compilation> findCompilationsByPinnedIsOrderByIdAsc(Boolean pinned, Pageable pageable);

}
