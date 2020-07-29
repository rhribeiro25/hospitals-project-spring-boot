package br.com.rhribeiro25.manageLabs.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LaboratoryStatusEnum {

    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private String descricao;

    LaboratoryStatusEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
