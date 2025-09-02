package br.dev.mmc.cbkt.domain.record;

public record EnderecoRecord(
    String cep,
    String logradouro,
    String complemento,
    String bairro,
    String localidade,
    String uf,
    String ibge,
    String gia,
    String ddd,
    String siafi,
    Boolean erro
) {}