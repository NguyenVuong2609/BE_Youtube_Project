package com.example.demo.service.user;

import com.example.demo.model.Role;
import com.example.demo.model.RoleName;
import com.example.demo.model.User;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    IRoleRepository roleRepository;
    @Autowired
    IUserRepository userRepository;

    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void setDefaultRoleName() {
        Iterable<Role> roles = findAll();
        long roleSize = roles.spliterator().getExactSizeIfKnown();
        if (roleSize == 0) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            List<Role> roleList = new ArrayList<>();
            roleList.add(new Role(1L, RoleName.USER));
            roleList.add(new Role(2L, RoleName.PM));
            roleList.add(new Role(3L, RoleName.ADMIN));
            roleRepository.saveAll(roleList);
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(new Role(3L, RoleName.ADMIN));
            User user = new User(1L, "ADMIN", "admin", "admin@admin.com", passwordEncoder.encode("admin")
                    , roleSet);
            userRepository.save(user);
        }
    }
}
