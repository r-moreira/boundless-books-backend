package com.extension.project.boundlessbooks.factory;

import com.extension.project.boundlessbooks.enums.BookCategory;
import com.extension.project.boundlessbooks.model.dto.BookMetadataDto;
import com.extension.project.boundlessbooks.model.entity.BookMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@UtilityClass
public class BookMetadataFactory {

    public static BookMetadata createBookMetadata() {
        BookMetadata book = new BookMetadata();
        book.setId(1L);
        book.setTitle("Harry Potter e a Ordem da Fênix");
        book.setAuthor("J.K. Rowling");
        book.setPublisher("Rocco");
        book.setCategory("Fantasia");
        book.setSynopsis("Harry Potter retorna para seu quinto ano em Hogwarts...");
        book.setPages(704);
        book.setReleaseDate(new Date());
        book.setCoverImageUrl("https://example.com/cover.jpg");
        book.setEpubUrl("https://example.com/book.epub");
        book.setFavoriteByUsers(new ArrayList<>(List.of(UserProfileFactory.createUserProfile())));
        book.setShelfByUsers(new ArrayList<>(List.of(UserProfileFactory.createUserProfile())));
        return book;
    }

    public static BookMetadataDto createBookMetadataDto() {
        return new BookMetadataDto(
                1L,
                "Harry Potter e a Ordem da Fênix",
                "J.K. Rowling",
                "Rocco",
                BookCategory.FANTASIA,
                "Harry Potter retorna para seu quinto ano em Hogwarts...",
                704,
                new Date(),
                "https://example.com/cover.jpg",
                "https://example.com/book.epub",
                "2025-05-02T23:20:13.18641",
                "2025-05-07T22:55:03.095056"
        );
    }

    public static List<BookMetadataDto> createBookMetadataDtoList() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(BOOKS_METADATA_LIST, objectMapper.getTypeFactory().constructCollectionType(List.class, BookMetadataDto.class));
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse book metadata list", e);
        }
    }

    public static String BOOKS_METADATA_LIST = """
       [
                 {
                   "title": "Harry Potter e a Ordem da Fênix",
                   "author": "J.K. Rowling",
                   "publisher": "Rocco",
                   "category": "Fantasia",
                   "synopsis": "Harry Potter retorna para seu quinto ano em Hogwarts e descobre que o Ministério da Magia está em negação sobre o retorno de Voldemort.",
                   "releaseDate": "2003/06/21",
                   "pages": 704,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_avHr99l5G.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_RRU77Qqdh.epub"
                 },
                 {
                   "title": "Harry Potter e o enigma do Príncipe",
                   "author": "J.K. Rowling",
                   "publisher": "Rocco",
                   "category": "Fantasia",
                   "synopsis": "No sexto ano de Harry em Hogwarts, ele descobre mais sobre o passado de Voldemort e se prepara para a batalha final.",
                   "releaseDate": "2005/07/16",
                   "pages": 652,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_A_Nf7y0QS.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_E0e1nsyang.epub"
                 },
                 {
                   "title": "Harry Potter e o Cálice de Fogo",
                   "author": "J.K. Rowling",
                   "publisher": "Rocco",
                   "category": "Fantasia",
                   "synopsis": "Harry é inesperadamente escolhido para competir no Torneio Tribruxo, uma competição perigosa entre três escolas de magia.",
                   "releaseDate": "2000/07/08",
                   "pages": 636,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_zkkXai0Oh.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_dXQZuMFA3.epub"
                 },
                 {
                   "title": "Harry Potter e o prisioneiro de Azkaban",
                   "author": "J.K. Rowling",
                   "publisher": "Rocco",
                   "category": "Fantasia",
                   "synopsis": "No terceiro ano de Harry em Hogwarts, ele descobre que um perigoso prisioneiro fugiu de Azkaban e está atrás dele.",
                   "releaseDate": "1999/07/08",
                   "pages": 317,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_d_W_MyRBI.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_SyWiwsW-3.epub"
                 },
                 {
                   "title": "Harry Potter e a Criança Amaldiçoada Partes Um e Dois (Edição Especial do Guião da Peça de Teatro)",
                   "author": "J. K. Rowling",
                   "publisher": "Pottermore",
                   "category": "Fantasia",
                   "synopsis": "Baseado numa nova história original de J.K. Rowling, John Tiffany e Jack Thorne, HARRY POTTER E A CRIANÇA AMALDIÇOADA é uma nova peça de teatro de Jack Thorne. É a oitava história de Harry Potter e a primeira a ser oficialmente representada em palco. A Edição Especial do Guião da Peça de Teatro apresenta aos leitores novos desenvolvimentos da vida de Harry Potter e dos seus amigos e familiares, logo após a estreia mundial da peça, em West End, Londres, em 30 de julho de 2016.Foi sempre difícil ser Harry Potter e não é mais fácil agora que ele se tornou num muito atarefado funcionário do Ministério da Magia, casado e pai de três crianças em idade escolar.Enquanto Harry luta com um passado que se recusa a ficar para trás, o seu filho mais novo, Albus, tem de se debater com o peso de um legado familiar que nunca desejou. Quando o passado e o presente se cruzam, pai e filho confrontam-se com uma desconfortável verdade: por vezes as trevas vêm de lugares inesperados.",
                   "releaseDate": "2016/07/31",
                   "pages": 336,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_JmV3P1wl6.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_S9td1bmY02.epub"
                 },
                 {
                   "title": "Hogwarts",
                   "author": "J.K. Rowling",
                   "publisher": "Le Livros",
                   "category": "Fantasia",
                   "synopsis": "Uma visão detalhada da escola de magia e bruxaria de Hogwarts, incluindo sua história, professores e segredos.",
                   "releaseDate": "2014/09/01",
                   "pages": 200,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_49jNjTYum.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_a2nqocKWu.epub"
                 },
                 {
                   "title": "Harry Potter e a Câmara Secreta",
                   "author": "J.K. Rowling",
                   "publisher": "Rocco",
                   "category": "Fantasia",
                   "synopsis": "No segundo ano de Harry em Hogwarts, ele descobre uma câmara secreta que guarda um terrível monstro.",
                   "releaseDate": "1998/07/02",
                   "pages": 341,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_ptS-WZwHr.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_yNn-0r3Sv.epub"
                 },
                 {
                   "title": "Harry Potter e as Relíquias da Morte",
                   "author": "J.K. Rowling",
                   "publisher": "Rocco",
                   "category": "Fantasia",
                   "synopsis": "No sétimo e último livro da série, Harry, Rony e Hermione saem em busca das Horcruxes para derrotar Voldemort de uma vez por todas.",
                   "releaseDate": "2007/07/21",
                   "pages": 607,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_iePwtC2Sa.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_pFknI3wLg.epub"
                 },
                 {
                   "title": "Harry Potter e a Pedra Filosofal",
                   "author": "J.K. Rowling",
                   "publisher": "Rocco",
                   "category": "Fantasia",
                   "synopsis": "Harry Potter descobre que é um bruxo e começa sua educação mágica em Hogwarts, onde faz amigos e enfrenta perigos.",
                   "releaseDate": "1997/06/26",
                   "pages": 223,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_sLBbKOlym.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_EVViu_oAI.epub"
                 },
                 {
                   "title": "O Guia do Mochileiro das Galaxias - Coleção Completa",
                   "author": "Douglas Adams",
                   "publisher": "Arqueiro",
                   "category": "Ficção",
                   "synopsis": "Douglas Adams - Coleção Completa O Guia do Mochileiro das GalaxiasUnidos em um único livro, todos volumes da popular \\"trilogia de cinco livros\\" de Douglas Adams, e uma das séries de sátira e ficção científica mais populares do mundo: \\"O Guia do Mochileiro das Galáxias.  O Livro traz:   - \\"O Guia do Mochileiro das Galáxias\\" - \\"O Restaurante no Fim do Universo\\" - \\"A Vida, o Universo e Tudo mais\\" - \\"Até Mais, e Obrigado Pelos Peixes!\\" - \\"Praticamente Inofensiva\\" ",
                   "releaseDate": "1970/01/01",
                   "pages": 832,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_IAcYXRd0J.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_U4-qtbdbX.epub"
                 },
                 {
                   "title": "A Revolução dos Bichos",
                   "author": "George Orwell",
                   "publisher": "LeLivros.com",
                   "category": "Ficção",
                   "synopsis": "Sentindo chegar sua hora, Major, um velho porco, reúne os animais da fazenda para compartilhar de um sonho: serem governados por eles próprios, os animais, sem a submissão e exploração do homem. Ensinou-lhes uma antiga canção, Animais da Inglaterra (Beasts of England), que resume a filosofia do Animalismo, exaltando a igualdade entre eles e os tempos prósperos que estavam por vir, deixando os demais animais extasiados com as possibilidades. O velho Major faleceu três dias depois, tomando a frente os astutos e jovens porcos Bola-de-Neve e Napoleão, que passaram a se reunir clandestinamente a fim de traçar as estratégias da revolução. Certo dia Sr. Jones, então proprietário da fazenda, se descuidou na alimentação dos animais, fato este que se tornou o estopim para aqueles bichos. Deu-se a Revolução.",
                   "releaseDate": "1970/01/01",
                   "pages": 112,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_Yf9LfSrvC.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_ckv-kzBVz.epub"
                 },
                 {
                   "title": "Neuromancer",
                   "author": "William Gibson",
                   "publisher": "Le Livros",
                   "category": "Ficção",
                   "synopsis": "O Livro é um marco na literatura moderna, inaugurando a cibercultura, inspirado a trilogia Matrix. Esta edição especial possui um prefácio inédito do autor, posfácio acadêmico de Adriana Amaral e nova tradução feita por Fábio Fernandes (o mesmo tradutor de Reconhecimento de Padrões, também de Gibson, e Laranja Mecânica, de Anthony Burgess).",
                   "releaseDate": "1970/01/01",
                   "pages": 271,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_jez_i6vQB.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_ilUTcFUVbT.epub"
                 },
                 {
                   "title": "1984",
                   "author": "George Orwell",
                   "publisher": "Companhia das Letras",
                   "category": "Ficção",
                   "synopsis": "Winston, herói de 1984, último romance de George Orwell, vive aprisionado na engrenagem totalitária de uma sociedade completamente dominada pelo Estado, onde tudo é feito coletivamente, mas cada qual vive sozinho. Ninguém escapa à vigilância do Grande Irmão, a mais famosa personificação literária de um poder cínico e cruel ao infinito, além de vazio de sentido histórico. De fato, a ideologia do Partido dominante em Oceânia não visa nada de coisa alguma para ninguém, no presente ou no futuro. O’Brien, hierarca do Partido, é quem explica a Winston que 'só nos interessa o poder em si. Nem riqueza, nem luxo, nem vida longa, nem felicidade - só o poder pelo poder, poder puro.",
                   "releaseDate": "1970/01/01",
                   "pages": 328,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_IgtfhhPwZ.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub__KQbeVHiJ.epub"
                 },
                 {
                   "title": "Fahrenheit 451",
                   "author": "Ray Bradbury",
                   "publisher": "lelivros",
                   "category": "Ficção",
                   "synopsis": "Em um futuro opressor, os livros são proibidos e queimados. Guy Montag, um bombeiro encarregado de destruir livros, começa a questionar sua missão.",
                   "releaseDate": "1970/01/01",
                   "pages": 158,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_Ohxnm7q66h.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_RW1CI7jUL.epub"
                 },
                 {
                   "title": "Cinquenta Tons Mais Escuros - vol.2",
                   "author": "E.L. James",
                   "publisher": "Top Livros",
                   "category": "Romance",
                   "synopsis": "Após a turbulenta separação de Christian e Ana, eles tentam encontrar uma maneira de se reconciliar. A história continua a explorar a relação intensa e cheia de desafios do casal, com temas de amor, controle e redenção.",
                   "releaseDate": "2012/02/01",
                   "pages": 496,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_yuwWgHj94.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_MJaJnn3I98.epub"
                 },
                 {
                   "title": "Cinquenta Tons de Cinza",
                   "author": "E L James",
                   "publisher": "LeLivros.com",
                   "category": "Romance",
                   "synopsis": "Quando Anastasia Steele entrevista o jovem empresário Christian Grey, descobre nele um homem atraente, brilhante e profundamente dominador. Ingênua e inocente, Ana se surpreende ao perceber que, a despeito da enigmática reserva de Grey, está desesperadamente atraída por ele. Incapaz de resistir à beleza discreta, à timidez e ao espírito independente de Ana, Grey admite que também a deseja - mas em seus próprios termos...",
                   "releaseDate": "2009/05/25",
                   "pages": 528,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_kb2HVA-OO.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_N3rAKXoY4.epub"
                 },
                 {
                   "title": "Cinquenta Tons De Liberdade - vol.3",
                   "author": "E.L. James",
                   "publisher": "Top Livros",
                   "category": "Romance",
                   "synopsis": "O último volume da série acompanha o relacionamento de Christian e Ana, agora casados, enfrentando novos desafios enquanto tentam manter sua vida pessoal e profissional equilibrada. Segredos do passado ameaçam sua felicidade, mas o amor deles é forte o suficiente para lutar contra qualquer adversidade.",
                   "releaseDate": "2012/10/23",
                   "pages": 592,
                   "coverImageUrl": "https://ik.imagekit.io/boundlessbooks/cover-images/cover_hlNU0wXSD.jpg",
                   "epubUrl": "https://ik.imagekit.io/boundlessbooks/epubs/epub_Os_yWK6f1.epub"
                 }
               ]
    """;
}
