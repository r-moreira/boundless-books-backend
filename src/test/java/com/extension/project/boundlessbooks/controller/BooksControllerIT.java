package com.extension.project.boundlessbooks.controller;

import com.extension.project.boundlessbooks.configuration.properties.ApplicationProperties;
import com.extension.project.boundlessbooks.factory.RequestFactory;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class BooksControllerIT {

    public static final String X_API_KEY = "X-API-KEY";

    @Autowired
    private ApplicationProperties properties;

    @Autowired
    private MockMvc mockMvc;

    @Order(value = 0)
    @Test
    void getAllBooks_ReturnsBooksList() throws Exception {
        mockMvc.perform(RequestFactory.build(get("/api/v1/books"))
                        .header(X_API_KEY, properties.getApiKey()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(17))
                .andExpect(jsonPath("$[*].title").isNotEmpty())
                .andExpect(jsonPath("$[*].author").isNotEmpty())
                .andExpect(jsonPath("$[*].publisher").isNotEmpty())
                .andExpect(jsonPath("$[*].category").isNotEmpty())
                .andExpect(jsonPath("$[*].synopsis").isNotEmpty())
                .andExpect(jsonPath("$[*].pages").isNotEmpty())
                .andExpect(jsonPath("$[*].releaseDate").isNotEmpty())
                .andExpect(jsonPath("$[*].coverImageUrl").isNotEmpty())
                .andExpect(jsonPath("$[*].epubUrl").isNotEmpty())
                .andExpect(jsonPath("$[*].createdAt").isNotEmpty())
                .andExpect(jsonPath("$[*].updatedAt").isNotEmpty());
    }

    @Order(1)
    @Test
    void getAllBooks_FilterByTitle_ReturnsFilteredBooks() throws Exception {
        mockMvc.perform(RequestFactory.build(get("/api/v1/books"))
                        .header(X_API_KEY, properties.getApiKey())
                        .param("title", "Harry Potter e a Ordem da Fênix"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title",  Matchers.everyItem(Matchers.equalTo("Harry Potter e a Ordem da Fênix"))));
    }

    @Order(2)
    @Test
    void getAllBooks_FilterByAuthor_ReturnsFilteredBooks() throws Exception {
        mockMvc.perform(RequestFactory.build(get("/api/v1/books"))
                        .header(X_API_KEY, properties.getApiKey())
                        .param("author", "J.K. Rowling"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].author", Matchers.everyItem(Matchers.equalTo("J.K. Rowling"))));
    }

    @Order(3)
    @Test
    void getAllBooks_FilterByCategory_ReturnsFilteredBooks() throws Exception {
        mockMvc.perform(RequestFactory.build(get("/api/v1/books"))
                        .header(X_API_KEY, properties.getApiKey())
                        .param("category", "Fantasia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].category", Matchers.everyItem(Matchers.equalTo(("Fantasia")))));
    }

    @Order(4)
    @Test
    void getAllBooks_FilterByReleaseDate_ReturnsFilteredBooks() throws Exception {
        mockMvc.perform(RequestFactory.build(get("/api/v1/books"))
                        .header(X_API_KEY, properties.getApiKey())
                        .param("release-date", "2003/06/20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].releaseDate", Matchers.everyItem(Matchers.equalTo(("2003-06-20")))));
    }

    @Order(5)
    @Test
    void getAllBooks_FilterByMultipleParams_ReturnsFilteredBooks() throws Exception {
        mockMvc.perform(RequestFactory.build(get("/api/v1/books"))
                        .header(X_API_KEY, properties.getApiKey())
                        .param("title", "Harry Potter e a Ordem da Fênix")
                        .param("author", "J.K. Rowling")
                        .param("category", "Fantasia")
                        .param("release-date", "2003/06/20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].title", Matchers.everyItem(Matchers.equalTo("Harry Potter e a Ordem da Fênix"))))
                .andExpect(jsonPath("$[*].author", Matchers.everyItem(Matchers.equalTo("J.K. Rowling"))))
                .andExpect(jsonPath("$[*].category", Matchers.everyItem(Matchers.equalTo(("Fantasia")))))
                .andExpect(jsonPath("$[*].releaseDate", Matchers.everyItem(Matchers.equalTo(("2003-06-20")))));
    }


    @Order(value = 6)
    @Test
    void getBookById_ReturnsBook() throws Exception {
        mockMvc.perform(get("/api/v1/books/1")
                .header("X-API-KEY", properties.getApiKey()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Harry Potter e a Ordem da Fênix"))
                .andExpect(jsonPath("$.author").value("J.K. Rowling"))
                .andExpect(jsonPath("$.publisher").value("Rocco"))
                .andExpect(jsonPath("$.category").value("Fantasia"))
                .andExpect(jsonPath("$.synopsis").value("Harry Potter retorna para seu quinto ano em Hogwarts e descobre que o Ministério da Magia está em negação sobre o retorno de Voldemort."))
                .andExpect(jsonPath("$.pages").value(704))
                .andExpect(jsonPath("$.releaseDate").value("2003/06/21"))
                .andExpect(jsonPath("$.coverImageUrl").value("https://ik.imagekit.io/boundlessbooks/cover-images/cover_avHr99l5G.jpg"))
                .andExpect(jsonPath("$.epubUrl").value("https://ik.imagekit.io/boundlessbooks/epubs/epub_RRU77Qqdh.epub"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty());
    }


    @Order(value = 7)
    @Test
    void createBook_ReturnsCreatedBook() throws Exception {
        mockMvc.perform(post("/api/v1/books")
                        .header("X-API-KEY", properties.getApiKey())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "title": "Harry Potter Teste",
                            "author": "J.K. Rowling",
                            "publisher": "Rocco",
                            "category": "Fantasia",
                            "synopsis": "Harry Potter retorna para seu quinto ano em Hogwarts...",
                            "pages": 704,
                            "releaseDate": "2003/06/20",
                            "coverImageUrl": "https://example.com/cover.jpg",
                            "epubUrl": "https://example.com/book.epub"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Harry Potter Teste"));
    }


    @Order(value = 8)
    @Test
    void deleteBook_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/books/7")
                        .header("X-API-KEY", properties.getApiKey())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Order(value = 9)
    @Test
    void getAllBooksPaginated_ReturnsPagedBooks() throws Exception {
        mockMvc.perform(get("/api/v1/books/paginated")
                        .header("X-API-KEY", properties.getApiKey())
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(5))
                .andExpect(jsonPath("$.content[*].title").isNotEmpty())
                .andExpect(jsonPath("$.content[*].author").isNotEmpty())
                .andExpect(jsonPath("$.content[*].publisher").isNotEmpty())
                .andExpect(jsonPath("$.content[*].category").isNotEmpty())
                .andExpect(jsonPath("$.content[*].synopsis").isNotEmpty())
                .andExpect(jsonPath("$.content[*].pages").isNotEmpty())
                .andExpect(jsonPath("$.content[*].releaseDate").isNotEmpty())
                .andExpect(jsonPath("$.content[*].coverImageUrl").isNotEmpty())
                .andExpect(jsonPath("$.content[*].epubUrl").isNotEmpty())
                .andExpect(jsonPath("$.content[*].createdAt").isNotEmpty())
                .andExpect(jsonPath("$.content[*].updatedAt").isNotEmpty());

    }

    @Order(10)
    @Test
    void getAllBooksPaginated_FilterByTitle_ReturnsFilteredPagedBooks() throws Exception {
        mockMvc.perform(get("/api/v1/books/paginated")
                        .header(X_API_KEY, properties.getApiKey())
                        .param("title", "Harry Potter e a Ordem da Fênix")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].title", Matchers.everyItem(Matchers.equalTo("Harry Potter e a Ordem da Fênix"))));
    }

    @Order(11)
    @Test
    void getAllBooksPaginated_FilterByAuthor_ReturnsFilteredPagedBooks() throws Exception {
        mockMvc.perform(get("/api/v1/books/paginated")
                        .header(X_API_KEY, properties.getApiKey())
                        .param("author", "J.K. Rowling")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].author", Matchers.everyItem(Matchers.equalTo("J.K. Rowling"))));
    }

    @Order(12)
    @Test
    void getAllBooksPaginated_FilterByCategory_ReturnsFilteredPagedBooks() throws Exception {
        mockMvc.perform(get("/api/v1/books/paginated")
                        .header(X_API_KEY, properties.getApiKey())
                        .param("category", "Fantasia")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].category", Matchers.everyItem(Matchers.equalTo("Fantasia"))));
    }

    @Order(13)
    @Test
    void getAllBooksPaginated_FilterByReleaseDate_ReturnsFilteredPagedBooks() throws Exception {
        mockMvc.perform(get("/api/v1/books/paginated")
                        .header(X_API_KEY, properties.getApiKey())
                        .param("release-date", "2003/06/20")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].releaseDate", Matchers.everyItem(Matchers.equalTo("2003-06-20"))));
    }

    @Order(14)
    @Test
    void getAllBooksPaginated_FilterByMultipleParams_ReturnsFilteredPagedBooks() throws Exception {
        mockMvc.perform(get("/api/v1/books/paginated")
                        .header(X_API_KEY, properties.getApiKey())
                        .param("title", "Harry Potter e a Ordem da Fênix")
                        .param("author", "J.K. Rowling")
                        .param("category", "Fantasia")
                        .param("release-date", "2003/06/20")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[*].title", Matchers.everyItem(Matchers.equalTo("Harry Potter e a Ordem da Fênix"))))
                .andExpect(jsonPath("$.content[*].author", Matchers.everyItem(Matchers.equalTo("J.K. Rowling"))))
                .andExpect(jsonPath("$.content[*].category", Matchers.everyItem(Matchers.equalTo("Fantasia"))))
                .andExpect(jsonPath("$.content[*].releaseDate", Matchers.everyItem(Matchers.equalTo("2003-06-20"))));
    }

    @Order(value = 15)
    @Test
    void createBooks_ReturnsCreatedBooks() throws Exception {
        mockMvc.perform(post("/api/v1/books/bulk")
                        .header("X-API-KEY", properties.getApiKey())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        [
                            {
                                "title": "Book 1",
                                "author": "Author 1",
                                "publisher": "Publisher 1",
                                "category": "Fantasia",
                                "synopsis": "Synopsis 1",
                                "pages": 300,
                                "releaseDate": "2023/01/01",
                                "coverImageUrl": "https://example.com/cover1.jpg",
                                "epubUrl": "https://example.com/book1.epub"
                            },
                            {
                                "title": "Book 2",
                                "author": "Author 2",
                                "publisher": "Publisher 2",
                                "category": "Fantasia",
                                "synopsis": "Synopsis 2",
                                "pages": 400,
                                "releaseDate": "2023/02/01",
                                "coverImageUrl": "https://example.com/cover2.jpg",
                                "epubUrl": "https://example.com/book2.epub"
                            }
                        ]
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].title").value("Book 2"));
    }

    @Order(value = 16)
    @Test
    void updateBook_ReturnsUpdatedBook() throws Exception {
        mockMvc.perform(put("/api/v1/books/1")
                        .header("X-API-KEY", properties.getApiKey())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "title": "Updated Title",
                            "author": "Updated Author",
                            "publisher": "Updated Publisher",
                            "category": "Fantasia",
                            "synopsis": "Updated Synopsis",
                            "pages": 500,
                            "releaseDate": "2023/03/01",
                            "coverImageUrl": "https://example.com/updated-cover.jpg",
                            "epubUrl": "https://example.com/updated-book.epub"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.author").value("Updated Author"));
    }

    @Order(value = 17)
    @Test
    void deleteBooks_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/books/bulk")
                .header("X-API-KEY", properties.getApiKey()))
                .andExpect(status().isNoContent());
    }


}