package com.extension.project.boundlessbooks;

import io.documentnode.epub4j.domain.Book;
import io.documentnode.epub4j.epub.EpubReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;

@SpringBootApplication
public class BoundlessBooksApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BoundlessBooksApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        EpubReader epubReader = new EpubReader();

        try (FileInputStream fis = new FileInputStream("/Users/rodrigo/Dev/infnet/extension/boundless-books/src/main/resources/epub/zodiacs-toy-john-jones-obooko.epub")) {

            Book book = epubReader.readEpub(fis);

            System.out.println("Title: " + book.getTitle());
            System.out.println("Dates: " + book.getMetadata().getDates());
            System.out.println("Publishers: " + book.getMetadata().getPublishers());
            System.out.println("Author: " + book.getMetadata().getAuthors());
            System.out.println("Language: " + book.getMetadata().getLanguage());
            System.out.println("Description: " + book.getMetadata().getDescriptions());

            // Save cover image file to a file
            byte[] coverImageData = book.getCoverImage().getData();
            try (FileOutputStream fos = new FileOutputStream("/Users/rodrigo/Dev/infnet/extension/boundless-books/src/main/resources/epub/cover.jpg")) {
                fos.write(coverImageData);
            }
        } catch (net.lingala.zip4j.exception.ZipException e) {
            System.err.println("Error reading EPUB file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
