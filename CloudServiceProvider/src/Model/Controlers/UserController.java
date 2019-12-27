package Model.Controlers;

import Model.Entities.*;
import Model.Repositories.*;
import com.sun.xml.internal.bind.v2.TODO;

import java.io.IOException;
import java.util.List;

/**
 * Sadrzi metode koje vracaju podatke na osnovu toga koji je korisnik ulogovan
 */
public class UserController {
    public static List<VirtualMachine> getVirtualMachinesForUser(String email) throws IOException {
        UserRepository userRepository = UserRepository.getInstance();
        User user = userRepository.getUserByEmail(email);

        if(user.getRole().equals(UserRole.SuperAdmin)){
            VirtualMachineRepository virtualMachineRepository = VirtualMachineRepository.getInstance();
            return virtualMachineRepository.getVirtualMachineList();
        } else {
            return user.getOrganization().getVirtualMachinesList();
        }
    }

    public static List<Disc> getDiscsForUser(String email) throws IOException {
        UserRepository userRepository = UserRepository.getInstance();
        User user = userRepository.getUserByEmail(email);

        if(user.getRole().equals(UserRole.SuperAdmin)){
            DiscRepository discRepository = DiscRepository.getInstance();
            return discRepository.getDiscList();
        } else {
            return user.getOrganization().getDiscsOfOrganization();
        }
    }

    public static List<User> getAllUsers(String email) throws Exception {
        UserRepository userRepository = UserRepository.getInstance();
        User user = userRepository.getUserByEmail(email);
        if(user.getRole().equals(UserRole.SuperAdmin)){
            return userRepository.getUsersList();
        }

        // TODO : napravi novi error i hvataj ga u fuknciji koja poziva ovu fju
        // TODO : i tada vrati neki kod, nzm da l je 400 ili 500
        throw new Exception("nemas prava da to uradis");
    }

    /**
     * Sme samo ako super user
     * @param email
     * @return
     * @throws Exception
     */
    public static List<Organization> getAllOrganizations(String email) throws Exception {
        UserRepository userRepository = UserRepository.getInstance();
        User user = userRepository.getUserByEmail(email);

        if(user.getRole().equals(UserRole.SuperAdmin)){
            OrganizationRepository organizationRepository = OrganizationRepository.getInstance();
            return organizationRepository.getOrganizationsList();
        }

        // TODO : napravi novi error i hvataj ga u fuknciji koja poziva ovu fju
        // TODO : i tada vrati neki kod, nzm da l je 400 ili 500
        throw new Exception("Nema prava da to uradis");
    }

    public static List<Category> getAllCategories(String email) throws Exception {
        UserRepository userRepository = UserRepository.getInstance();
        User user = userRepository.getUserByEmail(email);

        if(user.getRole().equals(UserRole.SuperAdmin)){
            CategoryRepository categoryRepository = CategoryRepository.getInstance();
            return categoryRepository.getCategoryList();
        }

        // TODO : napravi novi error i hvataj ga u fuknciji koja poziva ovu fju
        // TODO : i tada vrati neki kod, nzm da l je 400 ili 500
        throw new Exception("Nema prava da to uradis");
    }


}
