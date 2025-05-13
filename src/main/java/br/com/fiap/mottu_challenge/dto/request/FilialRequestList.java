package br.com.fiap.mottu_challenge.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilialRequestList {
    @Valid
    @NotEmpty
    List<FilialRequest> filialRequests;
}
