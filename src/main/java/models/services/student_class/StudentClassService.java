package models.services.student_class;

import models.repositories.student_class.StudentClassRepository;
import models.view_models.student_class.StudentClassCreateRequest;
import models.view_models.student_class.StudentClassUpdateRequest;
import models.view_models.student_class.StudentClassViewModel;

import java.util.ArrayList;

public class StudentClassService implements IStudentClassService{
    private static StudentClassService instance = null;
    public static StudentClassService getInstance(){
        if(instance == null)
            instance = new StudentClassService();
        return instance;
    }

    @Override
    public boolean insert(StudentClassCreateRequest request) {
        return StudentClassRepository.getInstance().insert(request);
    }

    @Override
    public boolean update(StudentClassUpdateRequest request) {
        return StudentClassRepository.getInstance().update(request);
    }

    @Override
    public boolean delete(String hashKey, String rangeKey) {
        return StudentClassRepository.getInstance().delete(hashKey, rangeKey);
    }

    @Override
    public StudentClassViewModel retrieveById(String hashKey, String rangeKey) {
        return StudentClassRepository.getInstance().retrieveById(hashKey, rangeKey);
    }

    @Override
    public ArrayList<StudentClassViewModel> retrieveAll() {
        return StudentClassRepository.getInstance().retrieveAll();
    }

    @Override
    public boolean containFaculty(String facultyId) {
        return StudentClassRepository.getInstance().containFaculty(facultyId);
    }
}
