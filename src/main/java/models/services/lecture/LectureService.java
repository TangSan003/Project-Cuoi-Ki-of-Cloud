package models.services.lecture;

import models.repositories.faculty.FacultyRepository;
import models.repositories.lecture.LectureRepository;
import models.view_models.lecture.LectureCreateRequest;
import models.view_models.lecture.LectureUpdateRequest;
import models.view_models.lecture.LectureViewModel;

import java.util.ArrayList;

public class LectureService implements ILectureService{
    private static LectureService instance = null;
    public static LectureService getInstance(){
        if(instance == null)
            instance = new LectureService();
        return instance;
    }
    @Override
    public boolean insert(LectureCreateRequest request) {
        return LectureRepository.getInstance().insert(request);
    }

    @Override
    public boolean update(LectureUpdateRequest request) {
        return LectureRepository.getInstance().update(request);
    }

    @Override
    public boolean delete(String hashKey, String rangeKey) {
        return LectureRepository.getInstance().delete(hashKey, rangeKey);
    }

    @Override
    public LectureViewModel retrieveById(String hashKey, String rangeKey) {
        return LectureRepository.getInstance().retrieveById(hashKey, rangeKey);
    }

    @Override
    public ArrayList<LectureViewModel> retrieveAll() {
        return LectureRepository.getInstance().retrieveAll();
    }

    @Override
    public boolean containLectureBelongFaculty(String facultyId) {
        return LectureRepository.getInstance().containLectureBelongFaculty(facultyId);
    }
}
