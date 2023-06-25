package com.example.demo.controller;

import com.example.demo.constant.Constant;
import com.example.demo.dto.response.ResponMessage;
import com.example.demo.model.Category;
import com.example.demo.model.Role;
import com.example.demo.model.RoleName;
import com.example.demo.model.User;
import com.example.demo.security.userprincal.UserDetailService;
import com.example.demo.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("category")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private UserDetailService userDetailService;

    @GetMapping
    public ResponseEntity<?> showPageCategory() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(
            @Valid
            @RequestBody
            Category category) {
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        List<Role> roleList = new ArrayList<>(user.getRoles());
        boolean checkRole = false;
        for (Role role : roleList) {
            if (role.getName().equals(RoleName.ADMIN)) {
                checkRole = true;
                break;
            }
        }
        if (checkRole) {
            if (categoryService.existsByName(category.getName())) {
                return new ResponseEntity<>(new ResponMessage(Constant.NAME_EXISTED), HttpStatus.OK);
            }
            categoryService.save(category);
            return new ResponseEntity<>(new ResponMessage(Constant.CREATE_SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponMessage(Constant.NO_PERMISSION), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailCategory(
            @PathVariable Long id) {
        System.out.println(id + "abc");
        Optional<Category> category = categoryService.findById(id);
        if (!category.isPresent()) {
            return new ResponseEntity<>(new ResponMessage("not_found"), HttpStatus.OK);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Optional<Category> category1 = categoryService.findById(id);
        if (!category1.isPresent()) {
            return new ResponseEntity<>(new ResponMessage("not_found"), HttpStatus.OK);
        }
        if (!category1.get().getAvatar().equals(category.getAvatar())) {
            category.setId(category1.get().getId());
        }
        if (!category.getName().equals(category1.get().getName())) {
            if (categoryService.existsByName(category.getName())) {
                return new ResponseEntity<>(new ResponMessage("name_existed"), HttpStatus.OK);
            }
        }
        if (category.getName().equals(category1.get().getName()) && category.getAvatar().equals(category1.get().getAvatar())) {
            return new ResponseEntity<>(new ResponMessage("no_change"), HttpStatus.OK);
        }
        category.setId(category1.get().getId());
        categoryService.save(category);
        return new ResponseEntity<>(new ResponMessage("update_successful"), HttpStatus.OK);
    }
}
