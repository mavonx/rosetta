//package org.rosetta.services;
//
//import org.rosetta.model.Category;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.rosetta.repositories.CategoryRepository;
//import org.rosetta.repositories.FakeCategoryRepository;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class CategoryServiceTest {
//    private CategoryService categoryService;
//
//    @BeforeEach
//    void setUp() {
//        CategoryRepository categoryRepository = new FakeCategoryRepository();
//        categoryService = new CategoryService(categoryRepository);
//    }
//
//    @Test
//    void create_newName_returnsSavedCategory() {
//        Category created = categoryService.create(new Category("Electronics"));
//
//        assertNotNull(created);
//        assertEquals("Electronics", created.getName());
//    }
//
//    @Test
//    void create_duplicateName_throwsIllegalStateException() {
//        categoryService.create(new Category("Electronics"));
//
//        assertThrows(IllegalStateException.class, () -> {
//            categoryService.create(new Category("Electronics"));
//        });
//    }
//
//    @Test
//    void delete_existingId_removesCategory() {
//        Category created = categoryService.create(new Category("Electronics"));
//
//        categoryService.delete(created.getId());
//        assertEquals(0, categoryService.getAll().size());
//    }
//
//    @Test
//    void delete_unknownId_throwsIllegalArgumentException() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            categoryService.delete(999);
//        });
//    }
//}
