package adastra.backend.softDeletion;

import adastra.backend.enums.IsDeleted;

public interface SoftDeleteInt {
    IsDeleted getIsDeleted();

    void setIsDeleted(IsDeleted isDeleted);

}
