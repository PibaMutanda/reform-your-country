package reformyourcountry.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import reformyourcountry.model.User;
import reformyourcountry.exceptions.*;

public class UserDao {

    // TODO: remove this when we'll use a DB
    private  Set<User> listeUser = new HashSet<User>();

    // TODO: remove this when we'll use Spring.
    private static UserDao uniqueInstance = new UserDao();
    private UserDao() {};  // To disable new UserDao() outside this singleton class.
    public static UserDao getInstance() {
        return uniqueInstance;
    }

    @SuppressWarnings("unchecked")
    public  List<User> getListeUser() {
        return (List<User>) listeUser;
    }

    // TODO maxime delete static modifier when using JPA
    public void save(User user) {
        if (listeUser.contains(user)) {
            return;  // Already in the list
        }

        if (!listeUser.add(user))  {
            try {
                remove(user.getUserName());
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
            listeUser.add(user);
        }
    }
    //needed for JUnitTest data not annoying official data
    public void remove(String identifier) throws UserNotFoundException
    {
        for (User user : listeUser) {
            if (identifier.equals(user.getUserName())) {
                listeUser.remove(user);
                break;
            }
        }
    }

    // TODO maxime delete static modifier when using JPA
    public  User get(Long id) {

        for (User user : listeUser) {
            if (id.equals(user.getId()))
                return user;
        }
        return null;
    }

    // TODO maxime delete static modifier when using JPA
    public  User getUserByEmail(String identifier)  {
        for (User user : listeUser) {
            if (identifier.equals(user.getMail()))
                return user;
        }
        return null;
    }

    // TODO maxime delete static modifier when using JPA
    public  User getUserByUserName(String identifier) {
        for (User user : listeUser) {
            if (identifier.equals(user.getUserName()))
                return user;
        }
        return null;
    }



}
