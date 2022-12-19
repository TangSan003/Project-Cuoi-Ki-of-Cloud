package models.services.subject_group;

import models.view_models.subject_group.SubjectGroupCreateRequest;
import models.view_models.subject_group.SubjectGroupUpdateRequest;
import models.view_models.subject_group.SubjectGroupViewModel;

import java.util.ArrayList;

public interface ISubjectGroupService {
    boolean insert(SubjectGroupCreateRequest request);
    boolean update(SubjectGroupUpdateRequest request);
    boolean delete(String hashKey, String rangeKey);
    SubjectGroupViewModel retrieveById(String hashKey, String rangeKey);
    ArrayList<SubjectGroupViewModel> retrieveAll();
    boolean containLecture(String lectureId);
    boolean containSubject(String subjectId);
    ArrayList<SubjectGroupViewModel> retrieveSubjectGroupByLectureId(String lectureId);
}
