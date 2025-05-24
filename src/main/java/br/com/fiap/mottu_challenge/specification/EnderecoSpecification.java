package br.com.fiap.mottu_challenge.specification;

import br.com.fiap.mottu_challenge.model.Endereco;
import br.com.fiap.mottu_challenge.model.specification.EnderecoFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EnderecoSpecification {

    public static Specification<Endereco> withFilters(EnderecoFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(filter.cidade() != null && !filter.cidade().isEmpty()) {
                predicates.add(cb.like(root.get("cidade"),filter.cidade()));
            }

            if(filter.uf() != null && !filter.uf().isEmpty()) {
                predicates.add(cb.like(root.get("uf"),filter.uf()));
            }
            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);
        };

    }
}
