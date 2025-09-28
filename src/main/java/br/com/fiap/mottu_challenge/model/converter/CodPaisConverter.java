package br.com.fiap.mottu_challenge.model.converter;

import br.com.fiap.mottu_challenge.model.enums.CodPais;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CodPaisConverter implements AttributeConverter<CodPais, String> {

    @Override
    public String convertToDatabaseColumn(CodPais codPais) {
        if (codPais == null) {
            return null;
        }
        return codPais.getCodPais();
    }

    @Override
    public CodPais convertToEntityAttribute(String codPais) {
        if (codPais == null) {
            return null;
        }
        for (CodPais pais : CodPais.values()) {
            if (pais.getCodPais().equals(codPais)) {
                return pais;
            }
        }
        throw new IllegalArgumentException("Código de país inválido: " + codPais);
    }
}
