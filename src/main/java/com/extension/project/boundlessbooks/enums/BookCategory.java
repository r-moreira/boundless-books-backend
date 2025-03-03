package com.extension.project.boundlessbooks.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookCategory {
    RECOMENDADOS(Recomendados.class),
    LITERATURA_E_FICCAO(LiteraturaEFiccao.class),
    TECNICOS_E_ACADEMICOS(TecnicosEAcademicos.class),
    VIDA_PRATICA_E_OUTROS(VidaPraticaEOutros.class);

    private final Class<? extends Enum<?>> subCategory;

    @AllArgsConstructor
    @Getter
    public enum Recomendados {
        RECOMENDADOS_PARA_VOCE("Recomendados para você");

        private final String displayName;
    }

    @AllArgsConstructor
    @Getter
    public enum LiteraturaEFiccao {
        ROMANCE("Romance"),
        AVENTURA("Aventura"),
        BIOGRAFIAS_E_MEMORIAS("Biografias e Memórias"),
        FICCAO("Ficção"),
        FICCAO_FANTASTICA("Ficção Fantástica"),
        FICCAO_CIENTIFICA("Ficção Científica"),
        CONTOS_E_CRONICAS("Contos e Crônicas"),
        INFANTO_E_JUVENIL("Infanto e Juvenil"),
        POLICIAL("Policial"),
        HUMOR("Humor"),
        POEMAS_E_POESIA("Poemas e Poesia"),
        SUSPENSE_E_TERROR("Suspense e Terror");

        private final String displayName;
    }

    @AllArgsConstructor
    @Getter
    public enum TecnicosEAcademicos {
        ARTES_E_MUSICA("Artes e Música"),
        ADMINISTRACAO_E_ECONOMIA("Administração e Economia"),
        DIREITO("Direito"),
        POLITICA("Política"),
        FILOSOFIA("Filosofia"),
        DIDATICOS("Didáticos"),
        CONCURSO_PUBLICO("Concurso Público"),
        INFORMATICA("Informática"),
        HISTORIA("História"),
        GASTRONOMIA("Gastronomia"),
        HUMANAS_E_SOCIAIS("Humanas e Sociais"),
        PSICOLOGIA("Psicologia"),
        NUTRICAO_E_DIETAS("Nutrição e Dietas"),
        CIENCIAS("Ciências"),
        SAUDE_MEDICINA("Saúde, Medicina");

        private final String displayName;
    }

    @AllArgsConstructor
    @Getter
    public enum VidaPraticaEOutros {
        AUTOAJUDA("Autoajuda"),
        ESPORTES_E_JOGOS("Esportes e Jogos"),
        ESPIRITUALIDADE("Espiritualidade"),
        TURISMO_E_GUIAS_DE_VIAGEM("Turismo e Guias de Viagem");

        private final String displayName;
    }
}