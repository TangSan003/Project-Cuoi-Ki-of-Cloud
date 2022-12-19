package models.services.grade;

import models.view_models.grade.GradeCreateRequest;
import models.view_models.grade.GradeUpdateRequest;
import models.view_models.grade.GradeViewModel;

import java.util.ArrayList;

public interface IGradeService {
    boolean insert(GradeCreateRequest request);
    boolean update(GradeUpdateRequest request);
    boolean delete(String hashKey, String rangeKey);
    GradeViewModel retrieveById(String hashKey, String rangeKey);
    ArrayList<GradeViewModel> retrieveAll();
    boolean containStudent(String studentId);
    boolean containSubjectGroup(String subjectGroupId);
    ArrayList<GradeViewModel> retrieveGradeByLectureId(String lectureId);
}
