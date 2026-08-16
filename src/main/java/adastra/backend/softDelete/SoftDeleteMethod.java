package adastra.backend.softDelete;

import adastra.backend.enums.IsDeleted;
import adastra.backend.exceptions.NotFoundException;
import adastra.backend.exceptions.WrongBodyDeletionException;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class SoftDeleteMethod<T extends SoftDeleteInt, ID> {

    protected abstract JpaRepository<T, ID> getRepository();

    protected abstract String getEntityName();

    public void softDeleteGeneric(ID entityId, String body) {
        T found = getRepository().findById(entityId).orElseThrow(() -> new NotFoundException("nessun film con questo id trovato"));

        boolean bodyTrue = body.equalsIgnoreCase("true");
        boolean foundTrue = found.getIsDeleted() == IsDeleted.TRUE;


        if (bodyTrue && foundTrue) {
            throw new WrongBodyDeletionException("L'elemento " + getEntityName() + " è già disponibile");
        } else if (!bodyTrue && !foundTrue) {
            throw new WrongBodyDeletionException("L'elemento " + getEntityName() + " risulta già cancellato");
        }
        switch (body.toLowerCase()) {
            case "true" -> found.setIsDeleted(IsDeleted.TRUE);
            case "false" -> found.setIsDeleted(IsDeleted.FALSE);
            default -> throw new WrongBodyDeletionException("il body può contenere solo 'true' o 'false'");
        }
        getRepository().save(found);
    }

}
