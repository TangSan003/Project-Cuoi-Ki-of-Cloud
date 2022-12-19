package models.repositories.subject_group;

import common.interfaces.IModifyEntity;
import common.interfaces.IRetrieveEntity;
import models.view_models.subject_group.SubjectGroupCreateRequest;
import models.view_models.subject_group.SubjectGroupUpdateRequest;
import models.view_models.subject_group.SubjectGroupViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface ISubjectGroupRepository extends IModifyEntity<SubjectGroupCreateRequest, SubjectGroupUpdateRequest, String, String>,
        IRetrieveEntity<SubjectGroupViewModel, String, String> {
    boolean containLecture(String lectureId);
    boolean containSubject(String subjectId);
    ArrayList<SubjectGroupViewModel> retrieveSubjectGroupByLectureId(String lectureId);
}
