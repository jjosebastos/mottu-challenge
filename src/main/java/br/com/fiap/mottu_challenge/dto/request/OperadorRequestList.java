package br.com.fiap.mottu_challenge.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OperadorRequestList {
    @JsonProperty("operadoresList")
    @Valid
    private List<OperadorRequest> operadorList;
}
