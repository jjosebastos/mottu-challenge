package br.com.fiap.mottu_challenge.specification;

import br.com.fiap.mottu_challenge.model.Filial;
import br.com.fiap.mottu_challenge.model.specification.FilialFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FilialSpecification {

    public static Specification<Filial> withFilters(FilialFilter filter) {
        return (root, query, cb) ->{
            List<Predicate> predicates = new ArrayList<>();
            if(filter.codPais() != null) {
                predicates.add(cb.equal(root.get("codPais"), filter.codPais()));
            }
            if(filter.dataAberturaInicial() != null && filter.dataAberturaFinal() != null) {
                predicates.add(cb.between(root.get("date"), filter.dataAberturaInicial(), filter.dataAberturaFinal()));
            }
            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);
        };
    }
}
