package br.com.fiap.mottu_challenge.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilialRequestList {
    @JsonProperty("filialRequests")
    @Valid
    List<FilialRequest> filialRequests;
}
