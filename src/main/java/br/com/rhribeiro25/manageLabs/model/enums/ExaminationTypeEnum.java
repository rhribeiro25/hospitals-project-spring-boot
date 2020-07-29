package br.com.rhribeiro25.manageLabs.model.enums;

public enum ExaminationTypeEnum {
    ANALYSIS("Análise"),
    CLINIC("Clínica"),
    IMAGE("Imagem");

    private String descricao;

    ExaminationTypeEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
