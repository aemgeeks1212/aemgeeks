package com.aem.geeks.core.models.impl;

import com.aem.geeks.core.models.Author;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class AuthorImplTest {

    private final AemContext aemContext=new AemContext();

    private Author author;

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(AuthorImpl.class);
        aemContext.load().json("/com/aem/geeks/core/models/impl/Author.json","/component");
        aemContext.load().json("/com/aem/geeks/core/models/impl/Page.json","/page");
        aemContext.load().json("/com/aem/geeks/core/models/impl/Resource.json","/resource");
    }

    @Test
    void getPageTitle() {
        aemContext.currentPage("/page/currentPage");
        author=aemContext.request().adaptTo(Author.class);
        assertEquals("Author Bio",author.getPageTitle());
    }

    @Test
    void getFirstName() {
        aemContext.currentResource("/component/author");
        author=aemContext.request().adaptTo(Author.class);
        final String expected="AEM";
        String actual=author.getFirstName();
        assertEquals(expected,actual);
    }

    @Test
    void getLastName() {
        aemContext.currentResource("/component/author");
        author=aemContext.request().adaptTo(Author.class);
        final String expected="GEEKS";
        String actual=author.getLastName();
        assertEquals(expected,actual);
    }

    @Test
    void getBooks() {
        aemContext.currentResource("/component/author");
        author=aemContext.request().adaptTo(Author.class);
        assertEquals(4,author.getBooks().size());
        assertEquals("JAVA",author.getBooks().get(0));
        assertEquals("DS",author.getBooks().get(2));
    }

    @Test
    void getBooksWithNull() {
        aemContext.currentResource("/component/author-empty-books");
        author=aemContext.request().adaptTo(Author.class);
        assertEquals(0,author.getBooks().size());
    }

    @Test
    void getIsProfessor() {
        aemContext.currentResource("/component/author");
        author=aemContext.request().adaptTo(Author.class);
        assertEquals(true,author.getIsProfessor());
    }

    @Test
    void getRequestAttribute() {
        aemContext.request().setAttribute("rAttribute","TestAttribute");
        author=aemContext.request().adaptTo(Author.class);
        assertEquals("TestAttribute",author.getRequestAttribute());
    }

    @Test
    void getLastModifiedBy() {
        aemContext.currentResource("/component/author");
        author=aemContext.request().adaptTo(Author.class);
        assertEquals("admin",author.getLastModifiedBy());
    }

    @Test
    void authorName() {
        assertEquals("AEM GEEKS", aemContext.registerService(new AuthorImpl()).authorName());
    }

    @Test
    void getHomePageName() {
        Resource resource=aemContext.currentResource("/resource/resourcePage");
        AuthorImpl authorImpl=aemContext.registerService(new AuthorImpl());
        authorImpl.resourcePage=resource;
        assertEquals("resourcePage",authorImpl.getHomePageName());
    }
    @Test
    void getBookDetailsWithMap() {
        aemContext.currentResource("/component/author");
        author=aemContext.request().adaptTo(Author.class);
        assertEquals(2,author.getBookDetailsWithMap().size());
        assertEquals("2000",author.getBookDetailsWithMap().get(0).get("publishyear"));
        assertEquals("Subject 1",author.getBookDetailsWithMap().get(0).get("booksubject"));
    }

}