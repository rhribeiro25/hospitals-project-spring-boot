package br.com.rhribeiro25.manageLabs.model.enums;

public enum ExaminationStatusEnum {
    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    private String descricao;

    ExaminationStatusEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
