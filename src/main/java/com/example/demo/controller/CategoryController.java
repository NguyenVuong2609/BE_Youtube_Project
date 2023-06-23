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

@RestController
@RequestMapping("category")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private UserDetailService userDetailService;

    @GetMapping
    public ResponseEntity<?> showListCategory() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(
            @Valid
            @RequestBody
            Category category) {
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null){
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
}
