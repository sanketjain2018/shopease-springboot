package in.sj.service;

import in.sj.entity.User;

public interface UserService {
	// ================= REGISTER =================
    void register(User user);

    // ================= FIND =================
    User findByUsername(String username);

    // ================= UPDATE (GENERIC) =================
    void updateUser(User user);

    // ================= PROFILE =================
    User getUserForProfile(String username);

    void updateProfile(String username, User updatedUser);
}
