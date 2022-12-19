package models.services.lecture;

import models.view_models.lecture.LectureCreateRequest;
import models.view_models.lecture.LectureUpdateRequest;
import models.view_models.lecture.LectureViewModel;

import java.util.ArrayList;

public interface ILectureService {
    boolean insert(LectureCreateRequest request);
    boolean update(LectureUpdateRequest request);
    boolean delete(String hashKey, String rangeKey);
    LectureViewModel retrieveById(String hashKey, String rangeKey);
    ArrayList<LectureViewModel> retrieveAll();
    boolean containLectureBelongFaculty(String facultyId);
}
