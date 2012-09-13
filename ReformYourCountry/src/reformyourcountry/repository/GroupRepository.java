package reformyourcountry.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import reformyourcountry.model.Group;

@Repository
@SuppressWarnings("unchecked")
public class GroupRepository extends BaseRepository<Group> {
    public List<Group> findAll(){
        return em.createQuery("select g from Group g order by name").getResultList();
    }
    
    public Group findByName(String name) {
        return getSingleOrNullResult( em.createQuery("select g from Group g where lower(g.name) = :name")
                .setParameter("name", name.toLowerCase()));
    }
}
