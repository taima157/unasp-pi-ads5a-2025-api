package com.projetovirtus.api.Services;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projetovirtus.api.Exception.NotFoundException;
import com.projetovirtus.api.Exception.UnauthorizedException;
import com.projetovirtus.api.Models.UserModel;
import com.projetovirtus.api.Repository.UserRepository;
import com.projetovirtus.api.ViewObject.UserViewObject;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenderService genderService;

    public void checkIfAccountExists(UserModel userModel) {
        UserModel existUserModel = userRepository.findByEmail(userModel.getEmail());

        if (existUserModel != null) {
            throw new UnauthorizedException("Já existe uma conta com as credenciais que você colocou");
        }
    }

    public void encodePassword(UserModel userModel) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(encryptedPassword);
    }

    public void verifyUserPassword(UserModel inputUser, UserModel originalUser) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean rightPassword = passwordEncoder.matches(inputUser.getPassword(), originalUser.getPassword());

        if (!rightPassword) {
            throw new UnauthorizedException("Email ou senhas estão incorretos");
        }
    }

    public UserModel getUserByEmail(String email) {
        UserModel existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            throw new UnauthorizedException("Email ou senhas estão incorretos");
        }

        return existingUser;
    }

    public UserViewObject userModelToViewObject(UserModel userModel) {
        UserViewObject userViewObject = new UserViewObject();

        userViewObject.setId(userModel.getId());
        userViewObject.setFirstName(userModel.getFirstName());
        userViewObject.setLastName(userModel.getLastName());
        userViewObject.setEmail(userModel.getEmail());
        userViewObject.setBirth(userModel.getBirth());
        userViewObject.setIsProfessional(userModel.getIsProfessional());
        userViewObject.setActuationArea(userModel.getActuationArea());
        userViewObject.setOABCode(userModel.getOABCode());
        userViewObject.setPhoneNumber(userModel.getPhoneNumber());
        userViewObject.setGenderData(genderService.getGenderById(userModel.getGender()));

        return userViewObject;
    }

    public UserModel userViewObjectToModel(UserViewObject userViewObject) {
        Optional<UserModel> userObject = Optional.of(userRepository.findById(userViewObject.getId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado")));
        return userObject.get();
    }

    public UserViewObject getUserById(Long id) {
        Optional<UserModel> optionalUser = Optional
                .of(userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado")));
        return userModelToViewObject(optionalUser.get());
    }

    public List<UserViewObject> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::userModelToViewObject)
                .toList();
    }

    public UserViewObject editUserProfile(Long id, UserModel user) {
        Optional<UserModel> optionalUser = Optional
                .of(userRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário não encontrado")));
        UserModel userModel = optionalUser.get();
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userRepository.save(userModel);
        return userModelToViewObject(userModel);
    }

    public UserViewObject authenticateUser(UserModel user) {
        UserModel userModel = getUserByEmail(user.getEmail());
        verifyUserPassword(user, userModel);
        userRepository.save(userModel);
        return userModelToViewObject(userModel);
    }

    public void createUser(UserModel user) {
        checkIfAccountExists(user);
        encodePassword(user);
        userRepository.save(user);
    }
}
