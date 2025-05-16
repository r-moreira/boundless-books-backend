INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'Harry Potter e a Ordem da Fênix', 'J.K. Rowling', 'Rocco', 'Fantasia', 'Harry Potter retorna para seu quinto ano em Hogwarts e descobre que o Ministério da Magia está em negação sobre o retorno de Voldemort.', '2003-06-21', 704, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_avHr99l5G.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_RRU77Qqdh.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'Harry Potter e a Ordem da Fênix'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'Harry Potter e o enigma do Príncipe', 'J.K. Rowling', 'Rocco', 'Fantasia', 'No sexto ano de Harry em Hogwarts, ele descobre mais sobre o passado de Voldemort e se prepara para a batalha final.', '2005-07-16', 652, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_A_Nf7y0QS.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_E0e1nsyang.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'Harry Potter e o enigma do Príncipe'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'Harry Potter e o Cálice de Fogo', 'J.K. Rowling', 'Rocco', 'Fantasia', 'Harry é inesperadamente escolhido para competir no Torneio Tribruxo, uma competição perigosa entre três escolas de magia.', '2000-07-08', 636, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_zkkXai0Oh.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_dXQZuMFA3.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'Harry Potter e o Cálice de Fogo'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'Harry Potter e o prisioneiro de Azkaban', 'J.K. Rowling', 'Rocco', 'Fantasia', 'No terceiro ano de Harry em Hogwarts, ele descobre que um perigoso prisioneiro fugiu de Azkaban e está atrás dele.', '1999-07-08', 317, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_d_W_MyRBI.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_SyWiwsW-3.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'Harry Potter e o prisioneiro de Azkaban'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'Harry Potter e a Criança Amaldiçoada Partes Um e Dois (Edição Especial do Guião da Peça de Teatro)', 'J. K. Rowling', 'Pottermore', 'Fantasia', 'Baseado numa nova história original de J.K. Rowling, John Tiffany e Jack Thorne, HARRY POTTER E A CRIANÇA AMALDIÇOADA é uma nova peça de teatro de Jack Thorne. É a oitava história de Harry Potter e a primeira a ser oficialmente representada em palco. A Edição Especial do Guião da Peça de Teatro apresenta aos leitores novos desenvolvimentos da vida de Harry Potter e dos seus amigos e familiares, logo após a estreia mundial da peça, em West End, Londres, em 30 de julho de 2016.Foi sempre difícil ser Harry Potter e não é mais fácil agora que ele se tornou num muito atarefado funcionário do Ministério da Magia, casado e pai de três crianças em idade escolar.Enquanto Harry luta com um passado que se recusa a ficar para trás, o seu filho mais novo, Albus, tem de se debater com o peso de um legado familiar que nunca desejou. Quando o passado e o presente se cruzam, pai e filho confrontam-se com uma desconfortável verdade: por vezes as trevas vêm de lugares inesperados.', '2016-07-31', 336, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_JmV3P1wl6.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_S9td1bmY02.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'Harry Potter e a Criança Amaldiçoada Partes Um e Dois (Edição Especial do Guião da Peça de Teatro)'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'Hogwarts', 'J.K. Rowling', 'Le Livros', 'Fantasia', 'Uma visão detalhada da escola de magia e bruxaria de Hogwarts, incluindo sua história, professores e segredos.', '2014-09-01', 200, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_49jNjTYum.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_a2nqocKWu.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'Hogwarts'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'Harry Potter e a Câmara Secreta', 'J.K. Rowling', 'Rocco', 'Fantasia', 'No segundo ano de Harry em Hogwarts, ele descobre uma câmara secreta que guarda um terrível monstro.', '1998-07-02', 341, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_ptS-WZwHr.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_yNn-0r3Sv.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'Harry Potter e a Câmara Secreta'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'Harry Potter e as Relíquias da Morte', 'J.K. Rowling', 'Rocco', 'Fantasia', 'No sétimo e último livro da série, Harry, Rony e Hermione saem em busca das Horcruxes para derrotar Voldemort de uma vez por todas.', '2007-07-21', 607, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_iePwtC2Sa.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_pFknI3wLg.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'Harry Potter e as Relíquias da Morte'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'Harry Potter e a Pedra Filosofal', 'J.K. Rowling', 'Rocco', 'Fantasia', 'Harry Potter descobre que é um bruxo e começa sua educação mágica em Hogwarts, onde faz amigos e enfrenta perigos.', '1997-06-26', 223, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_sLBbKOlym.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_EVViu_oAI.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'Harry Potter e a Pedra Filosofal'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'O Guia do Mochileiro das Galaxias - Coleção Completa', 'Douglas Adams', 'Arqueiro', 'Ficção', 'Douglas Adams - Coleção Completa O Guia do Mochileiro das GalaxiasUnidos em um único livro, todos volumes da popular "trilogia de cinco livros" de Douglas Adams, e uma das séries de sátira e ficção científica mais populares do mundo: "O Guia do Mochileiro das Galáxias.  O Livro traz:   - "O Guia do Mochileiro das Galáxias" - "O Restaurante no Fim do Universo" - "A Vida, o Universo e Tudo mais" - "Até Mais, e Obrigado Pelos Peixes!" - "Praticamente Inofensiva" ', '1970-01-01', 832, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_IAcYXRd0J.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_U4-qtbdbX.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'O Guia do Mochileiro das Galaxias - Coleção Completa'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'A Revolução dos Bichos', 'George Orwell', 'LeLivros.com', 'Ficção', 'Sentindo chegar sua hora, Major, um velho porco, reúne os animais da fazenda para compartilhar de um sonho: serem governados por eles próprios, os animais, sem a submissão e exploração do homem. Ensinou-lhes uma antiga canção, Animais da Inglaterra (Beasts of England), que resume a filosofia do Animalismo, exaltando a igualdade entre eles e os tempos prósperos que estavam por vir, deixando os demais animais extasiados com as possibilidades. O velho Major faleceu três dias depois, tomando a frente os astutos e jovens porcos Bola-de-Neve e Napoleão, que passaram a se reunir clandestinamente a fim de traçar as estratégias da revolução. Certo dia Sr. Jones, então proprietário da fazenda, se descuidou na alimentação dos animais, fato este que se tornou o estopim para aqueles bichos. Deu-se a Revolução.', '1970-01-01', 112, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_Yf9LfSrvC.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_ckv-kzBVz.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'A Revolução dos Bichos'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'Neuromancer', 'William Gibson', 'Le Livros', 'Ficção', 'O Livro é um marco na literatura moderna, inaugurando a cibercultura, inspirado a trilogia Matrix. Esta edição especial possui um prefácio inédito do autor, posfácio acadêmico de Adriana Amaral e nova tradução feita por Fábio Fernandes (o mesmo tradutor de Reconhecimento de Padrões, também de Gibson, e Laranja Mecânica, de Anthony Burgess).', '1970-01-01', 271, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_jez_i6vQB.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_ilUTcFUVbT.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'Neuromancer'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT '1984', 'George Orwell', 'Companhia das Letras', 'Ficção', 'Winston, herói de 1984, último romance de George Orwell, vive aprisionado na engrenagem totalitária de uma sociedade completamente dominada pelo Estado, onde tudo é feito coletivamente, mas cada qual vive sozinho. Ninguém escapa à vigilância do Grande Irmão, a mais famosa personificação literária de um poder cínico e cruel ao infinito, além de vazio de sentido histórico. De fato, a ideologia do Partido dominante em Oceânia não visa nada de coisa alguma para ninguém, no presente ou no futuro. O’Brien, hierarca do Partido, é quem explica a Winston que "só nos interessa o poder em si. Nem riqueza, nem luxo, nem vida longa, nem felicidade - só o poder pelo poder, poder puro.', '1970-01-01', 328, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_IgtfhhPwZ.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub__KQbeVHiJ.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = '1984'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'Fahrenheit 451', 'Ray Bradbury', 'lelivros', 'Ficção', 'Em um futuro opressor, os livros são proibidos e queimados. Guy Montag, um bombeiro encarregado de destruir livros, começa a questionar sua missão.', '1970-01-01', 158, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_Ohxnm7q66h.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_RW1CI7jUL.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'Fahrenheit 451'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'Cinquenta Tons Mais Escuros - vol.2', 'E.L. James', 'Top Livros', 'Romance', 'Após a turbulenta separação de Christian e Ana, eles tentam encontrar uma maneira de se reconciliar. A história continua a explorar a relação intensa e cheia de desafios do casal, com temas de amor, controle e redenção.', '2012-02-01', 496, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_yuwWgHj94.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_MJaJnn3I98.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'Cinquenta Tons Mais Escuros - vol.2'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'Cinquenta Tons de Cinza', 'E L James', 'LeLivros.com', 'Romance', 'Quando Anastasia Steele entrevista o jovem empresário Christian Grey, descobre nele um homem atraente, brilhante e profundamente dominador. Ingênua e inocente, Ana se surpreende ao perceber que, a despeito da enigmática reserva de Grey, está desesperadamente atraída por ele. Incapaz de resistir à beleza discreta, à timidez e ao espírito independente de Ana, Grey admite que também a deseja - mas em seus próprios termos...', '2009-05-25', 528, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_kb2HVA-OO.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_N3rAKXoY4.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'Cinquenta Tons de Cinza'
);

INSERT INTO books_metadata (title, author, publisher, category, synopsis, release_date, pages, cover_image_url, epub_url, created_at, updated_at)
SELECT 'Cinquenta Tons De Liberdade - vol.3', 'E.L. James', 'Top Livros', 'Romance', 'O último volume da série acompanha o relacionamento de Christian e Ana, agora casados, enfrentando novos desafios enquanto tentam manter sua vida pessoal e profissional equilibrada. Segredos do passado ameaçam sua felicidade, mas o amor deles é forte o suficiente para lutar contra qualquer adversidade.', '2012-10-23', 592, 'https://ik.imagekit.io/boundlessbooks/cover-images/cover_hlNU0wXSD.jpg', 'https://ik.imagekit.io/boundlessbooks/epubs/epub_Os_yWK6f1.epub', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1 FROM books_metadata WHERE title = 'Cinquenta Tons De Liberdade - vol.3'
);