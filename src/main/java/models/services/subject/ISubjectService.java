package models.services.subject;

import models.view_models.subject.SubjectCreateRequest;
import models.view_models.subject.SubjectUpdateRequest;
import models.view_models.subject.SubjectViewModel;

import java.util.ArrayList;

public interface ISubjectService {
    boolean insert(SubjectCreateRequest request);
    boolean update(SubjectUpdateRequest request);
    boolean delete(String hashKey, String rangeKey);
    SubjectViewModel retrieveById(String hashKey, String rangeKey);
    ArrayList<SubjectViewModel> retrieveAll();
}
