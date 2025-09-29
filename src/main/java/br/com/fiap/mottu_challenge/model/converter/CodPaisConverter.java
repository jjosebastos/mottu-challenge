package br.com.fiap.mottu_challenge.model.converter;

import br.com.fiap.mottu_challenge.model.enums.CodPais;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CodPaisConverter implements AttributeConverter<CodPais, String> {

    @Override
    public String convertToDatabaseColumn(CodPais codPais) {
        return codPais != null ? codPais.getCodigo() : null;
    }

    @Override
    public CodPais convertToEntityAttribute(String codigo) {
        return codigo != null ? CodPais.fromCodigo(codigo) : null;
    }
}
