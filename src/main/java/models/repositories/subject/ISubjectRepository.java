package models.repositories.subject;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.subject.SubjectCreateRequest;
import models.view_models.subject.SubjectUpdateRequest;
import models.view_models.subject.SubjectViewModel;

public interface ISubjectRepository extends IModifyEntity<SubjectCreateRequest, SubjectUpdateRequest, String, String>,
        IRetrieveEntity<SubjectViewModel, String, String> {

}
