package pl.library.model;

import pl.library.exception.PublicationAlreadyExistsException;
import pl.library.exception.UserAlreadyExistsException;

import java.io.Serializable;
import java.util.*;

public class Library implements Serializable {

    private Map<String, Publication> publications = new HashMap<>();
    private Map<String, LibraryUser> users = new HashMap<>();

    public Map<String, Publication> getPublications() {
        return publications;
    }

    public Collection<Publication> getSortedPublications(Comparator<Publication> comparator){
        ArrayList<Publication> list = new ArrayList<>(this.publications.values());
        list.sort(comparator);
        return list;
    }

    public Collection<LibraryUser> getSortedUsers(Comparator<LibraryUser> comparator){
        ArrayList<LibraryUser> list = new ArrayList<>(this.users.values());
        list.sort(comparator);
        return list;
    }

    public Map<String, LibraryUser> getUsers() {
        return users;
    }

    public void addPublication(Publication publication){
        if(publications.containsKey(publication.getTitle())){
            throw new PublicationAlreadyExistsException("This publication alreaedy exists in the library: " + publication.getTitle());
        }else{
            publications.put(publication.getTitle(), publication);
        }
    }

    public void addUser(LibraryUser user){
        if(users.containsKey(user.getPesel())){
            throw new UserAlreadyExistsException("user with this PESEL number alreaedy exists in the library: " + user.getPesel());
        }else{
            users.put(user.getPesel(), user);
        }
    }

    public boolean removePublication(Publication publication){
        if(publications.containsValue(publication)){
            publications.remove(publication.getTitle());
            return true;
        }
        return false;
    }

    public Optional<Publication> findPublicationByTitle(String title){
        return Optional.ofNullable(publications.get(title));
    }



}
