package uz.nanotechsolutions.lexon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.nanotechsolutions.lexon.entity.User;
import uz.nanotechsolutions.lexon.payload.request.ChangePasswordRequest;
import uz.nanotechsolutions.lexon.payload.request.UserRequest;
import uz.nanotechsolutions.lexon.payload.response.UserResponse;
import uz.nanotechsolutions.lexon.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserResponse changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return new UserResponse(409,"Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            return new UserResponse(409,"Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(user);
//        System.out.println("Password has been changed successfully!");
        return new UserResponse(0, "Password has been changed successfully!");
    }

    public UserResponse create(UserRequest userRequest){
        Optional<User> byEmail = userRepository.findByEmail(userRequest.getEmail());

        if(byEmail.isPresent()){
            return new UserResponse(409, "This user has already been added!");
        }

        User user = new User();
        User savedUser = createOrUpdateUser(userRequest, user);

        return new UserResponse(0, "User has been created successfully by ADMIN!", savedUser.getId());
    }

    public UserResponse getUserById(Integer id){
        Optional<User> userById = userRepository.findById(id);
        if(userById.isEmpty()){
            return new UserResponse(404,"User not found!");
        }

        User user = userById.get();
        return new UserResponse(0, "Completed successfully", user);
    }

    public UserResponse deleteUserById(Integer id){
        if(!userRepository.existsById(id))
            return new UserResponse(404,"User not found!");

        userRepository.deleteById(id);
        return new UserResponse(0, "User has been deleted successfully", id);
    }

    public UserResponse getAll(){
        List<User> allUsers = userRepository.findAll();

        if (allUsers.isEmpty()){
            return new UserResponse(204,"No users found!");
        }

        return new UserResponse(0, "Completed successfully", allUsers);
    }

    public UserResponse update(UserRequest userRequest, Integer id){
        Optional<User> userById = userRepository.findById(id);
        if(userById.isEmpty()){
            return new UserResponse(404,"User not found!");
        }
        User user = userById.get();
        User savedUser = createOrUpdateUser(userRequest, user);

        return new UserResponse(0, "User has been updated successfully", savedUser.getId());
    }

    private User createOrUpdateUser(UserRequest userRequest, User user) {
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFirstname(userRequest.getFirstName());
        user.setLastname(userRequest.getLastName());
        user.setRole(userRequest.getRole());
        return userRepository.save(user);
    }
}
