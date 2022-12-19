package models.services.grade;

import models.repositories.grade.GradeRepository;
import models.view_models.grade.GradeCreateRequest;
import models.view_models.grade.GradeUpdateRequest;
import models.view_models.grade.GradeViewModel;

import java.util.ArrayList;

public class GradeService implements IGradeService{
    private static GradeService instance = null;
    public static GradeService getInstance(){
        if(instance == null)
            instance = new GradeService();
        return instance;
    }
    @Override
    public boolean insert(GradeCreateRequest request) {
        return GradeRepository.getInstance().insert(request);
    }

    @Override
    public boolean update(GradeUpdateRequest request) {
        return GradeRepository.getInstance().update(request);
    }

    @Override
    public boolean delete(String hashKey, String rangeKey) {
        return GradeRepository.getInstance().delete(hashKey,rangeKey);
    }

    @Override
    public GradeViewModel retrieveById(String hashKey, String rangeKey) {
        return GradeRepository.getInstance().retrieveById(hashKey,rangeKey);
    }

    @Override
    public ArrayList<GradeViewModel> retrieveAll() {
        return GradeRepository.getInstance().retrieveAll();
    }

    @Override
    public boolean containStudent(String studentId) {
        return GradeRepository.getInstance().containStudent(studentId);
    }

    @Override
    public boolean containSubjectGroup(String subjectGroupId) {
        return GradeRepository.getInstance().containSubjectGroup(subjectGroupId);
    }

    @Override
    public ArrayList<GradeViewModel> retrieveGradeByLectureId(String lectureId) {
        return GradeRepository.getInstance().retrieveGradeByLectureId(lectureId);
    }
}
