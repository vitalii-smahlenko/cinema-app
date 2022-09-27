package cinema.dao.impl;

import cinema.dao.AbstractDao;
import cinema.dao.RoleDao;
import cinema.exception.DataProcessingException;
import cinema.model.Role;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao {
    private static final Logger LOGGER = LogManager.getLogger(RoleDaoImpl.class);

    public RoleDaoImpl(SessionFactory factory) {
        super(factory, Role.class);
    }

    @Override
    public Optional<Role> getByName(String roleName) {
        try (Session session = factory.openSession()) {
            Query<Role> getByNameQuery
                    = session.createQuery("FROM Role WHERE roleName = :roleNane", Role.class);
            getByNameQuery.setParameter("roleNane", Role.RoleName.valueOf(roleName));
            LOGGER.info("Found role {} by role name {}",
                    getByNameQuery.uniqueResultOptional().get().toString(), roleName);
            return getByNameQuery.uniqueResultOptional();
        } catch (RuntimeException e) {
            LOGGER.error("Can't get role by roleName: {}.", roleName, e);
            throw new DataProcessingException("Can't get role by roleName: " + roleName, e);
        }
    }
}
