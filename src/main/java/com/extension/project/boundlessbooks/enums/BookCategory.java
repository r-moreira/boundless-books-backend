package com.extension.project.boundlessbooks.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookCategory {

    // Recomendados
    RECOMENDADOS_PARA_VOCE("Recomendados para você", MainCategory.RECOMENDADOS),

    // Literatura e Ficção
    ROMANCE("Romance", MainCategory.LITERATURA_E_FICCAO),
    AVENTURA("Aventura", MainCategory.LITERATURA_E_FICCAO),
    BIOGRAFIAS_E_MEMORIAS("Biografias e Memórias", MainCategory.LITERATURA_E_FICCAO),
    FICCAO("Ficção", MainCategory.LITERATURA_E_FICCAO),
    FICCAO_FANTASTICA("Ficção Fantástica", MainCategory.LITERATURA_E_FICCAO),
    FICCAO_CIENTIFICA("Ficção Científica", MainCategory.LITERATURA_E_FICCAO),
    CONTOS_E_CRONICAS("Contos e Crônicas", MainCategory.LITERATURA_E_FICCAO),
    INFANTO_E_JUVENIL("Infanto e Juvenil", MainCategory.LITERATURA_E_FICCAO),
    POLICIAL("Policial", MainCategory.LITERATURA_E_FICCAO),
    HUMOR("Humor", MainCategory.LITERATURA_E_FICCAO),
    POEMAS_E_POESIA("Poemas e Poesia", MainCategory.LITERATURA_E_FICCAO),
    SUSPENSE_E_TERROR("Suspense e Terror", MainCategory.LITERATURA_E_FICCAO),

    // Técnicos e Acadêmicos
    ARTES_E_MUSICA("Artes e Música", MainCategory.TECNICOS_E_ACADEMICOS),
    ADMINISTRACAO_E_ECONOMIA("Administração e Economia", MainCategory.TECNICOS_E_ACADEMICOS),
    DIREITO("Direito", MainCategory.TECNICOS_E_ACADEMICOS),
    POLITICA("Política", MainCategory.TECNICOS_E_ACADEMICOS),
    FILOSOFIA("Filosofia", MainCategory.TECNICOS_E_ACADEMICOS),
    DIDATICOS("Didáticos", MainCategory.TECNICOS_E_ACADEMICOS),
    CONCURSO_PUBLICO("Concurso Público", MainCategory.TECNICOS_E_ACADEMICOS),
    INFORMATICA("Informática", MainCategory.TECNICOS_E_ACADEMICOS),
    HISTORIA("História", MainCategory.TECNICOS_E_ACADEMICOS),
    GASTRONOMIA("Gastronomia", MainCategory.TECNICOS_E_ACADEMICOS),
    HUMANAS_E_SOCIAIS("Humanas e Sociais", MainCategory.TECNICOS_E_ACADEMICOS),
    PSICOLOGIA("Psicologia", MainCategory.TECNICOS_E_ACADEMICOS),
    NUTRICAO_E_DIETAS("Nutrição e Dietas", MainCategory.TECNICOS_E_ACADEMICOS),
    CIENCIAS("Ciências", MainCategory.TECNICOS_E_ACADEMICOS),
    SAUDE_MEDICINA("Saúde, Medicina", MainCategory.TECNICOS_E_ACADEMICOS),

    // Vida Prática e Outros
    AUTOAJUDA("Autoajuda", MainCategory.VIDA_PRATICA_E_OUTROS),
    ESPORTES_E_JOGOS("Esportes e Jogos", MainCategory.VIDA_PRATICA_E_OUTROS),
    ESPIRITUALIDADE("Espiritualidade", MainCategory.VIDA_PRATICA_E_OUTROS),
    TURISMO_E_GUIAS_DE_VIAGEM("Turismo e Guias de Viagem", MainCategory.VIDA_PRATICA_E_OUTROS);

    private final String displayName;
    private final String mainCategory;

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    public static BookCategory fromDisplayName(String displayName) {
        for (BookCategory category : BookCategory.values()) {
            if (category.getDisplayName().equals(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category name: " + displayName);
    }

    public static class MainCategory {
        public static final String RECOMENDADOS = "Recomendados";
        public static final String LITERATURA_E_FICCAO = "Literatura e Ficção";
        public static final String TECNICOS_E_ACADEMICOS = "Técnicos e Acadêmicos";
        public static final String VIDA_PRATICA_E_OUTROS = "Vida Prática e Outros";
    }
}